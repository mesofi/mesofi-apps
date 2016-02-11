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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Marks a constructor, field, setter method or config method as to be autowired
 * by Spring's dependency injection facilities.
 * 
 * <p>
 * Only one constructor (at max) of any given bean class may carry this
 * annotation, indicating the constructor to autowire when used as a Spring
 * bean. Such a constructor does not have to be public.
 * 
 * <p>
 * Fields are injected right after construction of a bean, before any config
 * methods are invoked. Such a config field does not have to be public.
 * 
 * <p>
 * Config methods may have an arbitrary name and any number of arguments; each
 * of those arguments will be autowired with a matching bean in the Spring
 * container. Bean property setter methods are effectively just a special case
 * of such a general config method. Such config methods do not have to be
 * public.
 * 
 * <p>
 * In case of a {@link java.util.Collection} or {@link java.util.Map} dependency
 * type, the container will autowire all beans matching the declared value type.
 * In case of a Map, the keys must be declared as type String and will be
 * resolved to the corresponding bean names.
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see Component
 * @see DAO
 * @see Bean
 * @see Service
 */
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface Inject {

}
