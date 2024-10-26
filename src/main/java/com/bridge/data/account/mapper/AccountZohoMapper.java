package com.bridge.data.account.mapper;

import com.bridge.data.account.model.Account;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmAccountDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountZohoMapper implements MapperImpl<Account, CrmAccountDto> {
    @Override
    public CrmAccountDto toDto(Account entity) {
        CrmAccountDto crmAccountDto = new CrmAccountDto();
        return crmAccountDto;
    }
}
