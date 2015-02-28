package com.autelhome.multiroom.errors;

/**
 * Exception with an associated error code to be sent to client.
 *
 * @author xdeclercq
 */
public class ToClientException extends RuntimeException {

    private final ErrorCode errorCode;

    /**
     * Constructor.
     *
     * @param errorCode the error code
     * @param message the detail message
     */
    public ToClientException(final ErrorCode errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the status code related to this exception.
     *
     * @return the status code related to this exception
     */
    public int getStatusCode() {
        return errorCode.getStatusCode();
    }
}
