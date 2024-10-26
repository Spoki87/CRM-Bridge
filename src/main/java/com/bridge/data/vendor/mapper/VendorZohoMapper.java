package com.bridge.data.vendor.mapper;


import com.bridge.data.vendor.model.Vendor;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmVendorDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VendorZohoMapper implements MapperImpl<Vendor, CrmVendorDto> {
    @Override
    public CrmVendorDto toDto(Vendor entity) {
        CrmVendorDto crmVendorDto = new CrmVendorDto();
        return crmVendorDto;
    }
}
