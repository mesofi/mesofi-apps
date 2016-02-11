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

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;

/**
 * This interface has the most common methods to manipulate sending email using
 * some templates.
 * 
 * @author Armando Rivas
 * @since 12.04.2013
 * @see SimpleComposer
 * @see JavaMailSender
 */
public interface Composer {
    /**
     * Adds an attachment to the mail.
     * 
     * @param attachment
     *            The file to attach.
     */
    void addAttachment(File attachment);

    /**
     * Adds a new mail which is going to receive the content.
     * 
     * @param email
     * @see #addTo(String...)
     */
    void addTo(String email);

    /**
     * Adds a list of email's which is going to receive the content.
     * 
     * @param email
     * @see #addTo(String)
     */
    void addTo(String... emails);

    /**
     * Adds an email as a CC.
     * 
     * @param email
     *            The email.
     * @see #addCc(String...)
     */
    void addCc(String email);

    /**
     * Adds an email as a CC.
     * 
     * @param emails
     *            List of emails.
     * @see #addCc(String)
     */
    void addCc(String... emails);

    /**
     * Adds an email as a BCC.
     * 
     * @param email
     *            The email.
     * @see #addBcc(String...)
     */
    void addBcc(String email);

    /**
     * Adds an email as a BCC.
     * 
     * @param emails
     *            List of emails.
     * @see #addBcc(String)
     */
    void addBcc(String... emails);

    /**
     * Set the subject of the email.
     * 
     * @param subject
     *            The subject.
     */
    void setSubject(String subject);

    /**
     * Set the body content of the email. When this body is set, then the
     * template behavior is overridden.
     * 
     * @param body
     *            Body content.
     */
    void setBody(String body);

    /**
     * Sets a template identifier in order to process and send the mail.
     * 
     * @param templateIdentifier
     *            Template identifier.
     * @see #setBodyTemplate(String, Map)
     */
    void setBodyTemplate(String templateIdentifier);

    /**
     * Sets a template identifier in order to process and send the mail.
     * 
     * @param templateIdentifier
     *            Template identifier.
     * @param params
     *            Parameters for the template.
     * @see #setBodyTemplate(String)
     */
    void setBodyTemplate(String templateIdentifier, Map<String, Object> params);

    /**
     * Gets the template name. When the template is not found then returns a
     * null value.
     * 
     * @return Template name.
     */
    String getTemplateName();

    /**
     * Gets an array of addresses that the mail will be sent to.
     * 
     * @return Array of addresses.
     */
    String[] getTo();

    /**
     * Gets an array of addresses that the mail contains as BCC.
     * 
     * @return Array of addresses.
     */
    String[] getBcc();

    /**
     * Gets an array of addresses that the mail contains as CC.
     * 
     * @return Array of addresses.
     */
    String[] getCc();

    /**
     * Gets the plain body of the email.
     * 
     * @return Body of this email.
     */
    String getBody();

    /**
     * Gets the model or parameters for the template.
     * 
     * @return Model of the template.
     */
    Map<String, Object> getModel();

    /**
     * Gets the subject of this email.
     * 
     * @return Title of this email.
     */
    String getSubject();

    /**
     * Gets a list of files attached to the email.
     * 
     * @return List of attachments or an empty list if there is no attachments.
     */
    List<File> getAttachments();

    /**
     * Gets the identifier template the user is trying to find.
     * 
     * @return Template identifier.
     */
    String getTemplateIdentifier();

}
