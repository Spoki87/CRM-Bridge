package com.bridge.data.account.service;

import com.bridge.data.account.mapper.AccountZohoMapper;
import com.bridge.data.account.model.Account;
import com.bridge.data.account.repository.AccountRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.enums.Module;
import com.bridge.zoho.model.request.CrmRequest;
import com.bridge.zoho.model.response.CrmResponse;
import com.bridge.zoho.model.response.Data;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@AllArgsConstructor
@EnableAsync
public class AccountService {

    private final AccountRepository accountRepository;
    private final CrmApiClient crmApiClient;
    private final AccountZohoMapper accountZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {

        List<Account> accounts = accountRepository.findAll()
                .stream()
                .filter(account -> account.getZohoId() == null)
                .toList();

        logger.info("Found {} accounts to synchronize", accounts.size());

        SyncReport syncReport = new SyncReport(
                Module.ACCOUNTS,
                accounts.size()
        );

        List<List<Account>> batches = BatchUtils.splitIntoBatches(accounts, BATCH_SIZE);

        for (List<Account> batch : batches) {
            logger.info("Processing batch with {} accounts", batch.size());

                List<CrmAccountDto> crmAccountDtoList = batch.stream().map(accountZohoMapper::toDto).toList();
                CrmRequest<CrmAccountDto> crmRequest = new CrmRequest<>(crmAccountDtoList);
                CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.ACCOUNTS);

                handleCrmResponse(crmResponse, batch, syncReport);
            }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");
    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Account> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if (responseData.getStatus().equalsIgnoreCase("success")) {
                    Account account = batch.get(i);
                    account.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    accountRepository.save(account);
                    syncReport.addSuccess(new SyncRecord(
                            account.getId(),
                            account.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Account id {} successfully synchronized with Zoho", account.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Account id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
