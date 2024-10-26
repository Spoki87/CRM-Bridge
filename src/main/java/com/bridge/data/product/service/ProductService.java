package com.bridge.data.product.service;

import com.bridge.data.account.mapper.AccountZohoMapper;
import com.bridge.data.account.model.Account;
import com.bridge.data.account.repository.AccountRepository;
import com.bridge.data.account.service.AccountService;
import com.bridge.data.product.mapper.ProductZohoMapper;
import com.bridge.data.product.model.Product;
import com.bridge.data.product.repository.ProductRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.dto.CrmProductDto;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final CrmApiClient crmApiClient;
    private final ProductZohoMapper productZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {

        List<Product> products = productRepository.findAll()
                .stream()
                .filter(account -> account.getZohoId() == null)
                .toList();

        logger.info("Found {} products to synchronize", products.size());

        SyncReport syncReport = new SyncReport(
                Module.ACCOUNTS,
                products.size()
        );

        List<List<Product>> batches = BatchUtils.splitIntoBatches(products, BATCH_SIZE);

        for (List<Product> batch : batches) {
            logger.info("Processing batch with {} products", batch.size());

            List<CrmProductDto> crmProductDtoList = batch.stream().map(productZohoMapper::toDto).toList();
            CrmRequest<CrmProductDto> crmRequest = new CrmRequest<>(crmProductDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.PRODUCTS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");
    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Product> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Product product = batch.get(i);
                    product.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    productRepository.save(product);
                    syncReport.addSuccess(new SyncRecord(
                            product.getId(),
                            product.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Product id {} successfully synchronized with Zoho", product.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Product id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
