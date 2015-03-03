package com.autelhome.multiroom.util;

import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class CorsFilterTest {

    private final CorsFilter testSubject = new CorsFilter();

    @Test
    public void doFilter() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        testSubject.doFilter(request, response, chain);

        verify(response, never()).addHeader(anyString(), anyString());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void doNotFilterNonHTTPRequest() throws Exception {
        final ServletRequest request = mock(ServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        testSubject.doFilter(request, response, chain);

        verify(response, never()).addHeader(anyString(), anyString());
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void doNotFilterNonHTTPResponse() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        testSubject.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void doFilterOptionsMethodWithOrigin() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Origin")).thenReturn("the origin");
        when(request.getMethod()).thenReturn("OPTIONS");

        testSubject.doFilter(request, response, chain);

        verify(response).addHeader("Access-Control-Allow-Origin", "*");
        verify(response).addHeader("Access-Control-Expose-Headers", "X-Cache-Date, X-Atmosphere-tracking-id");
        verify(response).addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST");
        verify(response).addHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Atmosphere-Framework, X-Cache-Date, X-Atmosphere-tracking-id, X-Atmosphere-Transport");
        verify(response).addHeader("Access-Control-Max-Age", "-1");

        verify(chain, times(1)).doFilter(request, response);
    }
}