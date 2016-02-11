/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.email;

/**
 * Composes a simple mail and send it to the destination.
 * 
 * @author Armando Rivas
 * @since 12.04.2013
 */
public interface MailComposerService {
    /**
     * Creates an email, this method should be call before sending the email.
     * 
     * @return A composer object.
     */
    public Composer createMailComposer();

    /**
     * Sends a mail.
     * 
     * @param composer
     *            A composer.
     */
    public void send(Composer composer);
}
