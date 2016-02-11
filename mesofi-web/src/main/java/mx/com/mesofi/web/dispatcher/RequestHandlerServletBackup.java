/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.dispatcher;

import static mx.com.mesofi.framework.error.ErrorType.BUSINESS;
import static mx.com.mesofi.framework.error.ErrorType.DEFAULT;
import static mx.com.mesofi.framework.error.ErrorType.FATAL;
import static mx.com.mesofi.framework.error.ErrorType.FRAMEWORK;
import static mx.com.mesofi.services.util.CommonConstants.SLASH_CHAR;
import static mx.com.mesofi.services.util.SimpleCommonActions.fromNullToCustomValue;
import static mx.com.mesofi.web.response.ErrorResponse.FATAL_MSG;
import static mx.com.mesofi.web.util.WebUtils.DEFAULT_ACTION;
import static mx.com.mesofi.web.util.WebUtils.extractBeanName;
import static mx.com.mesofi.web.util.WebUtils.extractMethodName;
import static mx.com.mesofi.web.util.WebUtils.getAnnotations;
import static mx.com.mesofi.web.util.WebUtils.validateActionRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.mesofi.framework.context.SpringContext;
import mx.com.mesofi.framework.error.ErrorTracerService;
import mx.com.mesofi.framework.error.ErrorTracerVo;
import mx.com.mesofi.framework.error.ErrorType;
import mx.com.mesofi.framework.error.FrameworkException;
import mx.com.mesofi.framework.error.ProcessRulesException;
import mx.com.mesofi.framework.error.ValidationBusinessException;
import mx.com.mesofi.web.request.PageParameters;
import mx.com.mesofi.web.request.RequestHandleException;
import mx.com.mesofi.web.response.ConversionFormatException;
import mx.com.mesofi.web.response.ErrorBusinessResponse;
import mx.com.mesofi.web.response.ErrorResponse;
import mx.com.mesofi.web.response.ResponseHandleException;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.response.types.HeaderResponse;
import mx.com.mesofi.web.response.types.JsonResponse;
import mx.com.mesofi.web.response.types.StreamResponse;
import mx.com.mesofi.web.stereotype.GET;
import mx.com.mesofi.web.stereotype.POST;
import mx.com.mesofi.web.stereotype.Path;
import mx.com.mesofi.web.util.MediaType;
import mx.com.mesofi.web.util.WebContext;
import mx.com.mesofi.web.util.WebUtils;

import org.apache.log4j.Logger;

