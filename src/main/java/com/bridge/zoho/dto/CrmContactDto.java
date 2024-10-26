package com.bridge.zoho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrmContactDto {

    //contact information
    @JsonProperty("Owner")
    private Long contactOwner;

    @JsonProperty("First_Name")
    private String firstName;

    @JsonProperty("Salutation")
    private String salutation;

    @JsonProperty("Department")
    private String department;

    @JsonProperty("Home_Phone")
    private String homePhone;

    @JsonProperty("Fax")
    private String fax;

    @JsonProperty("Date_of_Birth")
    private LocalDate dateOfBirth;

    @JsonProperty("Asst_Phone")
    private String asstPhone;

    @JsonProperty("Email_Opt_Out")
    private boolean emailOptOut;

    @JsonProperty("Lead_Source")
    private String source;

    @JsonProperty("Last_Name")
    private String lastName;

    @JsonProperty("Account_Name")
    private Long accountName;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Other_Phone")
    private String otherPhone;

    @JsonProperty("Mobile")
    private String mobile;

    @JsonProperty("Assistant")
    private String assistant;

    @JsonProperty("Skype_ID")
    private String skypeId;

    @JsonProperty("Secondary_Email")
    private String secondaryEmail;

    @JsonProperty("Twitter")
    private String twitter;

    @JsonProperty("Reporting_To")
    private long reportingTo;

    //address information
    @JsonProperty("Mailing_Street")
    private String mailingStreet;

    @JsonProperty("Mailing_City")
    private String mailingCity;

    @JsonProperty("Mailing_State")
    private String mailingState;

    @JsonProperty("Mailing_Zip")
    private String mailingZip;

    @JsonProperty("Mailing_Country")
    private String mailingCountry;

    @JsonProperty("Other_Street")
    private String otherStreet;

    @JsonProperty("Other_City")
    private String otherCity;

    @JsonProperty("Other_State")
    private String otherState;

    @JsonProperty("Other_Zip")
    private String otherZip;

    @JsonProperty("Other_Country")
    private String otherCountry;

    //description information
    @JsonProperty("Description")
    private String description;

    //custom fields
}
