package com.bridge.zoho.dto;

import com.bridge.zoho.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CrmQuoteDto {

    //Quote information
    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("Quote_Stage")
    private String quoteStage;

    @JsonProperty("Team")
    private String team;

    @JsonProperty("Carrier")
    private String carrier;

    @JsonProperty("Currency")
    private Currency currency;

    @JsonProperty("Deal_Name")
    private Long dealName;

    @JsonProperty("Valid_Till")
    private LocalDate validUntil;

    @JsonProperty("Contact_Name")
    private Long contactName;

    @JsonProperty("Account_Name")
    private Long accountName;

    @JsonProperty("Owner")
    private Long owner;

    @JsonProperty("Quoted_Items")
    private List<CrmQuotedItemDto> quotedItems;

    @JsonProperty("Adjustment")
    private double adjustment;

    @JsonProperty("Tax")
    private double tax;

    @JsonProperty("Discount")
    private double discount;

    //address information;
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

    //terms and conditions
    @JsonProperty("Terms_and_Conditions")
    private String termsAndConditions;

    //description information
    @JsonProperty("Description")
    private String description;


    //custom fields

}
