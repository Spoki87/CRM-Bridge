package com.bridge.data.salesorder.mapper;


import com.bridge.data.salesorder.model.SalesOrder;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmSalesOrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SalesOrderZohoMapper implements MapperImpl<SalesOrder, CrmSalesOrderDto> {
    @Override
    public CrmSalesOrderDto toDto(SalesOrder entity) {
        CrmSalesOrderDto crmSalesOrderDto = new CrmSalesOrderDto();
        return crmSalesOrderDto;
    }
}
