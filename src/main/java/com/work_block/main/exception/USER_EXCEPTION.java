package com.work_block.main.exception;

public enum USER_EXCEPTION {
    EXCEPTION_CREATE_USER("USER_001", "Can't create user"),
    MISSING_FIELDS("USER_002", "Required fields are missing."),
    ;

    private final String errorCode;
    private final String message;

    USER_EXCEPTION(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
