package com.autelhome.multiroom.util;

import com.google.inject.Injector;

/**
 * Util class to retrieve the guice injector.
 *
 * @author xdeclercq
 */
public final class InjectorLookup {

    private static Injector injector;

    private InjectorLookup() {
        // empty
    }

    /**
     * Sets the injector.
     *
     * @param injector an injector
     */
    public static void setInjector(final Injector injector) {
        InjectorLookup.injector = injector;
    }

    /**
     * Returns the injector.
     *
     * @return the injector
     */
    public static Injector getInjector() {
        return injector;
    }
}
