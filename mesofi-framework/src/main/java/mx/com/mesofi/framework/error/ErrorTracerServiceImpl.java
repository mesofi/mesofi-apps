/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.error;

import static mx.com.mesofi.framework.util.FrameworkUtils.SAVE_ERRORS;
import static mx.com.mesofi.framework.util.FrameworkUtils.SAVE_ERRORS_DEFAULT;
import mx.com.mesofi.framework.email.Composer;
import mx.com.mesofi.framework.email.MailComposerService;
import mx.com.mesofi.framework.stereotype.Bean;
import mx.com.mesofi.framework.stereotype.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Bean
public class ErrorTracerServiceImpl implements ErrorTracerService {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(ErrorTracerServiceImpl.class);

    @Inject
    private ErrorTracerDao tracerDAO;

    @Inject
    private MailComposerService mail;

    /**
     * States if the errors are saved or not.
     */
    @Value("${" + SAVE_ERRORS + ":" + SAVE_ERRORS_DEFAULT + "}")
    private Boolean saveErrors;

    @Transactional
    @Override
    public void saveErrorTrace(ErrorTracerVo errorTracerVO) {
        if (saveErrors) {
            try {
                // attempt to save errors.
                tracerDAO.saveLog(errorTracerVO);
                // sends an email about this error.
                Composer composer = mail.createMailComposer();
                composer.setBodyTemplate("mesofi.config.email.error");
                composer.setSubject("An error has occured");
                composer.addTo("rivasarmando271084@gmail.com");
                mail.send(composer);
            } catch (Throwable e) {
                if (log.isDebugEnabled()) {
                    log.error("Error when trying to save in log errors due to " + e.getMessage());
                }
                // it does nothing
                e.printStackTrace();
            }
        }
    }

}
