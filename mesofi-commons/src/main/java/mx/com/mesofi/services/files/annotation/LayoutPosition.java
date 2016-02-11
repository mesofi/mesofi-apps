/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacion usada en tiempo de ejecucion para posicionar o almacenar la
 * informacion regresada por un layout hacia un bean. Esto es, cuando se popula
 * un bean, es necesario especificar las posiciones en las cuales las columnas
 * del layout sera almacenado en el bean con sus propiedades.
 */
// Hace esta anotacion accesible en tiempo de ejecucion usando reflexion.
@Retention(RetentionPolicy.RUNTIME)
// Esta anotacion solo puede ser aplicada a los campos de alguna clase.
@Target(ElementType.FIELD)
@Documented
public @interface LayoutPosition {
    /**
     * Posicion equivalente a la posicion en el layout. Esta posicion debe
     * iniciar forzosamente en la posicion 1.
     * 
     * @return Posicion equivalente a la configuracion del layout.
     */
    int position();
}
