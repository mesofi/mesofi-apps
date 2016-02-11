/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.context;

import java.util.Properties;

import mx.com.mesofi.framework.jdbc.util.SqlParser;
import mx.com.mesofi.framework.stereotype.Bean;

/**
 * This bean contains the messages for the application, all the messages to
 * display should be loaded into this class when the application first starts.
 * 
 * @author Armando Rivas
 * @since Nov 18 2013
 */
@Bean
public class Messages {

    /**
     * Messages loaded.
     */
    private Properties messages;

    /**
     * @return the messages
     */
    public Properties getMessages() {
        return messages;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setMessages(Properties messages) {
        this.messages = messages;
    }

    /**
     * The value for the message.
     * 
     * @param key
     *            Key for the message.
     * @return The full message.
     */
    public String getMessage(String key) {
        return messages.getProperty(key, "The key [" + key + "] was not found in messages");
    }

    /**
     * Gets the message value given a key and a set of parameters for such a
     * key.
     * 
     * @param key
     *            Key for the message.
     * @param args
     *            Total values to be replaced.
     * @return The full message.
     */
    public String getMessage(String key, String... args) {
        String msg = getMessage(key);
        StringBuilder sb = new StringBuilder(msg);
        int start = 0;
        int end = 0;
        for (String p : args) {
            start = sb.indexOf(SqlParser.SQL_TOKEN);
            if (start == -1) {
                continue;
            }
            end = start + SqlParser.SQL_TOKEN.length();
            sb.replace(start, end, p);
        }
        return sb.toString();
    }

}
