package com.bridge.data.contact.mapper;

import com.bridge.data.contact.model.Contact;
import com.bridge.mapper.MapperImpl;
import com.bridge.zoho.dto.CrmContactDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContactZohoMapper implements MapperImpl<Contact, CrmContactDto> {
    @Override
    public CrmContactDto toDto(Contact entity) {
        CrmContactDto crmContactDto = new CrmContactDto();
        return crmContactDto;
    }
}
