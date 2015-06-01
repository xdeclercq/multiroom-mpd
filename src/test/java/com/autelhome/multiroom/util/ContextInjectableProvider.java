package com.autelhome.multiroom.util;

import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

import javax.ws.rs.core.Context;
import java.lang.reflect.Type;

/**
 * Provider to be able to inject context fields.
 *
 * @author xdeclercq
 */
public class ContextInjectableProvider<T> extends SingletonTypeInjectableProvider<Context, T> {
    public ContextInjectableProvider(final Type type, final T instance) {
        super(type, instance);
    }
}
