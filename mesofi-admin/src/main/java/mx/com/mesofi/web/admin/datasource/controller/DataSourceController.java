/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.datasource.controller;

import static mx.com.mesofi.services.util.SimpleCommonActions.fromNullToCustomValue;
import static mx.com.mesofi.services.util.SimpleCommonActions.randomNumber;
import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.context.Messages;
import mx.com.mesofi.framework.error.ValidationBusinessException;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.web.admin.datasource.error.RemoteConnectionException;
import mx.com.mesofi.web.admin.datasource.service.DataSourceService;
import mx.com.mesofi.web.admin.datasource.view.StateView;
import mx.com.mesofi.web.admin.datasource.view.TreeNodeView;
import mx.com.mesofi.web.admin.datasource.view.TreeView;
import mx.com.mesofi.web.admin.datasource.vo.ColumnFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnNameVo;
import mx.com.mesofi.web.admin.datasource.vo.ConnectionDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceSimpleVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseVo;
import mx.com.mesofi.web.request.PageParameters;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.response.types.JsonPlainResponse;
import mx.com.mesofi.web.response.types.JsonResponse;
import mx.com.mesofi.web.stereotype.Controller;
import mx.com.mesofi.web.stereotype.GET;

import org.apache.log4j.Logger;

/**
 * This class contains methods to handle connections to remote databases.
 * 
 * @author Armando Rivas
 * @since Feb 10 2014.
 * 
 */
@Controller
public class DataSourceController {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(DataSourceController.class);
    /**
     * Services injected.
     */
    @Inject
    private DataSourceService service;
    /**
     * Messages.
     */
    @Inject
    private Messages messages;

    /**
     * Gets all existing connections stored.
     * 
     * @param pageParameters
     *            Parameters from client.
     * @return All existing connections to show.
     */
    @GET
    public WebResponse retrieveConnections(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Fetching all existing connections...");
        }
        List<DataSourceSimpleVo> connections = service.getAvailableConnections();

