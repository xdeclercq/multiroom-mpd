package com.autelhome.multiroom.errors;

/**
 * {@link ToClientException} for resource not found.
 *
 * @author xdeclercq
 */
public class ResourceNotFoundException extends ToClientException {

    public static final String MESSAGE_FORMAT = "The '%s' %s was not found";

    /**
     * Constructs a new resource not found exception with the specified resource id and type.
     *
     * @param resourceType the resource type
     * @param resourceId the resource id
     */
    public ResourceNotFoundException(final String resourceType, final String resourceId) {
        super(ErrorCode.RESOURCE_NOT_FOUND, String.format(MESSAGE_FORMAT, resourceId, resourceType));
    }
}
