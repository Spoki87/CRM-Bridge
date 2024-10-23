package com.bridge.data.lead.service;

import com.bridge.data.lead.mapper.LeadZohoMapper;
import com.bridge.data.lead.model.Lead;
import com.bridge.data.lead.repository.LeadRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmLeadDto;
import com.bridge.zoho.enums.Module;
import com.bridge.zoho.model.request.CrmRequest;
import com.bridge.zoho.model.response.CrmResponse;
import com.bridge.zoho.model.response.Data;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
@EnableAsync
public class LeadService {

    private final LeadRepository leadRepository;
    private final CrmApiClient crmApiClient;
    private final LeadZohoMapper leadZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(LeadService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {

        List<Lead> leads = leadRepository.findAll()
                .stream()
                .filter(lead -> lead.getZohoId() == null)
                .toList();

        logger.info("Found {} leads to synchronize", leads.size());

        SyncReport syncReport = new SyncReport(
                Module.LEADS,
                leads.size()
                );

        List<List<Lead>> batches = BatchUtils.splitIntoBatches(leads, BATCH_SIZE);

        for (List<Lead> batch : batches) {
            logger.info("Processing batch with {} leads", batch.size());

            List<CrmLeadDto> crmLeadDtoList = batch.stream().map(leadZohoMapper::toDto).toList();
            CrmRequest<CrmLeadDto> crmRequest = new CrmRequest<>(crmLeadDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.LEADS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");

    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Lead> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Lead lead = batch.get(i);
                    lead.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    leadRepository.save(lead);
                    syncReport.addSuccess(new SyncRecord(
                            lead.getId(),
                            lead.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Lead id {} successfully synchronized with Zoho", lead.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Lead id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }

}