        TreeView treeView = new TreeView();
        treeView.setText("Existing Connections");
        StateView stateView = new StateView();
        stateView.setOpened(true);
        treeView.setState(stateView);
        List<TreeNodeView> children = new ArrayList<TreeNodeView>();
        TreeNodeView nodeView = null;
        for (DataSourceSimpleVo vo : connections) {
            nodeView = new TreeNodeView();
            // identifier for the connection
            nodeView.setId("connection-" + String.valueOf(vo.getId()));
            nodeView.setText(vo.getDescription());
            nodeView.setIcon("resources/mesofi/img/database-server.png");
            children.add(nodeView);
        }
        treeView.setChildren(children);
        // when no connections found, change the label.
        if (children.isEmpty()) {
            treeView.setText("No connections available");
        }
        // return plain JSON with no custom elements.
        return new JsonPlainResponse(treeView);
    }

    /**
     * Retrieves the database connections.
     * 
     * @param pageParameters
     *            Parameters from client.
     * @return Database details.
     */
    @GET
    public WebResponse retrieveSchemaDatabase(PageParameters pageParameters) {
        String connIdSelected = pageParameters.getValue("conn-id");
        String connTextSelected = pageParameters.getValue("conn-text");

        if (log.isDebugEnabled()) {
            log.debug("Retrieving schema or database from connection ..." + connIdSelected);
        }
        Integer connId = Integer.parseInt(connIdSelected.split("-")[1]);
        List<TreeNodeView> children = new ArrayList<TreeNodeView>();
        List<SchemaDatabaseVo> schemaDatabases = null;
        try {
            schemaDatabases = service.getAvailableSchemaDatabase(connId);
            TreeNodeView nodeView = null;
            for (SchemaDatabaseVo vo : schemaDatabases) {
                nodeView = new TreeNodeView();
                nodeView.setId("database-" + vo.getSchemaDatabase() + "-" + vo.isDatabase() + "-" + randomNumber(10000));
                nodeView.setText(vo.getSchemaDatabase());
                nodeView.setIcon(vo.isDatabase() ? "resources/mesofi/img/database-instance.png"
                        : "resources/mesofi/img/user.png");
                children.add(nodeView);
            }
        } catch (RemoteConnectionException e) {
            // when an error occurs, a normal response is replaced by the error
            // node.
            TreeNodeView nodeView = new TreeNodeView();
            nodeView.setId("database-error");
            nodeView.setChildren(false);
            nodeView.setText("[" + connTextSelected + "]: " + e.getMessage());
            children.add(nodeView);
        }
        return new JsonPlainResponse(children);
    }

    /**
     * Done
     */
    @GET
    public WebResponse retrieveTableNames(PageParameters pageParameters) {
        String connIdSelected = pageParameters.getValue("conn-id");
        Integer connId = Integer.parseInt(connIdSelected.split("-")[1]);

        String databaseSchemaIdSelected = pageParameters.getValue("database-schema-id");
        String[] idCompound = databaseSchemaIdSelected.split("-");
        String databaseSchema = idCompound[1];
        Boolean isDatabase = Boolean.parseBoolean(idCompound[2]);

        if (log.isDebugEnabled()) {
            log.debug("Retrieving tablenames from [" + idCompound[0] + "]");
        }
        List<String> tables = service.getTableNames(connId, databaseSchema, isDatabase);
        List<TreeNodeView> children = new ArrayList<TreeNodeView>();
        TreeNodeView nodeView = null;
        for (String tableName : tables) {
            nodeView = new TreeNodeView();
            nodeView.setId("table-" + tableName + "-" + randomNumber(10000));
            nodeView.setText(tableName);
            nodeView.setIcon("resources/mesofi/img/database-table.png");
            children.add(nodeView);
        }
        return new JsonPlainResponse(children);
    }

    /**
     * Done
     */
    @GET
    public WebResponse retrieveTableColumns(PageParameters pageParameters) {
        String connIdSelected = pageParameters.getValue("conn-id");
        Integer connId = Integer.parseInt(connIdSelected.split("-")[1]);

        String databaseSchemaIdSelected = pageParameters.getValue("database-schema-id");
        String[] idCompound = databaseSchemaIdSelected.split("-");
        String databaseSchema = idCompound[1];
        Boolean isDatabase = Boolean.parseBoolean(idCompound[2]);

        String tableNameSelected = pageParameters.getValue("table-name");
        String tableName = tableNameSelected.split("-")[1];

        if (log.isDebugEnabled()) {
            log.debug("Retrieving columnnames from [" + idCompound[0] + "]");
        }
        List<ColumnNameVo> columns = service.getColumnNames(connId, databaseSchema, tableName, isDatabase);
        List<TreeNodeView> children = new ArrayList<TreeNodeView>();
        TreeNodeView nodeView = null;
        for (ColumnNameVo column : columns) {
            nodeView = new TreeNodeView();
            nodeView.setChildren(false);
            nodeView.setId("column-" + column.getDescription() + "-" + randomNumber(10000));
            nodeView.setText(column.getDescription());
            nodeView.setIcon(column.isPrimaryKey() ? "resources/mesofi/img/database-column-key.png"
                    : "resources/mesofi/img/database-column.png");
            children.add(nodeView);
        }
        return new JsonPlainResponse(children);
    }

    /**
     * Done
     */
    @GET
    public WebResponse retrieveConnection(PageParameters pageParameters) {
        String connIdSelected = pageParameters.getValue("conn-id");
        Integer connId = Integer.parseInt(connIdSelected.split("-")[1]);
        if (log.isDebugEnabled()) {
            log.debug("Retrieving connection ..." + connIdSelected);
        }

        ConnectionDetailsVo connDetails = service.getConnectionDetails(connId);
        return new JsonResponse(connDetails);
    }

    /**
     * Creates a new database connection.
     * 
     * @param pageParameters
     *            Connection parameters.
     * @return Response as a result of the connection.
     */
    public WebResponse createNewDataSource(PageParameters pageParameters) {
        Integer connId = 0;
        Boolean isNewRecord = pageParameters.getValue("is-new-record", Boolean.class);
        if (!isNewRecord) {
            String connIdSelected = pageParameters.getValue("conn-id");
            connId = Integer.parseInt(connIdSelected.split("-")[1]);
        }
        if (log.isDebugEnabled()) {
            log.debug(isNewRecord ? "Creating datasource ..." : "Updating datasource...");
        }

        Integer databaseType = pageParameters.getValue("databaseType", Integer.class);
        String dbConnName = pageParameters.getValue("dbConnName");
        String user = pageParameters.getValue("user");
        String pass = pageParameters.getValue("pass");
        String host = pageParameters.getValue("host");
        String port = pageParameters.getValue("port");
        String databaseName = pageParameters.getValue("databaseName");
        Boolean isServiceNameInUse = pageParameters.getValue("serviceName", Boolean.class);

        DataSourceVo vo = null;
        vo = validateInputData(databaseType, dbConnName, user, pass, host, port, databaseName, isServiceNameInUse);
        vo.setId(connId);

        ConnectionDetailsVo connDetails = service.createOrUpdateRemoteDataSource(vo);
        return new JsonResponse(connDetails);
    }

    private DataSourceVo validateInputData(Integer databaseType, String dbConnName, String user, String pass,
            String host, String port, String databaseName, Boolean isServiceNameInUse) {
        DataSourceVo vo = new DataSourceVo();
        Map<String, List<String>> validations = new HashMap<String, List<String>>();

        // validation for the dbConnName field.
        if (isEmptyString(dbConnName)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.dbConnName.empty"));
            validations.put("dbConnName", list);
        } else if (dbConnName.length() > 50) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.dbConnName.len", "50"));
            validations.put("dbConnName", list);
        }
        // validation for the databaseType field.
        if (databaseType == 0) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.databaseType.empty"));
            validations.put("databaseType", list);
        }
        // validation for the host field.
        if (databaseType != 0 && DatabaseType.valueOf(databaseType).equals(DatabaseType.HSQLDB)) {
            if (!isEmptyString(host)) {
                if (host.length() > 50) {
                    List<String> list = new ArrayList<String>();
                    list.add(messages.getMessage("datasource.form.val.host.len", "50"));
                    validations.put("host", list);
                }
            }
        } else {
            if (isEmptyString(host)) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("datasource.form.val.host.empty"));
                validations.put("host", list);
            } else if (host.length() > 50) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("datasource.form.val.host.len", "50"));
                validations.put("host", list);
            }
        }
        // validation for the database name.
        if (isEmptyString(databaseName)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.databaseName.empty"));
            validations.put("databaseName", list);
        } else if (databaseName.length() > 60) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.databaseName.len", "60"));
            validations.put("databaseName", list);
        }
        // validation for the database port.
        if (databaseType != 0 && DatabaseType.valueOf(databaseType).equals(DatabaseType.HSQLDB)) {
            if (!isEmptyString(port)) {
                if (port.length() > 6) {
                    List<String> list = new ArrayList<String>();
                    list.add(messages.getMessage("datasource.form.val.port.len", "6"));
                    validations.put("port", list);
                } else {
                    try {
                        Integer.parseInt(port);
                    } catch (NumberFormatException ex) {
                        List<String> list = new ArrayList<String>();
                        list.add(messages.getMessage("datasource.form.val.port.fmt"));
                        validations.put("port", list);
                    }
                }
            }
        } else {
            if (isEmptyString(port)) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("datasource.form.val.port.empty"));
                validations.put("port", list);
            } else if (port.length() > 6) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("datasource.form.val.port.len", "6"));
                validations.put("port", list);
            } else {
                try {
                    Integer.parseInt(port);
                } catch (NumberFormatException ex) {
                    List<String> list = new ArrayList<String>();
                    list.add(messages.getMessage("datasource.form.val.port.fmt"));
                    validations.put("port", list);
                }
            }
        }
        // validation for the user.
        if (isEmptyString(user)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.user.empty"));
            validations.put("user", list);
        } else if (user.length() > 30) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("datasource.form.val.user.len", "30"));
            validations.put("user", list);
        }
        // validation for the pass field.
        if (!isEmptyString(pass)) {
            if (pass.length() > 30) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("datasource.form.val.pass.len", "30"));
                validations.put("pass", list);
            }
        }
        if (!validations.isEmpty()) {
            throw new ValidationBusinessException(validations);
        }

        vo.setDatabaseType(DatabaseType.valueOf(databaseType));
        vo.setDbConnName(dbConnName);
        vo.setDatabaseName(databaseName);
        vo.setHost(host);
        vo.setPort(fromNullToCustomValue(port, 0));
        vo.setUser(user);
        vo.setPassword(pass);
        vo.setServiceNameInUse(fromNullToCustomValue(String.valueOf(isServiceNameInUse), false));

        return vo;
    }

    /**
     * Deletes existing connections.
     * 
     * @param pageParameters
     *            Page parameters.
     */
    @GET
    public WebResponse deleteConnections(PageParameters pageParameters) {
        List<Integer> connId = new ArrayList<Integer>();
        String connIdSelected = pageParameters.getValue("conn-id");
        for (String conn : connIdSelected.split(",")) {
            connId.add(Integer.parseInt(conn.split("-")[1]));
        }
        int totalDeleted = service.deleteRemoteDataSource(connId);
        return new JsonResponse(totalDeleted);
    }

    /**
     * Duplicates existing connections.
     * 
     * @param pageParameters
     *            Page parameters.
     */
    @GET
    public WebResponse duplicateConnections(PageParameters pageParameters) {
        List<Integer> connId = new ArrayList<Integer>();
        String connIdSelected = pageParameters.getValue("conn-id");
        for (String conn : connIdSelected.split(",")) {
            connId.add(Integer.parseInt(conn.split("-")[1]));
        }
        int totalDuplicated = service.duplicateRemoteDataSource(connId);
        return new JsonResponse(totalDuplicated);
    }

    /**
     * Retrieves all the database or schema details.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Content for the response.
     */
    @GET
    public WebResponse retrieveSchemaDatabaseDetails(PageParameters pageParameters) {
        String connIdSelected = pageParameters.getValue("conn-id");
        Integer connId = Integer.parseInt(connIdSelected.split("-")[1]);

        String databaseSchemaIdSelected = pageParameters.getValue("database-schema-id");
        String[] idCompound = databaseSchemaIdSelected.split("-");
        String databaseSchema = idCompound[1];
        Boolean isDatabase = Boolean.parseBoolean(idCompound[2]);

        if (log.isDebugEnabled()) {
            String typeDb = isDatabase ? "database" : "schema";
            log.debug("Retrieving " + typeDb + " details from [" + idCompound[0] + "]");
        }
        SchemaDatabaseFullDetailsVo vo = service.getSchemaDatabaseDetails(connId, databaseSchema, isDatabase);
        return new JsonResponse(vo);
    }

    /**
     * Retrieves all table details.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Content for the response.
     */
    @GET
    public WebResponse retrieveTableNamesDetails(PageParameters pageParameters) {
        String connIdSelected = pageParameters.getValue("conn-id");
        Integer connId = Integer.parseInt(connIdSelected.split("-")[1]);

        String databaseSchemaIdSelected = pageParameters.getValue("database-schema-id");
        String[] idCompound = databaseSchemaIdSelected.split("-");
        String databaseSchema = idCompound[1];
        Boolean isDatabase = Boolean.parseBoolean(idCompound[2]);

        String tableNameSelected = pageParameters.getValue("table-name");
        String tableName = tableNameSelected.split("-")[1];

        if (log.isDebugEnabled()) {
            log.debug("Retrieving columnnames details from [" + tableName + "]");
        }
        ColumnFullDetailsVo vo = service.getTableDetails(connId, databaseSchema, tableName, isDatabase);
        return new JsonResponse(vo);
    }
}
