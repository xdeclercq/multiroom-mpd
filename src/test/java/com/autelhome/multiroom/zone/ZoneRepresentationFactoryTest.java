package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZoneRepresentationFactoryTest {

    private final UriInfo uriInfo = mock(UriInfo.class);
    private final MultiroomNamespaceResolver multiroomNamespaceResolver = mock(MultiroomNamespaceResolver.class);
    private final ZoneRepresentationFactory testSubject = new ZoneRepresentationFactory(uriInfo, multiroomNamespaceResolver);

    @Test
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();


        final String baseURI = "http://myserver/api";
        final String selfLink = baseURI + "/zones/myZone";
        final String playerLink = selfLink + "/player";

        when(uriInfo.getBaseUriBuilder()).thenAnswer(new Answer<UriBuilder>() {
            @Override
            public UriBuilder answer(final InvocationOnMock invocation) throws Throwable {
                return UriBuilder.fromUri(baseURI);
            }
        });
        final String mrNamespace = "/mr/{rel}";
        when(multiroomNamespaceResolver.resolve()).thenReturn(mrNamespace);

        final String expected = representationFactory
                .withFlag(StandardRepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(selfLink)
                .withNamespace("mr", mrNamespace)
                .withProperty("name", "myZone")
                .withLink("mr:player", playerLink)
                .toString(RepresentationFactory.HAL_JSON);

        final Zone zone = new Zone("myZone");
        final String actual = testSubject.newRepresentation(zone).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}