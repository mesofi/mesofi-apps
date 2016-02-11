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

/**
 * DAO that stores the request or response in database.
 * 
 * @author Armando Rivas.
 * @since 12.05.2013
 */
public interface LoggerReqRespDao {
    /**
     * Saves the request.
     * 
     * @param loggerReqRespVO
     *            Data for the request.
     * @return Identifier of the saved record.
     */
    long saveLogRequest(LoggerReqRespVo loggerReqRespVO);

    /**
     * Saves the response.
     * 
     * @param loggerReqRespVO
     *            Data for the response.
     */
    void saveLogResponse(LoggerReqRespVo loggerReqRespVO);
}
