/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.dao;

import java.util.List;

import mx.com.mesofi.framework.util.LabelEntityVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderCodeTypeVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;

public interface ConfiguratorAppDao {
    List<String> getAllCodeSections();

    List<LabelEntityVo> getAllAvailableTypes();

    List<Integer> getAllDatabaseNames();

    List<String> getAllPointcutNames(String pointcutName);

    List<BuilderMappingTypeVo> getTypesByDatabase(String databaseId);

    List<LabelEntityVo> getCustomCodeBySection(String pointcutName);

    void updateJavaDbTypes(int idType, String javaType, boolean effectiveType);

    void insertCustomCode(String pointcutName);

    void updateCustomCode(int codeId, String code);

    List<BuilderCodeTypeVo> getTypesBySection(String pointcutName);

    void removeTypesAndCodeById(Integer relationId);

    long createTypesAndCode(int codeId, int typeId);

}
