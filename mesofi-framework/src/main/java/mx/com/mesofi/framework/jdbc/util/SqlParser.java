/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.jdbc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * This class performs some basic operations over sql statements, this
 * operations change the behave of the current sql such as formatting the sql.
 * 
 * @author Armando Rivas
 * @since 24 Sep 2013
 */
public class SqlParser {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(SqlParser.class);
    /**
     * Token contained in SQL statements.
     */
    public static final String SQL_TOKEN = "?";
    /**
     * SQL to evaluate.
     */
    private String sql;
    /**
     * SQL parameters.
     */
    private Object[] params;
    /**
     * Sets the default sql formatter but it can be changed later.
     */
    private Formatter formatter = new BasicFormatterImpl();

    /**
     * Formats the sql using a basic implementation. This implementation can be
     * change in runtime.
     */
    public void formatSql() {
        if (sql == null || sql.trim().length() == 0) {
            throw new IllegalArgumentException("There is no SQL statement to format");
        }
        sql = formatter.format(sql);
    }

    /**
     * Prints the evaluation of the sql statement taking as a base the
     * parameters given. To print the evaluation, needs to use the standard
     * output in console.
     * 
     * @return The content being printed.
     */
    public String printSqlEvaluation() {
        boolean skipParameters = false;
        if (sql == null || sql.trim().length() == 0) {
            throw new IllegalArgumentException("There is no SQL statement to evaluate");
        }
        skipParameters = !sql.contains(SQL_TOKEN);
        if (!skipParameters) {
            if (params == null || params.length == 0) {
                throw new IllegalArgumentException("The parameters you try to bound are empty");
            }
        }
        int total = countReplacements(sql);
        if (total != params.length) {
            throw new IllegalArgumentException("The total of parameters and tokens are not the same\nParameters: "
                    + params.length + "\nTokens: " + total + "\n" + sql);
        }
        StringBuilder sb = new StringBuilder(sql);
        List<String> paramsEval = new ArrayList<String>();
        for (Object p : params) {
            String finalReplacement = getFinalReplacement(p);
            paramsEval.add(finalReplacement);
            findAndReplace(sb, finalReplacement);
        }
        StringBuffer printSb = new StringBuffer();
        if (total != 0) {
            printSb.append("\nParameters to be replaced in sql statement: {" + total + "} => " + paramsEval);
            for (int i = 1; i <= paramsEval.size(); i++) {
                printSb.append("\nSetting SQL statement parameter value: column index " + i + ", parameter value ["
                        + paramsEval.get(i - 1) + "]");
            }
        }
        printSb.append("\nSQL>\n" + sb + "\n");
        this.setSql(sb.toString());
        if (log.isDebugEnabled()) {
            log.debug("Details in SQL statements" + printSb);
        }
        return printSb.toString();
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * 
     * @param sql
     *            the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(Object... params) {
        this.params = params;
    }

    /**
     * Assign a new formatter for beautify the sql, by default uses
     * {@link BasicFormatterImpl}
     * 
     * @param formatter
     *            the formatter to set
     */
    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    private int countReplacements(String sqlStmt) {
        Pattern pattern = Pattern.compile("\\" + SQL_TOKEN);
        Matcher matcher = pattern.matcher(sqlStmt);
        int total = 0;
        while (matcher.find()) {
            total++;
        }
        return total;
    }

    private String getFinalReplacement(Object object) {
        String replacement = "UNKNOWN";
        if (object instanceof String) {
            replacement = "'" + (String) object + "'";
        } else if (object instanceof Integer) {
            replacement = String.valueOf((Integer) object);
        } else if (object instanceof Long) {
            replacement = String.valueOf((Long) object);
        } else if (object instanceof Float) {
            replacement = String.valueOf((Float) object);
        } else if (object instanceof Double) {
            replacement = String.valueOf((Double) object);
        } else if (object instanceof Boolean) {
            replacement = String.valueOf(((Boolean) object) ? "1" : "0");
        }
        return replacement;
    }

    private void findAndReplace(StringBuilder sqlBuilder, String finalReplacement) {
        int start = sqlBuilder.indexOf(SQL_TOKEN);
        int end = start + SQL_TOKEN.length();
        sqlBuilder.replace(start, end, finalReplacement);
    }
}
