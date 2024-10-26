package com.bridge.zoho.dto;

import com.bridge.zoho.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CrmInvoiceDto {
    //Invoice Information

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("Invoice_Date")
    private LocalDate invoiceDate;

    @JsonProperty("Due_Date")
    private LocalDate dueDate;

    @JsonProperty("Sales_Commission")
    private double salesCommission;

    @JsonProperty("Account_Name")
    private Long accountName;

    @JsonProperty("Contact_Name")
    private Long contactName;

    @JsonProperty("Deal_Name__s")
    private Long dealName;

    @JsonProperty("Currency")
    private Currency currency;

    @JsonProperty("Sales_Order")
    private Long salesOrder;

    @JsonProperty("Purchase_Order")
    private String purchaseOrder;

    @JsonProperty("Excise_Duty")
    private double exciseDuty;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Owner")
    private Long owner;

    @JsonProperty("Adjustment")
    private double adjustment;

    @JsonProperty("Tax")
    private double tax;

    @JsonProperty("Discount")
    private double discount;


    @JsonProperty("Invoiced_Items")
    private List<CrmInvoicedItemDto> invoicedItems;

    //Address Information
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

    //Terms and Conditions
    @JsonProperty("Terms_and_Conditions")
    private String termsAndConditions;

    //Description Information
    @JsonProperty("Description")
    private String description;

    //custom fields
}
