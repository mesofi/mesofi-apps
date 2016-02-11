/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.util;

import static mx.com.mesofi.services.util.CommonConstants.PIPE_CHAR;
import static mx.com.mesofi.services.util.CommonConstants.UNDER_SCORE;
import static mx.com.mesofi.services.util.SimpleCommonActions.fromUrlFormatToMethodFormat;
import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility that contains severals methods to deal with the parameters from the
 * request and makes some transformations.
 * 
 * @author Armando Rivas
 * @since Oct 02 2013
 */
public final class WebUtils {
    /**
     * Token.
     */
    private static final String TOKEN = ":";

    /**
     * Default action for the framework.
     */
    public static final String DEFAULT_ACTION = ".msf";

    /**
     * Default folder for the views.
     */
    public static final String DEFAULT_VIEWS = "/WEB-INF/jsp/";

    /**
     * Default extensions for static resources.
     */
    private static final String[] DEFAULT_STATIC_EXTENSIONS = { ".css", ".js", ".png", ".jpg" };

    /**
     * Avoid the creation of new objects.
     */
    private WebUtils() {

    }

    /**
     * Validates the action parameter, if the parameter is null then throws a
     * runtime exception. The action should be specified as follows.
     * <p>
     * If the method to execute is <code>someMethod</code> in the class
     * <code>CustomRequest</code>, then the action parameter should have the
     * following format.
     * <p>
     * action=custom-request#some-method.
     * 
     * @param actionValue
     *            Action to be validated.
     * @param parameterName
     *            The parameter name,
     */
    public static void validateActionRequest(final String actionValue, final String parameterName) {
        if (actionValue == null) {
            throw new IllegalStateException("Could not find a parameter called \"" + parameterName
                    + "\" in the request or header, "
                    + "please define it and make sure it has the following format [bean:methodToExecute]");
        }
    }

    /**
     * Given a {@link String} and its value is
     * <code>employer-action#validate-code</code>, the result for this method
     * would be <code>employerAction</code>
     * 
     * @param actionValue
     *            Value to be evaluated.
     * @return The spring bean format.
     * @see #extractMethodName(String)
     */
    public static String extractBeanName(final String actionValue) {
        isEmpty(actionValue, "Cannot extract the bean name because the value for the \"action\" parameter is empty");
        int index = hasValidToken(actionValue, "Cannot find the bean name because there is not a limiter "
                + "in the \"action\" parameter, this delimiter must exist and specified as \"" + TOKEN + "\"");
        String beanName = actionValue.substring(0, index);
        return fromUrlFormatToMethodFormat(beanName);
    }

    /**
     * Given a {@link String} and its value is
     * <code>employer-action#validate-code</code>, the result for this method
     * would be <code>validateCode</code>
     * 
     * @param actionValue
     *            Value to be evaluated.
     * @return the method name in Java format.
     * @see #extractBeanName(String)
     */
    public static String extractMethodName(final String actionValue) {
        isEmpty(actionValue, "Cannot extract the method name because the value for the \"action\" parameter is empty");
        int index = hasValidToken(actionValue, "Cannot find the method name because there is not a limiter "
                + "in the \"action\" parameter, this delimiter must exist and specified as \"" + TOKEN + "\"");
        String methodName = actionValue.substring(index + 1);
        return fromUrlFormatToMethodFormat(methodName);
    }

    /**
     * This method converts any {@link String} in format <code>SOME_NAME</code>
     * into a new {@link String} in a label format (including spaces).
     * 
     * @param erFormat
     *            ER format.
     * @return The same string converted. For example, for the following
     *         {@link String} <code>SOME_SIMPLE_STRING</code> is converted into
     *         <code>some simple string</code>
     */
    public static String fromERFormatToLabelFormat(String erFormat) {
        StringBuilder sb = new StringBuilder();
        erFormat = erFormat.toLowerCase();

        String[] parts = erFormat.split(UNDER_SCORE.toString());
        for (String part : parts) {
            sb.append(part).append(" ");
        }
        return sb.toString().trim();
    }

