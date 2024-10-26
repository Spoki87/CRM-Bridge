package com.bridge.data.product.mapper;

import com.bridge.data.product.model.Product;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductZohoMapper implements MapperImpl<Product, CrmProductDto> {
    @Override
    public CrmProductDto toDto(Product entity) {
        CrmProductDto crmProductDto = new CrmProductDto();
        return crmProductDto;
    }
}
