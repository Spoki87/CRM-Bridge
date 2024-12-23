package com.bridge.data.contact.service;

import com.bridge.data.account.model.Account;
import com.bridge.data.contact.mapper.ContactZohoMapper;
import com.bridge.data.contact.model.Contact;
import com.bridge.data.contact.repository.ContactRepository;
import com.bridge.report.model.SyncRecord;
import com.bridge.report.model.SyncReport;
import com.bridge.report.repository.SyncReportRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmAccountDto;
import com.bridge.zoho.dto.CrmContactDto;
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
public class ContactService {

    private final ContactRepository contactRepository;
    private final CrmApiClient crmApiClient;
    private final ContactZohoMapper contactZohoMapper;
    private final SyncReportRepository syncReportRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    private static final int BATCH_SIZE = 100;

    @Async
    public void sendToCrm() {
        List<Contact> contacts = contactRepository.findAll()
                .stream()
                .filter(contact -> contact.getZohoId() == null)
                .toList();

        logger.info("Found {} contacts to synchronize", contacts.size());

        SyncReport syncReport = new SyncReport(
                Module.CONTACTS,
                contacts.size()
        );

        List<List<Contact>> batches = BatchUtils.splitIntoBatches(contacts, BATCH_SIZE);

        for (List<Contact> batch : batches) {
            logger.info("Processing batch with {} contacts", batch.size());

            List<CrmContactDto> crmContactDtoList = batch.stream().map(contactZohoMapper::toDto).toList();
            CrmRequest<CrmContactDto> crmRequest = new CrmRequest<>(crmContactDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.CONTACTS);

            handleCrmResponse(crmResponse, batch, syncReport);
        }

        syncReportRepository.save(syncReport);
        logger.info("CRM sync process completed successfully.");
    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Contact> batch, SyncReport syncReport) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Contact contact = batch.get(i);
                    contact.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    contactRepository.save(contact);
                    syncReport.addSuccess(new SyncRecord(
                            contact.getId(),
                            contact.getZohoId(),
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.info("Contact id {} successfully synchronized with Zoho", contact.getId());
                }else{
                    syncReport.addFailure(new SyncRecord(
                            batch.get(i).getId(),
                            null,
                            responseData.getStatus(),
                            responseData.getMessage(),
                            syncReport
                    ));
                    logger.warn("Contact id {} failed to sync: {}", batch.get(i).getId(), responseData.getMessage());
                }
            }
        }
    }
}
