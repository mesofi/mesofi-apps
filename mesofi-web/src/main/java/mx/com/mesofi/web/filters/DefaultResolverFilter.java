/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.filters;

import static mx.com.mesofi.services.util.CommonConstants.SLASH_CHAR;
import static mx.com.mesofi.web.util.WebUtils.isStaticResource;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Filter that receives all the incoming requests from the client, when the
 * client does not specify any path for the request, then it is forwarded to the
 * default page. (e.g. index.jsp)
 * 
 * @author Armando Rivas.
 * @since Oct 25 2013
 */
@WebFilter(urlPatterns = { "/*" })
public class DefaultResolverFilter implements Filter {

    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(DefaultResolverFilter.class);

    /**
     * Default page when no path is specify.
     */
    private static String DEFAULT_WELCOME = "index.jsp";

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        if (request instanceof HttpServletRequest) {
            String url = ((HttpServletRequest) request).getRequestURL().toString();
            String contextPath = request.getServletContext().getContextPath();

            if (isStaticResource(url)) {
                filterChain.doFilter(request, response);
            } else if (url.endsWith(contextPath + SLASH_CHAR.toString())) {
                // forwards to the default page.
                if (log.isDebugEnabled()) {
                    log.debug("Requesting the default page: [" + DEFAULT_WELCOME + "]...");
                }
                request.getRequestDispatcher(DEFAULT_WELCOME).forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            throw new IllegalStateException("Cannot handle this type of request...");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {

    }

}
