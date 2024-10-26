package com.bridge.data.deal.service;

import com.bridge.data.account.mapper.AccountZohoMapper;
import com.bridge.data.account.model.Account;
import com.bridge.data.account.repository.AccountRepository;
import com.bridge.data.account.service.AccountService;
import com.bridge.data.deal.mapper.DealZohoMapper;
import com.bridge.data.deal.model.Deal;
import com.bridge.data.deal.repository.DealRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.dto.CrmDealDto;
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

import java.util.List;

@Service
@AllArgsConstructor
@EnableAsync
public class DealService {

    private final DealRepository dealRepository;
    private final CrmApiClient crmApiClient;
    private final DealZohoMapper dealZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {

        List<Deal> deals = dealRepository.findAll()
                .stream()
                .filter(deal -> deal.getZohoId() == null)
                .toList();

        logger.info("Found {} deals to synchronize", deals.size());

        SyncReport syncReport = new SyncReport(
                Module.ACCOUNTS,
                deals.size()
        );

        List<List<Deal>> batches = BatchUtils.splitIntoBatches(deals, BATCH_SIZE);

        for (List<Deal> batch : batches) {
            logger.info("Processing batch with {} deals", batch.size());

            List<CrmDealDto> crmDealDtoList = batch.stream().map(dealZohoMapper::toDto).toList();
            CrmRequest<CrmDealDto> crmRequest = new CrmRequest<>(crmDealDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.DEALS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");
    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Deal> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Deal deal = batch.get(i);
                    deal.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    dealRepository.save(deal);
                    syncReport.addSuccess(new SyncRecord(
                            deal.getId(),
                            deal.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Deal id {} successfully synchronized with Zoho", deal.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Deal id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
