package com.autelhome.multiroom.util;

/**
 * {@link RuntimeException} for instance already present in database.
 *
 * @author xdeclercq
 */
public class InstanceAlreadyPresentException extends RuntimeException {

    /**
     * Constructs a new instance already present exception with the specified detail message.
     *
     * @param message the detail message
     */
    public InstanceAlreadyPresentException(final String message) {
        super(message);
    }

}
