/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.properties;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import mx.com.mesofi.services.util.CommonConstants;

/**
 * Clase que funciona para leer los mensajes bajo una localizacion e
 * internacionalizables. especificando su ubicacion en el classpath.
 * 
 * @author mesofi
 */
public class I18nLoader {

	/**
	 * Contiene una instancia de ResourceBundle.
	 */
	private static ResourceBundle bundle;

	/**
	 * Obtiene el mensaje relacionada a la propiedad especificada.
	 * 
	 * @param msg
	 *            Llave de la propiedad.
	 * @return Mensaje inetrnacionalizado, si dicho mensaje no existe, entonces
	 *         regresa una cadena ??<llave-mansaje>>??.
	 */
	public static String getMessage(final String msg) {
		String message = null;
		try {
			isNull(bundle, "No se ha especificado una referencia valida "
					+ "hacia ResourceBundle, "
					+ "invoque el metodo setResource para obtener "
					+ "una referencia de la misma.");
			message = bundle.getString(msg);
		} catch (MissingResourceException mre) {
			message = CommonConstants.UNKNOWN_CHAR.toString() + msg
					+ CommonConstants.UNKNOWN_CHAR.toString();
		}
		return message;
	}

	/**
	 * Define el lugar en donde reside el archivo de propiedades que sera leido.
	 * 
	 * @param resource
	 *            Nombre completo del recurso.
	 * @param locale
	 *            Localizacion especificada.
	 * @see #setResource(java.lang.String)
	 */
	public static void setResource(final String resource, final Locale locale) {
		bundle = ResourceBundle.getBundle(resource, locale);
	}

	/**
	 * Define el lugar en donde reside el archivo de propiedades que sera leido.
	 * 
	 * @param resource
	 *            Nombre completo del recurso.
	 * @see #setResource(java.lang.String, java.util.Locale)
	 */
	public static void setResource(final String resource) {
		bundle = ResourceBundle.getBundle(resource);
	}

	/**
	 * Obtiene una refrencia directa al objeto ResourceBundle.
	 * 
	 * @return Referencia a ResourceBundle o null si no existe.
	 */
	public static ResourceBundle ResourceBundle() {
		return bundle;
	}
}
