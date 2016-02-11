/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.layers;

import java.util.List;

import mx.com.mesofi.plugins.project.core.files.PlainFile;

/**
 * This interface defines the common behavior for all the layers.
 * 
 * @author Armando Rivas
 * @since Jun 26, 2014
 */
public interface CommonLayer extends Layer {

    List<PlainFile> getCommonSources(String packageName);

}
