package com.bridge.data.lead.mapper;

import com.bridge.data.lead.model.Lead;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmLeadDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LeadZohoMapper implements MapperImpl<Lead, CrmLeadDto> {
    @Override
    public CrmLeadDto toDto(Lead entity) {
        CrmLeadDto dto = new CrmLeadDto();
        dto.setLastName(entity.getLastName());
        return dto;
    }

    @Override
    public Lead toEntity(CrmLeadDto dto) {
        return null;
    }
}
