package com.autelhome.multiroom.errors;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorRepresentationFactoryTest {

    private static final URI REQUEST_URI = URI.create("http://myserver:5678/the/path/to/some/resource");
    private final ErrorRepresentationFactory testSubject = new ErrorRepresentationFactory(REQUEST_URI);

    @Test
    public void newRepresentation() throws Exception {
        final ErrorCode errorCode = ErrorCode.INVALID_RESOURCE;


        final String mrNamespace = "http://myserver:5678/multiroom-mpd/docs/#/relations/{rel}";
        final String errorInfo = "http://myserver:5678/multiroom-mpd/docs/#/errors/invalid-resource";

        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
        final String message = "A small message";

        final String expected = representationFactory
                .withFlag(StandardRepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation()
                .withNamespace("mr", mrNamespace)
                .withProperty("errorCode", errorCode.getLabel())
                .withProperty("message", message)
                .withLink("mr:error-info", errorInfo)
                .toString(RepresentationFactory.HAL_JSON);

        final String actual = testSubject.newRepresentation(errorCode, message).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }

}