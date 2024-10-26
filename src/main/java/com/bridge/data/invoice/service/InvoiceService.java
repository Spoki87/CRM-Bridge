package com.bridge.data.invoice.service;

import com.bridge.data.invoice.mapper.InvoiceZohoMapper;
import com.bridge.data.invoice.model.Invoice;
import com.bridge.data.invoice.repository.InvoiceRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmInvoiceDto;
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
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CrmApiClient crmApiClient;
    private final InvoiceZohoMapper invoiceZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {
        List<Invoice> invoices = invoiceRepository.findAll()
                .stream()
                .filter(invoice -> invoice.getZohoId() == null)
                .toList();

        logger.info("Found {} invoices to synchronize", invoices.size());

        SyncReport syncReport = new SyncReport(
                Module.ACCOUNTS,
                invoices.size()
        );

        List<List<Invoice>> batches = BatchUtils.splitIntoBatches(invoices, BATCH_SIZE);

        for (List<Invoice> batch : batches) {
            logger.info("Processing batch with {} invoices", batch.size());

            List<CrmInvoiceDto> crmInvoiceDtoList = batch.stream().map(invoiceZohoMapper::toDto).toList();
            CrmRequest<CrmInvoiceDto> crmRequest = new CrmRequest<>(crmInvoiceDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.ACCOUNTS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");
    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Invoice> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Invoice invoice = batch.get(i);
                    invoice.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    invoiceRepository.save(invoice);
                    syncReport.addSuccess(new SyncRecord(
                            invoice.getId(),
                            invoice.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Invoice id {} successfully synchronized with Zoho", invoice.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Invoice id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
