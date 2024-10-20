package com.bridge.zoho.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Details {
    @JsonProperty("Modified_Time")
    private String modifiedTime;

    @JsonProperty("Modified_By")
    private User modifiedBy;

    @JsonProperty("Created_Time")
    private String createdTime;

    private String id;

    @JsonProperty("Created_By")
    private User createdBy;

    @JsonProperty("$approval_state")
    private String approvalState;
}
