package com.bridge.zoho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrmAccountDto {

    //account information
    @JsonProperty("Account_Name")
    private String accountName;

    @JsonProperty("Owner")
    private Long accountOwner;

    @JsonProperty("Fax")
    private String fax;

    @JsonProperty("Account_Site")
    private String accountSite;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Account_Type")
    private String accountType;

    @JsonProperty("Account_Number")
    private Long accountNumber;

    @JsonProperty("Industry")
    private String industry;

    @JsonProperty("Annual_Revenue")
    private double annualRevenue;

    @JsonProperty("Rating")
    private String rating;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Parent_Account")
    private Long parentAccount;

    @JsonProperty("Ticker_Symbol")
    private String tickerSymbol;

    @JsonProperty("Ownership")
    private String ownerShip;

    @JsonProperty("Employees")
    private Long employees;

    @JsonProperty("SIC_Code")
    private String sisCode;

    //address information
    @JsonProperty("Billing_Street")
    private String billingStreet;

    @JsonProperty("Billing_City")
    private String billingCity;

    @JsonProperty("Billing_State")
    private String billingState;

    @JsonProperty("Billing_Code")
    private String billingCode;

    @JsonProperty("Billing_Country")
    private String billingCountry;

    @JsonProperty("Shipping_Street")
    private String shippingStreet;

    @JsonProperty("Shipping_City")
    private String shippingCity;

    @JsonProperty("Shipping_State")
    private String shippingState;

    @JsonProperty("Shipping_Code")
    private String shippingCode;

    @JsonProperty("Shipping_Country")
    private String shippingCountry;

    //description information
    @JsonProperty("Description")
    private String description;

    //custom fields
}
