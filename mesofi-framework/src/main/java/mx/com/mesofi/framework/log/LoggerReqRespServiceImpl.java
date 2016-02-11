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

import java.util.Properties;

import mx.com.mesofi.framework.stereotype.Bean;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.stereotype.Transaction;

/**
 * Implementation for storing the requet or response.
 * 
 * @author Armando Rivas.
 * @since 12.05.2013
 */
@Bean
public class LoggerReqRespServiceImpl implements LoggerReqRespService {
    /**
     * DAO for persisting the request or response.
     */
    @Inject
    private LoggerReqRespDao dao;

    /**
     * Properties in the request to be skipped.
     */
    private String[] propsToSkip = { "accept-encoding", "referer", "cookie" };

    /**
     * {@inheritDoc}
     */
    @Override
    @Transaction
    public long saveReqRespo(boolean request, LoggerReqRespVo logger) {
        long lastRequest = 0;
        if (request) {
            // filter some unwanted properties.
            Properties reqProps = logger.getHeaders();
            for (String prop : propsToSkip) {
                if (reqProps.containsKey(prop)) {
                    reqProps.remove(prop);
                }
            }
            lastRequest = dao.saveLogRequest(logger);
        } else {
            // saves the request and response...
            dao.saveLogResponse(logger);
        }
        return lastRequest;
    }
}
