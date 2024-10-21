package com.bridge.zoho.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Module {
    LEADS("Leads"),
    CONTACTS("Contacts"),
    ACCOUNTS("Accounts"),
    DEALS("Deals"),
    PRODUCTS("Products");

    private final String moduleName;

}
