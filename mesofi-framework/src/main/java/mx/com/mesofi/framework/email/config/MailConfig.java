package mx.com.mesofi.framework.email.config;

import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_HOST;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_HOST_DEFAULT;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_PASS;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_PASS_DEFAULT;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_PORT;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_PORT_DEFAULT;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_USERNAME;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_USERNAME_DEFAULT;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSenderImpl emailSender(@Value("${" + EMAIL_HOST + ":" + EMAIL_HOST_DEFAULT + "}") String emailHost,
            @Value("${" + EMAIL_PORT + ":" + EMAIL_PORT_DEFAULT + "}") Integer emailPort, @Value("${" + EMAIL_USERNAME
                    + ":" + EMAIL_USERNAME_DEFAULT + "}") String username, @Value("${" + EMAIL_PASS + ":"
                    + EMAIL_PASS_DEFAULT + "}") String password) {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost(emailHost);
        emailSender.setPort(emailPort);
        emailSender.setUsername(username);
        emailSender.setPassword(password);
        // emailSender.setDefaultEncoding("UTF_8");
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.transport.protocol", "smtp");
        mailProps.setProperty("mail.smtp.auth", "true");
        mailProps.setProperty("mail.smtp.starttls.enable", "true");
        mailProps.setProperty("mail.debug", "false");
        emailSender.setJavaMailProperties(mailProps);
        return emailSender;
    }

    @Bean
    public SimpleMailMessage emailMessage() {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        return emailMessage;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.addProperty("resource.loader", "class");
        engine.addProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return engine;
    }
}
