package com.bridge.data.deal.mapper;

import com.bridge.data.deal.model.Deal;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmDealDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DealZohoMapper implements MapperImpl<Deal, CrmDealDto> {
    @Override
    public CrmDealDto toDto(Deal entity) {
        CrmDealDto crmDealDto = new CrmDealDto();
        return crmDealDto;
    }
}
