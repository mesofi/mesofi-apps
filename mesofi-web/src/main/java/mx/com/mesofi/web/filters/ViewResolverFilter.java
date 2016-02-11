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
 * Filter that receives all the incoming requests from the client only for JSP's
 * without doing any pre process before showing them.
 * 
 * @author Armando Rivas.
 * @since Oct 25 2013
 */
@WebFilter(urlPatterns = { "*.jsp" })
public class ViewResolverFilter implements Filter {

    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(ViewResolverFilter.class);

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
            String jspPage = ((HttpServletRequest) request).getServletPath();
            // forwards the corresponding JSP.
            if (log.isDebugEnabled()) {
                log.debug("Requesting the page: [" + jspPage + "]...");
            }
            filterChain.doFilter(request, response);
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
