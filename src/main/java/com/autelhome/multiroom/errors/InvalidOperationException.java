package com.autelhome.multiroom.errors;

/**
 * {@link RuntimeException} for invalid operations.
 *
 * @author xdeclercq
 */
public class InvalidOperationException extends RuntimeException
{

    /**
     * Constructs a new invalid operation exception with the specifed detail message.
     *
     * @param message a message
     */
    public InvalidOperationException(final String message) {
        super(message);
    }
}
