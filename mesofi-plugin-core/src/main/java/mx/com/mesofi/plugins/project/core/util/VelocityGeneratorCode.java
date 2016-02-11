/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.util.TemplateManager;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderMappingCodeTypeVo;
import mx.com.mesofi.services.util.CommonConstants;
import mx.com.mesofi.services.util.SimpleValidator;

/**
 * Utility that manages all velocity code generation.
 * 
 * @author Armando Rivas
 * @since Feb 10 2015.
 * 
 */
public class VelocityGeneratorCode {
    /**
     * Unique instance for this class.
     */
    private static VelocityGeneratorCode velocityGeneratorCode = new VelocityGeneratorCode();

    /**
     * Private constructor for this class.
     */
    private VelocityGeneratorCode() {
        // private constructor.
    }

    /**
     * Gets the only instance for this class.
     * 
     * @return The singleton.
     */
    public static VelocityGeneratorCode getInstance() {
        return velocityGeneratorCode;
    }

    /**
     * Generates velocity code based on configuration.
     * 
     * @param codeMapping
     *            Configuration for the types on this application.
     * @return Map that contains the corresponding velocity code generated along
     *         with the pointcut identifier.
     */
    public Map<String, String> createCustomCode(List<ApplicationBuilderMappingCodeTypeVo> codeMapping) {
        Map<String, String> generatedFinalCode = new HashMap<String, String>();

        String lastPointCutName = null;
        for (ApplicationBuilderMappingCodeTypeVo vo : codeMapping) {
            if (lastPointCutName == null) {
                generatedFinalCode.put(vo.getPointCutName(), generateCode(true, vo));
            } else {
                if (lastPointCutName.equals(vo.getPointCutName())) {
                    String tmpCode = generatedFinalCode.get(vo.getPointCutName());
                    generatedFinalCode.put(vo.getPointCutName(), tmpCode + generateCode(false, vo));
                } else {
                    generatedFinalCode.put(vo.getPointCutName(), generateCode(true, vo));
                }
            }
            lastPointCutName = vo.getPointCutName();
        }
        // for every condition, adds the #end statement
        String code = null;
        for (String key : generatedFinalCode.keySet()) {
            code = (String) generatedFinalCode.get(key);
            code += "#else\nType: '${field.fieldType}' not yet supported under @Pointcut('" + key + "')\n#end";
            generatedFinalCode.put(key, code);
        }
        return generatedFinalCode;
    }

    /**
     * This method will merge the evaluated code agains the template content.
     * When this method is invoked, the template may be modified.
     * 
     * @param templateContent
     *            The template content that may be modified.
     * @param evaluatedCodeByPointcut
     *            The pointcut identifier and the evaluated code that will be
     *            merged into the template.
     */
    public void mergeTemplateContent(StringBuilder templateContent, Map<String, String> evaluatedCodeByPointcut) {
        SimpleValidator.isNull(templateContent, "This template cannot be null");
        SimpleValidator.isNull(evaluatedCodeByPointcut, "This code cannot be null");
        if (templateContent.toString().contains(TemplateManager.POINT_CUT)) {
            String newContent = null;
            for (String key : evaluatedCodeByPointcut.keySet()) {
                newContent = evaluatedCodeByPointcut.get(key);
                mergeTemplate(templateContent, key, newContent);
            }
        }
    }

    /**
     * This is the actual method that does the work of merging.
     * 
     * @param templateContent
     *            The template content.
     * @param pointCutIdentifier
     *            The pointcut identifier.
     * @param newContent
     *            The new content that will be merged.
     */
    private void mergeTemplate(StringBuilder templateContent, String pointCutIdentifier, String newContent) {
        int index = templateContent.indexOf(pointCutIdentifier);
        if (index != -1) {
            templateContent.replace(index - (TemplateManager.POINT_CUT.length() + 2),
                    index + pointCutIdentifier.length() + 2, newContent);
        }
    }

    /**
     * Gets the velocity code generated.
     * 
     * @param firstBlock
     *            States if this is the first block or not.
     * @param vo
     *            Information regarding types and pointcuts.
     * @return The generated code.
     */
    private String generateCode(boolean firstBlock, ApplicationBuilderMappingCodeTypeVo vo) {
        final String PIPE = " || ";
        StringBuilder sb = new StringBuilder();

        String javaType = null;

        for (Integer idType : vo.getTypes().keySet()) {
            javaType = (String) vo.getTypes().get(idType);
            sb.append("${field.fieldType} == ");
            sb.append("\"");
            sb.append(javaType);
            sb.append("\"");
            sb.append(PIPE);
        }
        int index = sb.length() - PIPE.length();
        sb.replace(index, sb.length(), "");
        sb.insert(0, "(");
        sb.append(")");

        if (firstBlock) {
            sb.insert(0, "#if ");
        } else {
            sb.insert(0, "#elseif ");
        }
        // inserts the body of the condition
        sb.append(CommonConstants.BREAK_LINE_STANDARD);
        sb.append(vo.getCode());
        sb.append(CommonConstants.BREAK_LINE_STANDARD);
        return sb.toString();
    }
}
