package com.bridge.data.lead.service;

import com.bridge.data.lead.model.Lead;
import com.bridge.data.lead.repository.LeadRepository;
import com.bridge.zoho.api.CrmApiClient;
import com.bridge.zoho.dto.CrmLeadDto;
import com.bridge.zoho.model.request.CrmRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final CrmApiClient crmApiClient;

    public void sendToCrm(){

        List<Lead> leads = leadRepository.findAll()
                .stream()
                .filter(lead -> lead.getZohoId() != null)
                .toList();

        List<CrmLeadDto> leadDtoList = new ArrayList<>();

        CrmRequest<CrmLeadDto> crmRequest = new CrmRequest<CrmLeadDto>(leadDtoList);

        crmApiClient.sendDataToCrm(crmRequest,"leads");
    }
}
