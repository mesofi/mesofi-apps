/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.service;

import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.util.LabelEntityVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAvailableTypesVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;

/**
 * This service handle all operations to configure the application.
 * 
 * @author Armando Rivas
 * @since Dec 14 2014.
 * 
 */
public interface ConfiguratorAppService {
    /**
     * Retrieves all code sections for the generated app.
     * 
     * @return All code sections.
     */
    List<String> getAllCodeSections();

    /**
     * Retrieves all available types for the generated app.
     * 
     * @return All available types.
     */
    List<LabelEntityVo> getAllAvailableTypes();

    /**
     * Based on an pointcutName identifier. Gets all the related to this. This
     * method returns all the relations between code and types. This method is
     * also invoked using the following method with includeRelations = true.
     * <p/>
     * <code>Map<String, Object> getCustomCodeBySection(String pointcutName, boolean includeRelations);</code>
     * 
     * @param pointcutName
     *            Point cut name.
     * @return List of records.
     * @see ConfiguratorAppService#getCustomCodeBySection(String, boolean)
     */
    Map<String, Object> getCustomCodeBySection(String pointcutName);

    /**
     * Based on a point cut name, gets all the related to this. This method
     * returns all the relations between code and types or can retrieve only
     * code depending on the value of includeRelations.
     * 
     * @param pointcutName
     *            The point cut name.
     * @param includeRelations
     *            if this is true, then retrieves all relations, otherwise only
     *            retrieves the code.
     * @return List of records.
     * @see ConfiguratorAppService#getCustomCodeBySection(String)
     */
    Map<String, Object> getCustomCodeBySection(String pointcutName, boolean includeRelations);

    /**
     * Retrieves all database names.
     * 
     * @return All code database names.
     */
    List<String> getAllDatabaseNames();

    /**
     * Retrieves all point cut names.
     * 
     * @param pointcutName
     *            The point cut name.
     * 
     * @return All point cut names.
     */
    List<String> getAllPointcutNames(String pointcutName);

    /**
     * Based on an database identifier code. Gets all types related to this.
     * 
     * @param databaseId
     *            Database identifier.
     * @return List of records.
     */
    List<BuilderMappingTypeVo> getTypesByDatabase(String databaseId);

    /**
     * Update the Java and Db types.
     * 
     * @param existingTypes
     *            The parameters sent.
     */
    void updateJavaDbTypes(Map<String, String> existingTypes);

    /**
     * Creates a new record for the code and the point cut.
     * 
     * @param pointcutName
     *            The point cut name.
     */
    void updateCustomCode(String pointcutName);

    /**
     * Updates the custom code given its identifier.
     * 
     * @param codeId
     *            The codeId.
     * @param code
     *            The actual code.
     */
    void updateCustomCode(int codeId, String code);

    /**
     * Removes the existing relation between types and code.
     * 
     * @param relationIds
     *            The relations.
     */
    void removeTypesAndCodeById(String[] relationIds);

    /**
     * Creates a new relation with the type and code.
     * 
     * @param codeId
     *            The code id.
     * @param typeId
     *            The type id.
     * @return Identifier of the new record just created.
     */
    long createTypesAndCode(Integer codeId, Integer typeId);

    /**
     * Based on a code identifier and point cut, gets all types related to this
     * code.
     * 
     * @param pointcutName
     *            The code point cut name.
     * @param codeId
     *            The code identifier.
     * @return All related types for this code.
     */
    List<BuilderAvailableTypesVo> getTypesByCode(String pointcutName, Integer codeId);

    /**
     * 
     * @param libDirectory
     *            The directory that contains all the libraries for this
     *            application.
     * @param pointcutSelected
     *            The pointcut selected in order to preview the selected code.
     * @return The final evaluated code and the template name.
     */
    String[] previewCodeByPointcut(String libDirectory, String pointcutSelected);
}
