/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

package mx.com.mesofi.framework.log;

import static mx.com.mesofi.services.util.CommonConstants.PIPE_CHAR;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import mx.com.mesofi.framework.jdbc.JdbcAbstractDao;
import mx.com.mesofi.framework.stereotype.DAO;
import mx.com.mesofi.framework.stereotype.Inject;

import org.apache.log4j.Logger;

/**
 * Implementation for storing the request and response.
 * 
 * @author Armando Rivas.
 * @since 12.05.2013
 */
@DAO
public class LoggerReqRespDaoImpl extends JdbcAbstractDao implements LoggerReqRespDao {

    /**
     * log4j.
     */
    private Logger log = Logger.getLogger(LoggerReqRespDaoImpl.class);

    /**
     * References to the queries.
     */
    @Inject
    private Properties sqlLoggerReqResp;

    /**
     * Creates a new object given the datasource.
     * 
     * @param dataSource
     *            Data source.
     */
    @Inject
    public LoggerReqRespDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long saveLogRequest(LoggerReqRespVo loggerReqRespVO) {
        if (log.isDebugEnabled()) {
            log.debug("Saving request in log...");
        }

        List<Object> params = new ArrayList<Object>();
        params.add(loggerReqRespVO.getTransaction());
        params.add(loggerReqRespVO.getHttpMethod());
        params.add(loggerReqRespVO.getHttpRequest());
        params.add(loggerReqRespVO.getHttpContentType());
        params.add(loggerReqRespVO.getHttpContentLength());

        params.add(loggerReqRespVO.getClientHost());
        params.add(loggerReqRespVO.getClientAddr());
        params.add(loggerReqRespVO.getClientPort());

        // insert in parent
        long generatedId = insert(sqlLoggerReqResp.getProperty("insert.logger.parent"), "ID", params.toArray());
        params.clear();

        Properties header = loggerReqRespVO.getHeaders();
        String sql = sqlLoggerReqResp.getProperty("insert.logger.header.req");
        for (String name : header.stringPropertyNames()) {
            for (String value : header.getProperty(name).split("\\" + PIPE_CHAR.toString())) {
                // insert request header
                insert(sql, generatedId, name, value);
            }
        }

        Properties param = loggerReqRespVO.getRequestBody();
        sql = sqlLoggerReqResp.getProperty("insert.logger.params.req");
        for (String name : param.stringPropertyNames()) {
            for (String value : param.getProperty(name).split("\\" + PIPE_CHAR.toString())) {
                // insert request body
                insert(sql, generatedId, name, value);
            }
        }

        return generatedId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveLogResponse(LoggerReqRespVo loggerReqRespVO) {
        if (log.isDebugEnabled()) {
            log.debug("Saving response in log...");
        }
        List<Object> params = new ArrayList<Object>();
        params.add(loggerReqRespVO.getServerContentType());
        params.add(loggerReqRespVO.getServerStatus());
        params.add(loggerReqRespVO.getRequestId());

        // update in parent
        update(sqlLoggerReqResp.getProperty("update.logger.parent"), params.toArray());
        params.clear();

        Properties header = loggerReqRespVO.getHeaders();
        String sql = sqlLoggerReqResp.getProperty("insert.logger.header.resp");
        for (String name : header.stringPropertyNames()) {
            for (String value : header.getProperty(name).split("\\" + PIPE_CHAR.toString())) {
                // insert response header
                insert(sql, loggerReqRespVO.getRequestId(), name, value);
            }
        }

        String response = loggerReqRespVO.getResponseBody();
        sql = sqlLoggerReqResp.getProperty("insert.logger.params.resp");
        // insert response body
        insert(sql, loggerReqRespVO.getRequestId(), response);

    }
}
