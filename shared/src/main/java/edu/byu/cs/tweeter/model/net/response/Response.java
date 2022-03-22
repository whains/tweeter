package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;

class Response implements Serializable {

    private final boolean success;
    private final String errorMessage;

    Response(boolean success) {
        this(success, null);
    }

    Response(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return errorMessage;
    }
}
