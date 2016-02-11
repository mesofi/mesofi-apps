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

import static mx.com.mesofi.framework.util.FrameworkUtils.SAVE_REQ_RESP;
import static mx.com.mesofi.web.util.WebUtils.DEFAULT_ACTION;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.mesofi.framework.log.LoggerReqRespService;
import mx.com.mesofi.framework.log.LoggerReqRespVo;
import mx.com.mesofi.framework.util.FrameworkUtils;
import mx.com.mesofi.web.response.LoggerServletResponse;
import mx.com.mesofi.web.util.WebUtils;

import org.apache.log4j.Logger;

/**
 * This filter saves all the requests coming from the application. The response
 * are also saved as well.
 * 
 * @author Armando Rivas
 * @since 12.05.2013
 */
@WebFilter(urlPatterns = "*" + DEFAULT_ACTION)
public class RequestLoggerFilter implements Filter {

    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(RequestLoggerFilter.class);

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        String saveLog = FrameworkUtils.getPreferenceValue(SAVE_REQ_RESP);
        LoggerReqRespService service = null;
        LoggerReqRespVo logger = null;
        long lastSaved = 0;
        if (Boolean.valueOf(saveLog)) {
            if (log.isDebugEnabled()) {
                log.debug("Saving the current request...");
            }
            try {
                service = (LoggerReqRespService) FrameworkUtils.getSpringBeanFromContext("loggerReqRespServiceImpl");
                logger = createLoggerRequest((HttpServletRequest) request);
                lastSaved = service.saveReqRespo(true, logger);
            } catch (Exception exception) {
                // this exception is silenced
                exception.printStackTrace();
            }
        }
        // creates a custom response to be intercepted.
        LoggerServletResponse wrapperResponse = new LoggerServletResponse((HttpServletResponse) response);
        chain.doFilter(request, wrapperResponse);
        // Handle the response...
        if (Boolean.valueOf(saveLog)) {
            if (log.isDebugEnabled()) {
                log.debug("Request has been saved sucessfull");
            }
            try {
                if (service != null && lastSaved != 0) {
                    logger = createLoggerResponse(wrapperResponse);
                    logger.setRequestId(lastSaved);
                    service.saveReqRespo(false, logger);
                    if (log.isDebugEnabled()) {
                        log.debug("Response has been saved sucessfull");
                    }
                }
            } catch (Exception exception) {
                // this exception is silenced
                exception.printStackTrace();
            }
        }
    }

    /**
     * Creates a logger object to be stored, this object is populated from the
     * response, these information is related to the response status etc.
     * 
     * @param response
     *            Full response.
     * @return Logger for the response.
     */
    private LoggerReqRespVo createLoggerResponse(LoggerServletResponse response) {
        LoggerReqRespVo vo = new LoggerReqRespVo();
        vo.setServerContentType(response.getContentType());
        vo.setServerStatus(response.getStatus());

        vo.setHeaders(WebUtils.extractAllHeaders(response));
        vo.setResponseBody(response.getContent());
        return vo;
    }

    /**
     * Creates a logger object to be stored, this object is populated from the
     * request, these information is related to the header request, parameters
     * etc.
     * 
     * @param request
     *            Incoming request.
     * @return Logger for the request.
     */
    private LoggerReqRespVo createLoggerRequest(HttpServletRequest request) {
        LoggerReqRespVo vo = new LoggerReqRespVo();
        vo.setClientHost(request.getRemoteHost());
        vo.setClientAddr(request.getRemoteAddr());
        vo.setClientPort(request.getRemotePort());

        vo.setHttpMethod(request.getMethod());
        vo.setHttpRequest(request.getRequestURI());
        vo.setHttpContentType(request.getContentType());
        vo.setHttpContentLength(request.getContentLength());

        vo.setHeaders(WebUtils.extractAllHeaders(request));
        vo.setRequestBody(WebUtils.extractAllBody(request));

        return vo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }

}
