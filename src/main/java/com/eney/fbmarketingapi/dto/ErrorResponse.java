package com.eney.fbmarketingapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String originalMessage;
    private String userDefineMessage;
    private String requestURL;


    @Builder
    public ErrorResponse(String originalMessage, String userDefineMessage, String requestURL) {
        this.originalMessage = originalMessage;
        this.userDefineMessage = userDefineMessage;
        this.requestURL = requestURL;
    }
}
