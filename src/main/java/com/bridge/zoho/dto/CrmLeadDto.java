package com.bridge.zoho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrmLeadDto {

    //Lead information
    @JsonProperty("First_Name")
    private String firstName;

    @JsonProperty("Last_Name")
    private String lastName;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Secondary_Email")
    private String secondaryEmail;

    @JsonProperty("Company")
    private String company;

    @JsonProperty("Lead_Source")
    private String leadSource;

    @JsonProperty("Owner")
    private long owner;

    @JsonProperty("Salutation")
    private String salutation;

    @JsonProperty("Designation")
    private String title;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Mobile")
    private String mobile;

    @JsonProperty("Industry")
    private String industry;

    @JsonProperty("Annual_Revenue")
    private double annualRevenue;

    @JsonProperty("Rating")
    private String rating;

    @JsonProperty("Email_Opt_Out")
    private boolean emailOptOut;

    @JsonProperty("Twitter")
    private String twitter;

    @JsonProperty("Skype_ID")
    private String skypeId;

    @JsonProperty("No_of_Employees")
    private int numberOfEmployees;

    @JsonProperty("Lead_Status")
    private String leadStatus;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Fax")
    private String fax;

    //Address information
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

    //Description information
    @JsonProperty("Description")
    private String description;

    //custom fields

}
