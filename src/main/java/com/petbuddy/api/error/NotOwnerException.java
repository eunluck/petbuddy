package com.petbuddy.api.error;

import com.petbuddy.api.util.MessageUtils;

public class NotOwnerException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.notowner";

    static final String MESSAGE_DETAILS = "error.notowner.details";

    public NotOwnerException(String targetName) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new Object[]{targetName});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getMessage(getMessageKey());
    }
}
