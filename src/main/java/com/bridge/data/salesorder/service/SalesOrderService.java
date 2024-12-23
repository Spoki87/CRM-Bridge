package com.bridge.data.salesorder.service;

import com.bridge.data.account.mapper.AccountZohoMapper;
import com.bridge.data.account.model.Account;
import com.bridge.data.account.repository.AccountRepository;
import com.bridge.data.account.service.AccountService;
import com.bridge.data.salesorder.mapper.SalesOrderZohoMapper;
import com.bridge.data.salesorder.model.SalesOrder;
import com.bridge.data.salesorder.repository.SalesOrderRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.dto.CrmSalesOrderDto;
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
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final CrmApiClient crmApiClient;
    private final SalesOrderZohoMapper salesOrderZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {

        List<SalesOrder> salesOrders = salesOrderRepository.findAll()
                .stream()
                .filter(salesOrder -> salesOrder.getZohoId() == null)
                .toList();

        logger.info("Found {} accounts to synchronize", salesOrders.size());

        SyncReport syncReport = new SyncReport(
                Module.SALES_ORDERS,
                salesOrders.size()
        );

        List<List<SalesOrder>> batches = BatchUtils.splitIntoBatches(salesOrders, BATCH_SIZE);

        for (List<SalesOrder> batch : batches) {
            logger.info("Processing batch with {} sales orders", batch.size());

            List<CrmSalesOrderDto> crmSalesOrderDtoList = batch.stream().map(salesOrderZohoMapper::toDto).toList();
            CrmRequest<CrmSalesOrderDto> crmRequest = new CrmRequest<>(crmSalesOrderDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.SALES_ORDERS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");

    }

    private void handleCrmResponse(CrmResponse crmResponse, List<SalesOrder> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    SalesOrder salesOrder = batch.get(i);
                    salesOrder.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    salesOrderRepository.save(salesOrder);
                    syncReport.addSuccess(new SyncRecord(
                            salesOrder.getId(),
                            salesOrder.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Sales order id {} successfully synchronized with Zoho", salesOrder.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Sales order id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
