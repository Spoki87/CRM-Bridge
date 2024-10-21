package com.bridge.data.lead.service;

import com.bridge.data.lead.mapper.LeadZohoMapper;
import com.bridge.data.lead.model.Lead;
import com.bridge.data.lead.repository.LeadRepository;
import com.bridge.utils.BatchUtils;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmLeadDto;
import com.bridge.zoho.enums.Module;
import com.bridge.zoho.model.request.CrmRequest;
import com.bridge.zoho.model.response.CrmResponse;
import com.bridge.zoho.model.response.Data;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final CrmApiClient crmApiClient;
    private final LeadZohoMapper leadZohoMapper;

    private static final int BATCH_SIZE = 100;

    public void sendToCrm() {

        List<Lead> leads = leadRepository.findAll()
                .stream()
                .filter(lead -> lead.getZohoId() == null)
                .toList();

        List<List<Lead>> batches = BatchUtils.splitIntoBatches(leads, BATCH_SIZE);

        for (List<Lead> batch : batches) {

            List<CrmLeadDto> crmLeadDtoList = batch.stream().map(leadZohoMapper::toDto).toList();

            CrmRequest<CrmLeadDto> crmRequest = new CrmRequest<>(crmLeadDtoList);
            CrmResponse crmResponse = crmApiClient.sendDataToCrm(crmRequest, Module.LEADS);

            handleCrmResponse(crmResponse,batch);
        }
    }

    private void handleCrmResponse(CrmResponse crmResponse, List<Lead> batch) {
        if (crmResponse.getData() != null) {
            for (int i = 0; i < crmResponse.getData().size(); i++) {
                Data responseData = crmResponse.getData().get(i);
                if ("success".equalsIgnoreCase(responseData.getStatus())) {
                    Lead lead = batch.get(i);
                    lead.setZohoId(Long.valueOf(responseData.getDetails().getId()));
                    leadRepository.save(lead);
                }
            }
        }
    }

}