/**
 * Main Servlet that receives all the incoming request from the client. Once the
 * request has come, this servlet delegates its functionality to the class that
 * makes the real action.
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
//@WebServlet(urlPatterns = "*" + DEFAULT_ACTION)
@WebServlet(urlPatterns = "/ss")
public class RequestHandlerServletBackup extends HttpServlet {

    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(RequestHandlerServletBackup.class);

    /**
     * Required parameter which decides the method to invoke.
     */
    private final String PARAMETER_NAME_ACTION = "action";

    /**
     * Optional parameter in header to determine which format to return.
     */
    private final String HEADER_NAME_ACCEPT = "accept";

    /**
     * Optional parameter in header to state whether the request was sent
     * asynchronously or not.
     */
    private final String HEADER_NAME_ASYNC = "async";

    /**
     * serial.
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] response = handleWebRequest(POST.class, req);
        handleWebResponse(response, req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] response = handleWebRequest(GET.class, req);
        handleWebResponse(response, req, resp);
    }

    /**
     * Handle the actual request coming from the client.
     * 
     * @param requiredHttpMethod
     *            The HTTP method to be invoked.
     * @param req
     *            The request.
     * @return The response.
     */
    private String[] handleWebRequest(Class<? extends Annotation> requiredHttpMethod, HttpServletRequest req) {
        if (log.isDebugEnabled()) {
            log.debug("Requesting... " + req.getRequestURI());
        }
        String[] fullResponse = null;
        Object response = null;
        SpringContext springContext = SpringContext.getInstance();
        String accept = getRequestHeader(req, HEADER_NAME_ACCEPT);
        String async = getRequestHeader(req, HEADER_NAME_ASYNC);
        String headerActionValue = getRequestHeader(req, PARAMETER_NAME_ACTION);
        String parameterActionValue = req.getParameter(PARAMETER_NAME_ACTION);

        try {
            // when value in header exists then it is taken as a main parameter
            if (!headerActionValue.isEmpty()) {
                parameterActionValue = headerActionValue;
            }

            validateActionRequest(parameterActionValue, PARAMETER_NAME_ACTION);
            String beanName = extractBeanName(parameterActionValue);
            String methodName = extractMethodName(parameterActionValue);

            // lookup the bean in SpringContext
            Object springBean = springContext.getContext().getBean(beanName);
            validatePathRequest(req, springBean.getClass());
            PageParameters pageParameters = new PageParameters(req.getParameterMap());
            try {
                Method method = springBean.getClass().getMethod(methodName, PageParameters.class);
                List<Class<? extends Annotation>> annotationsInMethod = getAnnotations(method, POST.class);
                boolean foundValidAnnotation = false;
                for (Class<? extends Annotation> annotationClass : annotationsInMethod) {
                    if (annotationClass.isAssignableFrom(requiredHttpMethod)) {
                        foundValidAnnotation = true;
                        break;
                    }
                }
                if (!foundValidAnnotation) {
                    throw new RequestHandleException("The method [" + method
                            + "] cannot handle this request because is not annotated with " + requiredHttpMethod
                            + " annotation");
                }
                // setups the HTTP request and some others attributes.
                WebContext.getInstance().setHttpRequest(req);

                // Performs the invocation to the controller.
                long init = System.currentTimeMillis();
                response = method.invoke(springBean, pageParameters);
                long end = System.currentTimeMillis();
                calculateTimeReqResp(init, end);

                if (response == null) {
                    throw new ResponseHandleException("Cannot return an empty response for the method [" + method
                            + "], the return type should be one compatible with: " + WebResponse.class);
                } else if (!(response instanceof WebResponse)) {
                    throw new ResponseHandleException("The method [" + method
                            + "] should have a return type compatible with [" + WebResponse.class + "]");
                } else {
                    fullResponse = createResponse((WebResponse) response, accept);
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                e.printStackTrace();
                throw new RequestHandleException(e.toString(), errors.toString());
            } catch (InvocationTargetException e) {
                // gets original exception
                Throwable t = e.getCause();
                if (t.getClass().isAssignableFrom(ValidationBusinessException.class)) {
                    throw (ValidationBusinessException) t;
                } else {
                    StringWriter errors = new StringWriter();
                    t.printStackTrace(new PrintWriter(errors));
                    t.printStackTrace();
                    String cause = null;
                    cause = t.getMessage() == null || t.getMessage().trim().isEmpty() ? FATAL_MSG : t.getMessage();
                    throw new ProcessRulesException(cause, errors.toString());
                }
            } catch (Throwable e) {
                throw new FrameworkException(e.toString(), getAndPrintFullTrace(e));
            }
        } catch (Exception e) {
            ErrorType errorType = FATAL;
            String trace = null;
            if (e instanceof RequestHandleException) {
                errorType = DEFAULT;
                trace = ((RequestHandleException) e).getDetailError();
            } else if (e instanceof ProcessRulesException) {
                // if message is null, error change to higher level.
                errorType = e.getMessage().contains(FATAL_MSG) ? FATAL : DEFAULT;
                trace = ((ProcessRulesException) e).getDetailError();
            } else if (e instanceof FrameworkException) {
                errorType = FRAMEWORK;
                trace = ((FrameworkException) e).getDetailError();
            } else if (e instanceof ValidationBusinessException) {
                errorType = BUSINESS;
                trace = "";
            } else {
                trace = getAndPrintFullTrace(e);
            }
            // saves any error in database.
            springContext.getContext().getBean(ErrorTracerService.class)
                    .saveErrorTrace(new ErrorTracerVo(errorType, e.getMessage(), trace));
            if (!async.isEmpty()) {
                // When the request is sent async, the response is JSON.
                if (errorType.equals(ErrorType.BUSINESS)) {
                    ValidationBusinessException businessException = (ValidationBusinessException) e;
                    response = new JsonResponse(new ErrorBusinessResponse(businessException.getFields()), false);
                } else {
                    response = new JsonResponse(new ErrorResponse(errorType, e.getMessage(), trace), false);
                }
                fullResponse = createResponse((WebResponse) response, accept);
            } else {
                throw e;
            }
        }
        return fullResponse;
    }

    /**
     * Get the full trace of the exception and it is printed in logs.
     * 
     * @param e
     *            Exception.
     * @return Trace.
     */
    private String getAndPrintFullTrace(Throwable e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        e.printStackTrace();
        return errors.toString();
    }

    /**
     * Validate the path specified in the request in order to match it with the
     * specified (if any) in the controller class that handle the incoming
     * request.
     * 
     * @param req
     *            The request.
     * @param clazz
     *            Class that receive the incoming request.
     */
    private void validatePathRequest(HttpServletRequest req, Class<? extends Object> clazz) {
        if (clazz.isAnnotationPresent(Path.class)) {
            String[] paths = clazz.getAnnotation(Path.class).value();
            boolean[] tests = new boolean[paths.length];
            String[] validUrls = new String[paths.length];

            String newPath = null;
            int i = 0;
            if (paths.length != 0) {
                for (String path : paths) {
                    if (!path.startsWith(SLASH_CHAR.toString())) {
                        path = SLASH_CHAR.toString() + path;
                    }
                    newPath = req.getServletContext().getContextPath() + path + DEFAULT_ACTION;
                    validUrls[i] = newPath;
                    tests[i] = newPath.equals(req.getRequestURI());
                    if (tests[i]) {
                        break;
                    }
                    i++;
                }
                boolean containError = true;
                for (boolean b : tests) {
                    if (b) {
                        containError = false;
                        break;
                    }
                }
                if (containError) {
                    List<String> list = new ArrayList<>();
                    for (String s : validUrls) {
                        list.add(s);
                    }
                    String msg = list.size() == 1 ? " Use the following path "
                            : " Use one of the following paths instead ";
                    throw new RequestHandleException("This request [" + req.getRequestURI()
                            + "] cannot be handled by class [" + clazz + "]. " + msg + list);
                }
            }
        }
    }

    /**
     * Creates the response to the client.
     * 
     * @param response
     *            Response and its type.
     * @param resp
     *            Response.
     * @throws IOException
     *             In case an exception occurs.
     * @throws ServletException
     *             In case an exception occurs.
     */
    private void handleWebResponse(String[] response, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        resp.setContentType(response[0]);
        if (resp.getContentType().equals(MediaType.TEXT_HTML)) {
            // forwards the page to another resource view.
            RequestDispatcher dispatcher = req.getRequestDispatcher(createResponseUrl(response[1]));
            dispatcher.include(req, resp);
        } else {
            PrintWriter printWriter = resp.getWriter();
            printWriter.print(response[1]);
            printWriter.close();
        }
    }

    /**
     * Creates the full response path to send the request.
     * 
     * @param viewIdentifier
     *            Identifier for the view.
     * @return path where the view is located.
     */
    private String createResponseUrl(String viewIdentifier) {
        return WebUtils.DEFAULT_VIEWS + viewIdentifier;
    }

    /**
     * Calculate and display the time that a incoming request takes from the
     * moment the caller makes the call until finish the process.
     * 
     * @param init
     *            Initial time.
     * @param end
     *            Final time.
     */
    private void calculateTimeReqResp(long init, long end) {
        double time = (end - init) / 1000d;
        if (log.isDebugEnabled()) {
            log.debug("The current invocation took about " + time + " seconds to execute");
        }
    }

    /**
     * According to the type of return, create the full response for the client
     * inside an array with only 2 elements. The first element contains the
     * content type and the second contains the actual value of the response.
     * 
     * @param webResponse
     *            Web response.
     * @param accept
     *            The value that the client is expecting to receive.
     * @return Array with the content of the response and the type.
     */
    private String[] createResponse(WebResponse webResponse, String accept) {
        String response[] = new String[2];
        try {
            if (webResponse instanceof StreamResponse) {
                response[0] = webResponse.getMediaType().toString();
            } else if (webResponse instanceof HeaderResponse) {
                webResponse = ((HeaderResponse) webResponse).processBasedOnHeader(accept);
                response[0] = accept;
                response[1] = webResponse.getResponse();
            } else {
                response[0] = webResponse.getMediaType().toString();
                response[1] = webResponse.getResponse();
            }
        } catch (ConversionFormatException e) {
            throw new ResponseHandleException(e.getMessage());
        }
        return response;
    }

    /**
     * Gets the value contained in certain header of the HTTP request, if such
     * property does not exist then returns an empty value, otherwise returns
     * the actual value.
     * 
     * @param req
     *            Request.
     * @param headerName
     *            Header name.
     * @return value of the header.
     */
    private String getRequestHeader(HttpServletRequest req, String headerName) {
        return fromNullToCustomValue(req.getHeader(headerName));
    }
}
