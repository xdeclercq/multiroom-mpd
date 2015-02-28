package com.autelhome.multiroom.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to allow cross-origin requests.
 *
 * @author xdeclercq
 */
public class CorsFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException
    {
        if (!(request instanceof HttpServletRequest) || !(response instanceof  HttpServletResponse)) {
            return;
        }

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        if (req.getHeader("Origin") != null) {
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Expose-Headers", "X-Cache-Date, X-Atmosphere-tracking-id");
        }

        if ("OPTIONS".equals(req.getMethod())) {
            res.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST");
            res.addHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Atmosphere-Framework, X-Cache-Date, X-Atmosphere-tracking-id, X-Atmosphere-Transport");
            res.addHeader("Access-Control-Max-Age", "-1");
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // do nothing
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // do nothing
    }
}
