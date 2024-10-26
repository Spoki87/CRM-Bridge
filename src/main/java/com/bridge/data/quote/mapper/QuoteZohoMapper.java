package com.bridge.data.quote.mapper;

import com.bridge.data.quote.model.Quote;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmQuoteDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuoteZohoMapper implements MapperImpl<Quote, CrmQuoteDto> {
    @Override
    public CrmQuoteDto toDto(Quote entity) {
        CrmQuoteDto crmQuoteDto = new CrmQuoteDto();
        return crmQuoteDto;
    }
}
