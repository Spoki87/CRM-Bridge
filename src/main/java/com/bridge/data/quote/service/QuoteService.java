package com.bridge.data.quote.service;

import com.bridge.data.account.mapper.AccountZohoMapper;
import com.bridge.data.account.model.Account;
import com.bridge.data.account.repository.AccountRepository;
import com.bridge.data.account.service.AccountService;
import com.bridge.data.quote.mapper.QuoteZohoMapper;
import com.bridge.data.quote.model.Quote;
import com.bridge.data.quote.repository.QuoteRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.dto.CrmQuoteDto;
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
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final CrmApiClient crmApiClient;
    private final QuoteZohoMapper quoteZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {
        List<Quote> quotes = quoteRepository.findAll()
                .stream()
                .filter(quote -> quote.getZohoId() == null)
                .toList();

        logger.info("Found {} accounts to synchronize", quotes.size());

        SyncReport syncReport = new SyncReport(
                Module.QUOTES,
                quotes.size()
        );

        List<List<Quote>> batches = BatchUtils.splitIntoBatches(quotes, BATCH_SIZE);

        for (List<Quote> batch : batches) {
            logger.info("Processing batch with {} quotes", batch.size());

            List<CrmQuoteDto> crmQuoteDtoList = batch.stream().map(quoteZohoMapper::toDto).toList();
            CrmRequest<CrmQuoteDto> crmRequest = new CrmRequest<>(crmQuoteDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.QUOTES);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");

    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Quote> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Quote quote = batch.get(i);
                    quote.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    quoteRepository.save(quote);
                    syncReport.addSuccess(new SyncRecord(
                            quote.getId(),
                            quote.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Quote id {} successfully synchronized with Zoho", quote.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Quote id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
