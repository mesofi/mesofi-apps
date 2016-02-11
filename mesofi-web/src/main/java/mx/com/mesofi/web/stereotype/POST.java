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
 * Indicates that an annotated method is receiving a request using the POST
 * method from the HTTP standard specification. Whenever a method is annotated
 * with this type, is restricted to handle this kind of requests.
 * 
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see Controller
 * @see GET
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface POST {

}
