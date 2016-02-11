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

import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_ENABLED;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_ENABLED_DEFAULT;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import mx.com.mesofi.framework.stereotype.Bean;
import mx.com.mesofi.framework.stereotype.Inject;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Bean
class MailComposerServiceImpl implements MailComposerService {

    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(MailComposerServiceImpl.class);

    /**
     * The mail sender.
     */
    @Inject
    private JavaMailSender mailSender;

    /**
     * Message for the email.
     */
    @Inject
    private SimpleMailMessage templateMessage;

    /**
     * The velocity engine.
     */
    @Inject
    private VelocityEngine velocityEngine;

    /**
     * States if the email is send or not.
     */
    @Value("${" + EMAIL_ENABLED + ":" + EMAIL_ENABLED_DEFAULT + "}")
    private Boolean sendEmail;

    /**
     * The templates in the application.
     */
    @Inject
    private Properties templatesProps;

    /**
     * {@inheritDoc}
     */
    @Override
    public Composer createMailComposer() {
        return new SimpleComposer(templatesProps);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Composer composer) {
        if (sendEmail) {
            MimeMessage message = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String templateName = composer.getTemplateName();
                if (templateName == null) {
                    throw new IllegalStateException("This template [" + composer.getTemplateIdentifier()
                            + "] could not be found anywhere");
                }

                String[] to = composer.getTo();
                if (to.length == 0) {
                    throw new IllegalStateException("At least a recipient must be specified in order to send any email");
                }

                String[] bcc = composer.getBcc();
                String[] cc = composer.getCc();
                List<File> list = composer.getAttachments();

                Map<String, Object> model = composer.getModel();
                String subject = composer.getSubject();

                String content = null;
                if (composer.getBody() != null) {
                    content = composer.getBody();
                } else {
                    content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName,
                            StandardCharsets.UTF_8.toString(), model);
                }
                helper.setTo(to);
                helper.setBcc(bcc);
                helper.setCc(cc);
                helper.setText(content, true);
                helper.setSubject(subject);

                helper.setPriority(1);
                helper.setValidateAddresses(true);

                for (File file : list) {
                    FileSystemResource resource = new FileSystemResource(file);
                    helper.addAttachment(file.getName(), resource);
                }
            } catch (MessagingException e) {
                e.printStackTrace(); // does nothing
            }
            mailSender.send(message);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Sending emails has been disabled, to enable set this property as 'true' " + EMAIL_ENABLED);
            }
        }
    }
}
