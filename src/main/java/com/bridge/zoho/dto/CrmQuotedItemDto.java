package com.bridge.zoho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrmQuotedItemDto {

    @JsonProperty("Product_Name")
    private Long productName;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Quantity")
    private int quantity;

    @JsonProperty("List_Price")
    private double listPrice;

    @JsonProperty("Discount")
    private double discount;

    @JsonProperty("Tax")
    private double tax;

}
