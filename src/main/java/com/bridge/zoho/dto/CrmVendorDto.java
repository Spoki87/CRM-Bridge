package com.bridge.zoho.dto;

import com.bridge.zoho.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrmVendorDto {

    //vendor information
    @JsonProperty("Vendor_Name")
    private String vendorName;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("GL_Account")
    private String glAccount;

    @JsonProperty("Email_Opt_Out")
    private boolean emailOptOut;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Category")
    private String category;

    @JsonProperty("Currency")
    private Currency currency;

    @JsonProperty("Owner")
    private Long owner;

    //address information
    @JsonProperty("Street")
    private String street;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Zip_Code")
    private String zipCode;

    //description information
    @JsonProperty("Description")
    private String description;
}
