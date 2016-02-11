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

import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is a "Service", originally defined by
 * Domain-Driven Design (Evans, 2003) as "an operation offered as an interface
 * that stands alone in the model, with no encapsulated state."
 * 
 * <p>
 * May also indicate that a class is a "Business Service Facade" (in the Core
 * J2EE patterns sense), or something similar. This annotation is a
 * general-purpose stereotype and individual teams may narrow their semantics
 * and use as appropriate.
 * 
 * <p>
 * This annotation serves as a specialization of {@link Component @Component},
 * allowing for implementation classes to be autodetected through classpath
 * scanning.
 * 
 * @author Armando Rivas
 * @since 1.0
 * @see Service
 * @see DAO
 * @see Bean
 * @see Inject
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller { 

}
