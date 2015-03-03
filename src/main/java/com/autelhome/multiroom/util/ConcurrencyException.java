package com.autelhome.multiroom.util;

/**
 * {@link RuntimeException} for concurrency operations.
 *
 * @author xdeclercq
 */
public class ConcurrencyException extends RuntimeException
{

    /**
     * Constructs a new concurrency exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ConcurrencyException(final String message) {
        super(message);
    }

}
