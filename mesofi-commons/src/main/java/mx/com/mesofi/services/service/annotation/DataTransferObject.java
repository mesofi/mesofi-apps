/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Tipo de dato que debe ser implementado a fin de cumplir cierto estandar al
 * momento de usarlo. La clase que implemente esta interfaz debe cumplir con los
 * estandares del nombramiento de sus metodo como un JavaBean, esto es, por cada
 * propiedad que contenga dicha clasem debe de existir forzosamente sus metodos
 * getters y setters.
 * 
 * @author mesofi.
 */
// Hace esta anotacion accesible en tiempo de ejecucion usando reflexion.
@Retention(RetentionPolicy.RUNTIME)
// Esta anotacion solo puede ser aplicada a clases, interfaces o enums.
@Target(ElementType.TYPE)
@Documented
public @interface DataTransferObject {
}
