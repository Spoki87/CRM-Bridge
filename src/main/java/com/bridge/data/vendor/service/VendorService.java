package com.bridge.data.vendor.service;

import com.bridge.data.account.mapper.AccountZohoMapper;
import com.bridge.data.account.model.Account;
import com.bridge.data.account.repository.AccountRepository;
import com.bridge.data.account.service.AccountService;
import com.bridge.data.vendor.mapper.VendorZohoMapper;
import com.bridge.data.vendor.model.Vendor;
import com.bridge.data.vendor.repository.VendorRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.dto.CrmVendorDto;
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
public class VendorService {

    private final VendorRepository vendorRepository;
    private final CrmApiClient crmApiClient;
    private final VendorZohoMapper vendorZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {

        List<Vendor> vendors = vendorRepository.findAll()
                .stream()
                .filter(vendor -> vendor.getZohoId() == null)
                .toList();

        logger.info("Found {} vendors to synchronize", vendors.size());

        SyncReport syncReport = new SyncReport(
                Module.VENDORS,
                vendors.size()
        );

        List<List<Vendor>> batches = BatchUtils.splitIntoBatches(vendors, BATCH_SIZE);

        for (List<Vendor> batch : batches) {
            logger.info("Processing batch with {} vendors", batch.size());

            List<CrmVendorDto> crmVendorDtoList = batch.stream().map(vendorZohoMapper::toDto).toList();
            CrmRequest<CrmVendorDto> crmRequest = new CrmRequest<>(crmVendorDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.VENDORS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");

    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Vendor> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Vendor vendor = batch.get(i);
                    vendor.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    vendorRepository.save(vendor);
                    syncReport.addSuccess(new SyncRecord(
                            vendor.getId(),
                            vendor.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Vendor id {} successfully synchronized with Zoho", vendor.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Vendor id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }

}
