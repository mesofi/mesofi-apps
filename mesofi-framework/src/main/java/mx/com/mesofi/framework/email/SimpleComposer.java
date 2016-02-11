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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import mx.com.mesofi.services.util.SimpleValidator;

/**
 * This class is used by the framework and it is the most common known class
 * implementation that holds some properties to send emails.
 * 
 * @author Armando Rivas
 * @since 12.04.2013
 */
class SimpleComposer implements Composer {
    /**
     * Template identifier.
     */
    private String templateIdentifier;
    /**
     * Params for the template.
     */
    private Map<String, Object> params = new HashMap<String, Object>();
    /**
     * Templates in the application.
     */
    private Properties templates;
    /**
     * Body or content in the mail.
     */
    private String body;
    /**
     * The subject.
     */
    private String subject;
    private List<String> to = new ArrayList<String>();
    private List<String> cc = new ArrayList<String>();
    private List<String> bcc = new ArrayList<String>();
    private List<File> attachments = new ArrayList<File>();

    /**
     * Creates a new instance of this class passing
     * 
     * @param templates
     *            All the templates loaded into the application.
     */
    public SimpleComposer(Properties templates) {
        this.templates = templates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAttachment(File attachment) {
        this.attachments.add(attachment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTo(String email) {
        to.add(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTo(String... emails) {
        for (String email : emails) {
            to.add(email);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCc(String email) {
        cc.add(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCc(String... emails) {
        for (String email : emails) {
            cc.add(email);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBcc(String email) {
        bcc.add(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBcc(String... emails) {
        for (String email : emails) {
            bcc.add(email);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBodyTemplate(String templateIdentifier) {
        this.templateIdentifier = templateIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBodyTemplate(String templateIdentifier, Map<String, Object> params) {
        this.templateIdentifier = templateIdentifier;
        this.params = params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplateName() {
        return templates.getProperty(getTemplateIdentifier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getTo() {
        return to.toArray(new String[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getBcc() {
        return bcc.toArray(new String[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getCc() {
        return cc.toArray(new String[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBody() {
        return this.body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getModel() {
        return this.params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSubject() {
        return subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<File> getAttachments() {
        return attachments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplateIdentifier() {
        SimpleValidator.isNull(templateIdentifier, "An identifier for the mail template has not been found, "
                + "please specify one");
        return templateIdentifier;
    }

}
