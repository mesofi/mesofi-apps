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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describes transaction attributes on a method or class.
 * 
 * <p>
 * This annotation type is generally directly comparable to Spring's
 * {@link org.springframework.transaction.interceptor.RuleBasedTransactionAttribute}
 * class, and in fact {@link AnnotationTransactionAttributeSource} will directly
 * convert the data to the latter class, so that Spring's transaction support
 * code does not have to know about annotations. If no rules are relevant to the
 * exception, it will be treated like
 * {@link org.springframework.transaction.interceptor.DefaultTransactionAttribute}
 * (rolling back on runtime exceptions).
 * 
 * <p>
 * For specific information about the semantics of this annotation's attributes,
 * consider the {@link org.springframework.transaction.TransactionDefinition}
 * and {@link org.springframework.transaction.interceptor.TransactionAttribute}
 * javadocs.
 * 
 * <p>
 * Notice this annotation is a simplification for the original annotation
 * {@link org.springframework.transaction.annotation.Transactional} from
 * springframework but in Mesofi projects this annotation is prefered instead.
 * When it is needed, this framework can make used of the original spring
 * annotation.
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see org.springframework.transaction.annotation.Transactional
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional
public @interface Transaction {

}
