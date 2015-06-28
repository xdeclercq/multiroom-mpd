package com.autelhome.multiroom.errors;

import java.util.Locale;

/**
 * Error code.
 *
 * @author xdeclercq
 */
public enum ErrorCode {
    RESOURCE_NOT_FOUND("Resource Not Found", 404), INVALID_RESOURCE("Invalid Resource", 500), UNKNOWN_ERROR("Unknown Error", 500);


    private final String label;
    private final int statusCode;

    /**
     * Constructor.
     *
     * @param label the error label
     * @param statusCode the status code
     */
    private ErrorCode(final String label, final int statusCode) {
        this.label = label;
        this.statusCode = statusCode;
    }

    public String getLabel() {
        return label;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the label to be put in a URL.
     *
     * @return the label to be put in a URL
     */
    public String getURLabel() {
        return label.toLowerCase(Locale.UK).replaceAll(" ", "-");
    }
}
