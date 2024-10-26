package com.bridge.zoho.dto;

import com.bridge.zoho.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CrmSalesOrderDto {

    //Sales Order Information
    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("Customer_No")
    private String customerNo;

    @JsonProperty("Quote_Name")
    private Long quoteName;

    @JsonProperty("Pending")
    private String pending;

    @JsonProperty("Carrier")
    private String carrier;

    @JsonProperty("Sales_Commission")
    private double salesCommission;

    @JsonProperty("Account_Name")
    private Long accountName;

    @JsonProperty("Deal_Name")
    private Long dealName;

    @JsonProperty("Purchase_Order")
    private String purchaseOrder;

    @JsonProperty("Due_Date")
    private LocalDate dueDate;

    @JsonProperty("Contact_Name")
    private Long contactName;

    @JsonProperty("Excise_Duty")
    private double exciseDuty;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Currency")
    private Currency currency;

    @JsonProperty("Owner")
    private Long owner;

    @JsonProperty("Adjustment")
    private double adjustment;

    @JsonProperty("Tax")
    private double tax;

    @JsonProperty("Discount")
    private double discount;

    @JsonProperty("Ordered_Items")
    private List<CrmOrderedItemDto> orderedItems;

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
