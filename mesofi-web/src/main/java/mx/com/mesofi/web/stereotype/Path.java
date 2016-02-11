/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is meant to be used for handling incoming request. When this
 * annotation is set at level either type or method, it lets you specify the
 * paths for the request to handle the actions. If this annotation does not
 * exist, then all the incoming request are accepted.
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see Controller
 * @see GET
 * @see POST
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {
    /**
     * The path for the incoming request.
     * 
     * @return Several paths to respond for this incoming request.
     */
    String[] value();
}
