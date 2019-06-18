package com.eney.fbmarketingapi.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDefineException extends RuntimeException {

    private String originalMessage;
    private String errorMethod;

    @Builder
    public UserDefineException(String message, String originalMessage, String errorMethod) {
        super(message);
        this.originalMessage = originalMessage;
        this.errorMethod = errorMethod;
    }
}
