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

import org.springframework.stereotype.Repository;

/**
 * Indicates that an annotated class is a "DAO", originally defined by
 * Domain-Driven Design (Evans, 2003) as "a mechanism for encapsulating storage,
 * retrieval, and search behavior which emulates a collection of objects".
 * 
 * <p>
 * Teams implementing traditional J2EE patterns such as "Data Access Object" may
 * also apply this stereotype to DAO classes, though care should be taken to
 * understand the distinction between Data Access Object and DDD-style
 * repositories before doing so. This annotation is a general-purpose stereotype
 * and individual teams may narrow their semantics and use as appropriate.
 * 
 * <p>
 * A class thus annotated is eligible for Spring
 * {@link org.springframework.dao.DataAccessException DataAccessException}
 * translation when used in conjunction with a
 * {@link org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
 * PersistenceExceptionTranslationPostProcessor}. The annotated class is also
 * clarified as to its role in the overall application architecture for the
 * purpose of tooling, aspects, etc.
 * 
 * <p>
 * As of Mesofi framework, this annotation also serves as a specialization of
 * {@link Bean @Bean}, allowing for implementation classes to be autodetected
 * through classpath scanning.
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see Bean
 * @see Inject
 * @see Service
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface DAO {
    /**
     * The value may indicate a suggestion for a logical component name, to be
     * turned into a Spring bean in case of an autodetected component.
     * 
     * @return the suggested component name, if any
     */
    String value() default "";
}