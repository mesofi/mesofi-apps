/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.config.Field;

/**
 * This holds information to create custom code.
 * 
 * @author Armando Rivas
 * @since May 01 2015.
 */
public class BuilderCodeGenVo {
    private boolean createCode;
    private int totalFields;
    private List<List<Map<String, Object>>> fields = new ArrayList<List<Map<String, Object>>>();
    private int totalRows = 1;
    private final static int[] withFields = { -1, 6, 4 };
    private final static int MAX_COLUMN_PER_ROW = withFields.length;

    public void addField(Field field) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("field", field);
        map.put("html", field.toString());

        int module = this.totalFields % MAX_COLUMN_PER_ROW;
        if (module == 0) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            map.put("width", withFields[module]);
            list.add(map);
            this.fields.add(list);
        } else {
            this.fields.get(totalRows - 1).add(map);

            for (Map<String, Object> element : this.fields.get(totalRows - 1)) {
                element.put("width", withFields[module]);
            }
        }
        totalFields++;

        calculateRows();
        createCode = true;
    }

    /**
     * @return the createCode
     */
    public boolean isCreateCode() {
        return createCode;
    }

    /**
     * @return the fields
     */
    public List<List<Map<String, Object>>> getFields() {
        return fields;
    }

    /**
     * @return the totalRows
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * @return the totalFields
     */
    public int getTotalFields() {
        return totalFields;
    }

    private void calculateRows() {
        int rows = this.totalFields / MAX_COLUMN_PER_ROW;
        if (this.totalFields % MAX_COLUMN_PER_ROW != 0) {
            rows++;
        }
        totalRows = rows;
    }
}
