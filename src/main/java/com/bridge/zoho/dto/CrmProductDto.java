package com.bridge.zoho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CrmProductDto {

    //product information
    @JsonProperty("Product_Name")
    private String productName;

    @JsonProperty("Product_Category")
    private String productCode;

    @JsonProperty("Product_Active")
    private boolean productActive;

    @JsonProperty("Product_Category")
    private String productCategory;

    @JsonProperty("Sales_End_Date")
    private LocalDate salesEndDate;

    @JsonProperty("Support_Expiry_Date")
    private LocalDate supportEndDate;

    @JsonProperty("Vendor_Name")
    private Long vendorName;

    @JsonProperty("Manufacturer")
    private String manufacturer;

    @JsonProperty("Sales_Start_Date")
    private LocalDate salesStartDate;

    @JsonProperty("Support_Start_Date")
    private LocalDate supportStartDate;

    @JsonProperty("Owner")
    private Long owner;

    //price information

    @JsonProperty("Unit_Price")
    private double unitPrice;

    @JsonProperty("Commission_Rate")
    private double commissionRate;

    @JsonProperty("Taxable")
    private boolean taxable;

    @JsonProperty("Tax")
    private List<String> tax;

    //stock information

    @JsonProperty("Usage_Unit")
    private String usageUnit;

    @JsonProperty("Qty_in_Stock")
    private long quantityInStock;

    @JsonProperty("Handler")
    private Long handler;

    @JsonProperty("Qty_Ordered")
    private long qtyOrdered;

    @JsonProperty("Reorder_Level")
    private long reorderLevel;

    @JsonProperty("Qty_in_Demand")
    private long quantityInDemand;

    //description information

    @JsonProperty("Description")
    private String description;

    //custom fields
}
