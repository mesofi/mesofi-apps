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
 * Esta anotacion debe ser incluida en aquellos objetos que tengan por
 * definicion ser tratados como layouts de transferencia de informacion. En caso
 * de no implementarla, no seran tomando en cuenta para procesar dicha
 * informacion.
 * 
 * @author mesofi.
 */
// Hace esta anotacion accesible en tiempo de ejecucion usando reflexion.
@Retention(RetentionPolicy.RUNTIME)
// Esta anotacion solo puede ser aplicada a clases, interfaces o enums.
@Target(ElementType.TYPE)
@Documented
public @interface Layout {
}