package com.bridge.zoho.dto;

import com.bridge.zoho.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrmDealDto {

    //deal information

    @JsonProperty("Deal_Name")
    private String dealName;

    @JsonProperty("Account_Name")
    private Long accountName;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Lead_Source")
    private String leadSource;

    @JsonProperty("Contact_Name")
    private Long contactName;

    @JsonProperty("Amount")
    private double amount;

    @JsonProperty("Closing_Date")
    private LocalDate closingDate;

    @JsonProperty("Stage")
    private String stage;

    @JsonProperty("Probability")
    private int probability;

    @JsonProperty("Reason_For_Loss__s")
    private String reasonForLoss;

    @JsonProperty("Next_Step")
    private String nextStep;

    @JsonProperty("Owner")
    private Long owner;

    @JsonProperty("Currency")
    private Currency currency;

    //description

    @JsonProperty("Description")
    private String description;

    //custom fields

}
