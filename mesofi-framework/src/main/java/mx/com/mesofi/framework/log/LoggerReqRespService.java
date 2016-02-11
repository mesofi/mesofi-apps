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
 * Contains methods to manage the request and response.
 * 
 * @author Armando Rivas.
 * @since 12.05.2013
 */
public interface LoggerReqRespService {
    /**
     * Saves the request and response details.
     * 
     * @param request
     *            This flag states whether this operation is request or
     *            response.
     * @param logger
     *            Contains the request or response details.
     * @return Identifier for the request.
     */
    long saveReqRespo(boolean request, LoggerReqRespVo logger);
}
