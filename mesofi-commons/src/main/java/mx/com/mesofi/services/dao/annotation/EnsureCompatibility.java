/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion usada en tiempo de ejecucion para forzar a que un campo tenga una
 * compatibilidad con una campo de una base de datos, es decir, forzar una
 * conversion entre los datos de java y los de una base de datos.
 */
// Hace esta anotacion accesible en tiempo de ejecucion usando reflexion.
@Retention(RetentionPolicy.RUNTIME)
// Esta anotacion solo puede ser aplicada a los campos de alguna clase.
@Target(ElementType.FIELD)
@Documented
public @interface EnsureCompatibility {

}
