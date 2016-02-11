/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

package mx.com.mesofi.framework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is a "Bean", originally defined by
 * Domain-Driven Design (Evans, 2003) as "a mechanism for encapsulating storage,
 * retrieval, and search behavior which emulates a collection of objects".
 * 
 * <p>
 * As of Mesofi framework, this annotation also serves as a specialization of
 * {@link Component @Component}, allowing for implementation classes to be
 * autodetected through classpath scanning.
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see Component
 * @see Inject
 * @see Service
 * @see DAO
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Bean {
    /**
     * The value may indicate a suggestion for a logical component name, to be
     * turned into a Spring bean in case of an autodetected component.
     * 
     * @return the suggested component name, if any
     */
    String value() default "";
}