    private static int hasValidToken(String actionValue, String errorMessage) {
        int index = actionValue.indexOf(TOKEN);
        if (index == -1) {
            throw new IllegalStateException(errorMessage);
        }
        return index;
    }

    /**
     * Gets all the declared annotations for a particular method, if the method
     * is not annotated, then adds a default annotation into it, hence this
     * method returns at least one element.
     * 
     * @param method
     *            Annotated method.
     * @param defaultAnnotation
     *            Default annotation to be added.
     * @return List of all the annotations associated to the method.
     */
    public static List<Class<? extends Annotation>> getAnnotations(Method method,
            Class<? extends Annotation> defaultAnnotation) {
        List<Class<? extends Annotation>> list = new ArrayList<Class<? extends Annotation>>();
        list.add(defaultAnnotation);
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (!annotation.annotationType().isAssignableFrom(defaultAnnotation)) {
                list.add(annotation.annotationType());
            }
        }
        return list;
    }

    /**
     * Test whether a URL may be a resource or not. The decision is taken taking
     * as a base the most known extensions in a URL.
     * 
     * @param url
     *            URL from the request.
     * @return true, is a static resource, otherwise false.
     */
    public static boolean isStaticResource(String url) {
        boolean staticResource = false;
        for (String resources : DEFAULT_STATIC_EXTENSIONS) {
            if (url.endsWith(resources)) {
                staticResource = true;
                break;
            }
        }
        return staticResource;
    }

    /**
     * Extracts from the request all the headers and stores them into a
     * {@link Properties} object to handle them easily.
     * 
     * @param request
     *            HttpRequest.
     * @return All headers, if no headers are found then returns an not null
     *         object but empty.
     */
    public static Properties extractAllHeaders(HttpServletRequest request) {
        Properties properties = new Properties();
        Enumeration<String> headerNames = request.getHeaderNames();
        Enumeration<String> valueNames = null;
        String headerName = null;
        String headerValue = "";

        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            valueNames = request.getHeaders(headerName);
            while (valueNames.hasMoreElements()) {
                headerValue = valueNames.nextElement() + PIPE_CHAR + headerValue;
            }
            headerValue = headerValue.substring(0, headerValue.length() - PIPE_CHAR.toString().length());
            properties.setProperty(headerName, headerValue);
            headerValue = "";
        }
        return properties;
    }

    /**
     * Extracts from the response all the headers and stores them into a
     * {@link Properties} object to handle them easily.
     * 
     * @param response
     *            HttpResponse.
     * @return All headers, if no headers are found then returns an not null
     *         object but empty.
     */
    public static Properties extractAllHeaders(HttpServletResponse response) {
        Properties properties = new Properties();
        Collection<String> headerNames = response.getHeaderNames();
        Collection<String> valueNames = null;
        String headerValue = "";

        for (String headerName : headerNames) {
            valueNames = response.getHeaders(headerName);
            for (String next : valueNames) {
                headerValue = next + PIPE_CHAR + headerValue;
            }
            headerValue = headerValue.substring(0, headerValue.length() - PIPE_CHAR.toString().length());
            properties.setProperty(headerName, headerValue);
            headerValue = "";
        }
        return properties;
    }

    public static Properties extractAllBody(HttpServletRequest request) {
        Properties properties = new Properties();
        Enumeration<String> paramNames = request.getParameterNames();
        String[] paramValues = null;
        String paramName = null;
        String paramValue = "";

        while (paramNames.hasMoreElements()) {
            paramName = paramNames.nextElement();
            paramValues = request.getParameterValues(paramName);
            for (String value : paramValues) {
                paramValue = value + PIPE_CHAR + paramValue;
            }
            paramValue = paramValue.substring(0, paramValue.length() - PIPE_CHAR.toString().length());
            properties.setProperty(paramName, paramValue);
            paramValue = "";
        }
        return properties;
    }

}
