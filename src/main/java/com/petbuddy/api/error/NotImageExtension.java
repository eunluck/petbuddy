package com.petbuddy.api.error;

import com.petbuddy.api.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class NotImageExtension extends ServiceRuntimeException {
    static final String MESSAGE_KEY = "error.file.notimage";

    static final String MESSAGE_DETAILS = "error.file.notimage.details";

    public NotImageExtension(String targetName) {
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
