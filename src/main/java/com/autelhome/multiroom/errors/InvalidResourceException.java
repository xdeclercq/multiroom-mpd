package com.autelhome.multiroom.errors;

/**
 * {@link ToClientException} for invalid resource.
 *
 * @author xdeclercq
 */
public class InvalidResourceException extends ToClientException
{

    public static final String MESSAGE_FORMAT = "The '%s' %s was not found";

    /**
     * Constructs a new invalid resource exception with the specified resource id and type.
     *
     * @param message an error message
     */
    public InvalidResourceException(final String message) {
        super(ErrorCode.INVALID_RESOURCE, message);
    }
}
