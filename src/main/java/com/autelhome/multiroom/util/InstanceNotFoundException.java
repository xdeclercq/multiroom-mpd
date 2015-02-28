package com.autelhome.multiroom.util;

/**
 * {@link RuntimeException} for instance not found in database.
 *
 * @author xdeclercq
 */
public class InstanceNotFoundException extends RuntimeException
{

    /**
     * Constructs a new instance not found exception with the specified detail message.
     *
     * @param message the detail message
     */
    public InstanceNotFoundException(final String message) {
        super(message);
    }

}
