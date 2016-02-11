/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.response.types;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import mx.com.mesofi.web.response.AbstractResponse;
import mx.com.mesofi.web.response.ConversionFormatException;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.util.MediaType;

/**
 * Class to handle the XML responses.
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
public class XmlResponse extends AbstractResponse implements WebResponse {
    /**
     * Creates this object given a valid object.
     * 
     * @param object
     *            Object to be converted.
     */
    public XmlResponse(Object object) {
        setObjectToConvert(object, XmlResponse.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResponse() throws ConversionFormatException {
        Object object = getObjectToConvert();
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, sw);
        } catch (JAXBException e) {
            throw new ConversionFormatException(e);
        }
        return sw.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_XML_TYPE;
    }
}
