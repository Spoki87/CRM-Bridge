package com.bridge.data.invoice.mapper;

import com.bridge.data.invoice.model.Invoice;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmInvoiceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InvoiceZohoMapper implements MapperImpl<Invoice, CrmInvoiceDto> {
    @Override
    public CrmInvoiceDto toDto(Invoice entity) {
        CrmInvoiceDto crmInvoiceDto = new CrmInvoiceDto();
        return crmInvoiceDto;
    }
}
