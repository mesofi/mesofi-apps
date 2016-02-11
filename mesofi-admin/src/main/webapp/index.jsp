
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Admin Mesofi</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        
        <!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
        <!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
        <!--script src="js/less-1.3.3.min.js"></script-->
        <!--append #!watch to the browser URL, then refresh the page. -->

        <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="resources/mesofi/css/mesofi.css" rel="stylesheet"><!-- Mesofi -->

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
        <![endif]-->

        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="resources/bootstrap/img/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="resources/bootstrap/img/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="resources/bootstrap/img/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="resources/bootstrap/img/apple-touch-icon-57-precomposed.png">
        <link rel="shortcut icon" href="resources/bootstrap/img/favicon.png">

        <script type="text/javascript" src="resources/bootstrap/js/jquery.min.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/scripts.js"></script>

        <link type="text/css" rel="stylesheet" href="resources/treeview/themes/default/style.min.css" />
        <script type="text/javascript" src="resources/treeview/jstree.min.js"></script>
        <script type="text/javascript" src="resources/mesofi/js/mesofi.js"></script>
        <script type="text/javascript" src="resources/mesofi/js/screens/configure-application.js"></script>
        <script type="text/javascript" src="resources/mesofi/js/screens/java-types.js"></script>
        <script type="text/javascript" src="resources/mesofi/js/screens/custom-code.js"></script>
        <script type="text/javascript" src="resources/mesofi/js/screens/custom-code-java-types.js"></script>
        <script type="text/javascript" src="resources/mesofi/js/screens/preview-code.js"></script>
        
        <link type="text/css" rel="stylesheet" href="resources/codemirror/css/codemirror.css">
        <link type="text/css" rel="stylesheet" href="resources/codemirror/css/night.css">
        <link type="text/css" rel="stylesheet" href="resources/selectize/css/selectize.bootstrap3.css">
        <link type="text/css" rel="stylesheet" href="resources/multiselect/css/bootstrap-multiselect.css">
        
        <script type="text/javascript" src="resources/codemirror/js/codemirror.js"></script>
        <script type="text/javascript" src="resources/codemirror/js/velocity.js"></script>
        <script type="text/javascript" src="resources/selectize/js/standalone/selectize.js"></script>
        <script type="text/javascript" src="resources/multiselect/js/bootstrap-multiselect.js"></script>
                
    </head>

    <body>
    	
        <div class="container" id="body-global-container">        
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <nav class="navbar navbar-default navbar-fix-top navbar-inverse" role="navigation">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> 
                            <a id="new-edit-conn-id" class="navbar-brand" data-toggle="modal" href="#modal-container-new-edit-conn" data-backdrop="static" data-keyboard="false">
                                <span class="glyphicon glyphicon-globe"></span>
                                New Connection
                            </a>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li>
                                    <a id="delete-conn-button" href="javascript:void(0)" data-toggle="tooltip" title="Delete Connection" data-placement="bottom">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </li>
                                <li>
                                    <a id="refresh-conn-button" href="javascript:void(0)" data-toggle="tooltip" title="Refresh All Connections" data-placement="bottom">
                                    	<span class="glyphicon glyphicon-refresh"></span>
                                    </a>
                                </li>
                                <li>
                                	<a id="duplicate-conn-button" href="javascript:void(0)" data-toggle="tooltip" title="Duplicate Connection" data-placement="bottom">
                                		<span class="glyphicon glyphicon-transfer"></span>
                                	</a>
                                </li>
                                <li>
                                	<a id="close-conn-button" href="javascript:void(0)" data-toggle="tooltip" title="Close Connections" data-placement="bottom">
                                		<span class="glyphicon glyphicon-resize-small"></span>
                                	</a>
                                </li>
                            </ul>
                            
                            <ul class="nav navbar-nav navbar-left">
                            	<li>
                            		
                            	</li>
                            </ul>
                            <ul class="nav navbar-nav navbar-right">
                            	<li>
                            		<a id="configure-application-id" class="navbar-brand" data-toggle="modal" href="#modal-container-configure-application" data-backdrop="static" data-keyboard="false">
                            		    <span class="glyphicon glyphicon-circle-arrow-down"></span>
                            		    Configure My Application
                            		</a>
                            	</li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    	<span class="glyphicon glyphicon-cog" style="font-size: 1.4em;"></span>
                                    <strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a id="java-types-id" data-toggle="modal" href="#modal-container-java-types" data-backdrop="static" data-keyboard="false">Configure Java Types</a>
                                        </li>
                                        <li>
                                            <a id="custom-code-id" data-toggle="modal" href="#modal-container-custom-code" data-backdrop="static" data-keyboard="false">Configure Custom Code</a>
                                        </li>
                                        <li class="divider" />
                                        <li>
                                            <a id="code-types-id" data-toggle="modal" href="#modal-container-code-types" data-backdrop="static" data-keyboard="false">Code Type Association</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <!-- Panel New Connection -->
                    <div class="modal fade in" id="modal-container-new-edit-conn" role="dialog" aria-labelledby="modal-title-label" aria-hidden="false">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="modal-title-label">
                                        New Connection
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <form action="new-edit-datasource.msf" method="post" id="new-edit-connection-form" class="form-horizontal" role="form">
                                        <input type="hidden" name="action" value="data-source-controller:create-new-data-source"/>
                                        <input type="hidden" name="is-new-record" value="true" />
                                        <input type="hidden" name="conn-id" value="" />
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="dbConnNameIcon" data-placement="left"></span>
                                            </span>
                                            <input type="text" name="dbConnName" class="form-control" placeholder="Connection name"/>
                                        </div>
                                        <br/>
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="databaseTypeIcon" data-placement="left"></span>
                                            </span>
                                            <select name="databaseType" class="form-control" id="databaseTypeId" >
                                                <option value="0">Select a database</option>
                                                <option value="1">Oracle</option>
                                                <option value="2">MySQL</option>
                                                <option value="3">Db2</option>
                                                <option value="4">HSQLDB</option>
                                            </select>
                                        </div>
                                        <br/>
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="hostIcon" data-placement="left"></span>
                                            </span>
                                            <input type="text" name="host" class="form-control" placeholder="Database server"/>
                                        </div>
                                        <br/>
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="databaseNameIcon" data-placement="left"></span>
                                            </span>
                                            <input type="text" name="databaseName" class="form-control" placeholder="Database name"/>
                                        </div>
                                        <br/>
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="portIcon" data-placement="left"></span>
                                            </span>
                                            <input type="text" name="port" class="form-control" placeholder="Database port"/>
                                        </div>
                                        <br/>
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-user" id="userIcon" data-placement="left"></span>
                                            </span>
                                            <input type="text" name="user" class="form-control" placeholder="User name"/>

                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-user" id="passIcon" data-placement="bottom"></span>
                                            </span>
                                            <input type="password" name="pass" class="form-control" placeholder="Password"/>
                                        </div>
                                        <div class="input-group input-group-sm">
                                            <div class="checkbox">
                                                <label><input name="serviceName" type="checkbox" /> Is service name?</label>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <span class="glyphicon glyphicon-globe" id="connection-status-msg" data-placement="top" style="font-size: 1.4em;"></span>
                                    &nbsp;&nbsp;&nbsp;
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        <span class="glyphicon glyphicon-remove"></span>
                                        Close
                                    </button>
                                    <button type="button" id="new-edit-conn-button" class="btn btn-primary">
                                        <span class="glyphicon glyphicon-ok"></span>
                                        Create
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End of Panel -->
                    <!-- Panel Java types -->
                    <div class="modal fade in" id="modal-container-java-types" role="dialog" aria-labelledby="modal-title-label" aria-hidden="false">
                        <div class="modal-dialog modal-dialog-java-types">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="modal-title-label">
                                        Available SQL and Java Types
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <form action="java-database-types.msf" method="post" id="java-database-types-form" class="form-horizontal" role="form">
                                        <input type="hidden" name="action" value="configurator-app-controller:save-java-types-changes"/>
                                        
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="databaseIcon" data-placement="left"></span>
                                            </span>
                                            <select name="databaseType" class="form-control" id="database-type-id-select"></select>
                                        </div>
                                        <div class="table-responsive">
                                            <table id="java-table-container" class="table table-striped table-hover table-condensed">
                                                <thead>
                                                    <tr>
                                                        <th>Database Type</th>
                                                        <th>Sql Type</th>
                                                        <th width="250px">Java Type</th>
                                                        <th>Effective</th>
                                                    </tr>
                                                </thead>
                                                <tbody class="table-striped">
                                                </tbody>
                                            </table>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End of Panel -->
                    <!-- Panel Custom Code types -->
                    <div class="modal fade in" id="modal-container-custom-code" role="dialog" aria-labelledby="modal-title-label" aria-hidden="false">
                        <div class="modal-dialog modal-dialog-config-code">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="modal-title-label">
                                        Available Custom Code
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <div class="control-group">
                                        <select id="select-pointcut" class="demo-default" placeholder="Select or create a new pointcut...">
                                            <option value="0">Select a pointcut...</option>
                                        </select>
                                    </div>
                                    <form action="custom-code.msf" method="post" id="custom-code-form" class="form-horizontal" role="form" >
                                        <div class="input-group input-group-sm" ></div>
                                        <!-- generated code editor goes here -->
                                    </form>
                                    <button type="button" id="custom-code-add-button" class="btn btn-primary"> 
                                        <span class="glyphicon glyphicon-plus"></span>
                                        Add Code
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End of Panel -->
                    <!-- Panel Code Types -->
                    <div class="modal fade in" id="modal-container-code-types" role="dialog" aria-labelledby="modal-title-label" aria-hidden="false">
                        <div class="modal-dialog modal-dialog-config-types">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="modal-title-label">
                                        Available Custom Code and Types 
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <form action="config-database-types.msf" method="post" id="config-database-types-form" class="form-horizontal" role="form">
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="sectionIcon" data-placement="left"></span>
                                            </span>
                                            <select name="sectionType" class="form-control" id="section-type-id-select" ></select>
                                        </div>
                                        <!-- generated code goes here -->
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        <span class="glyphicon glyphicon-remove"></span>
                                        Close
                                    </button>
                                    <button type="button" id="preview-code-button" class="btn btn-primary"> 
                                        <span class="glyphicon glyphicon-eye-open"></span>
                                         Preview Code
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End of Panel -->
                    <!-- Panel for Preview the code -->
                    <div class="modal fade in" id="modal-container-preview-code" role="dialog" aria-labelledby="modal-title-label" aria-hidden="false">
                        <div class="modal-dialog modal-dialog-preview-code">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="modal-title-label">
                                        <span id="preview-code-title"></span> 
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <div id="preview-all-code-generated">
                                        <!-- Code preview generated -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        <span class="glyphicon glyphicon-remove"></span>
                                        Close
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End of Panel -->
                    <!-- Panel for Configure My Application -->
                    <div class="modal fade in" id="modal-container-configure-application" role="dialog" aria-labelledby="modal-title-label" aria-hidden="false">
                        <div class="modal-dialog modal-dialog-configure-application">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="modal-title-label">
                                        Configure My Application 
                                    </h4>
                                </div>
                                <div class="modal-body">
                                    <span class="glyphicon glyphicon-floppy-disk" id="app-status-msg" data-placement="top" style="font-size: 1.4em;"></span>
                                    &nbsp;&nbsp;&nbsp;
                                    <button type="button" id="download-app-button1" class="btn btn-primary"> 
                                        <span class="glyphicon glyphicon-download-alt"></span>
                                        ???
                                    </button>
                                    <p/>
                                    <div class="alert alert-info" role="alert" style="padding: 4px"><div class="glyphicon glyphicon-globe"></div>&nbsp;
                                        Database connection
                                    </div>
                                    <table id="connection-database-table-container" class="table table-striped table-hover table-condensed">
                                		<thead>
                                			<tr>
                                			    <th/>
                                				<th>Database Type</th>
                                				<th>Connection name</th>
                                				<th>Database</th>
                                				<th>Tables</th>
                                			</tr>
                                		</thead>
                                		<tbody class="table-striped">
                                		    <!-- Connections availables, code generated -->
                                		</tbody>
                                	</table>
                                	<div class="alert alert-info" role="alert" style="padding: 4px"><div class="glyphicon glyphicon-paperclip"></div>&nbsp;
                                        Plugin setup
                                    </div>
                                    <table id="plugin-container" class="table table-striped table-hover table-condensed">
                                		<thead>
                                			<tr>
                                			    <th/>
                                				<th>Title</th>
                                				<th>Description</th>
                                			</tr>
                                		</thead>
                                		<tbody class="table-striped">
                                		    <!-- Plugin availables, code generated -->
                                		</tbody>
                                	</table>
                                	<form action="project-details.msf" method="post" id="project-details-form" class="form-horizontal" role="form">
                                	    <input type="hidden" name="action" value="builder-app-controller:process-app"/>
                                        <div class="alert alert-info" role="alert" style="padding: 4px"><div class="glyphicon glyphicon-list-alt"></div>&nbsp;
                                            Project Information
                                        </div>
                                        <div class="input-group input-group-sm">
                                            <span class="input-group-addon ">
                                                <span class="glyphicon glyphicon-pencil" id="webProjectNameIcon" data-placement="top"></span>
                                            </span>
                                            <input type="text" id="webProjectNameId" name="webProjectName" class="form-control" placeholder="Project name"/>
                                        </div>
                                        <br/>
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon ">
                                                        <span class="glyphicon glyphicon-pencil" id="webGroupIdIcon" data-placement="bottom"></span>
                                                    </span>
                                                    <input type="text" id="webGroupIdId" name="webGroupId" class="form-control" placeholder="Group Id"/>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon ">
                                                        <span class="glyphicon glyphicon-pencil" id="webArtifactIdIcon" data-placement="top"></span>
                                                    </span>
                                                    <input type="text" id="webArtifactIdId" name="webArtifactId" class="form-control" placeholder="Artifact Id"/>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon ">
                                                        <span class="glyphicon glyphicon-pencil" id="webVersionIcon" data-placement="top"></span>
                                                    </span>
                                                    <input type="text" id="webVersionId" name="webVersion" class="form-control" placeholder="Version"/>
                                                </div>
                                            </div>
                                        </div>
                                        <br/>
                                        <div class="alert alert-info" role="alert" style="padding: 4px"><div class="glyphicon glyphicon-wrench"></div>&nbsp;
                                            General configuration
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon ">
                                                        <span class="glyphicon glyphicon-pencil" id="authModuleNameIcon" data-placement="top"></span>
                                                    </span>
                                                    <input type="text" id="authModuleNameId" name="authModuleName" class="form-control" placeholder="Authorization module name"/>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon ">
                                                        <span class="glyphicon glyphicon-pencil" id="mainModuleNameIcon" data-placement="top"></span>
                                                    </span>
                                                    <input type="text" id="mainModuleNameId" name="mainModuleName" class="form-control" placeholder="Main module name"/>
                                                </div>
                                            </div>
                                        </div>
                                        <br/>
                                        <div id="additionalConfigId">
                                            <!-- Additional config available, code generated -->
                                        </div>
                                        <input type="hidden" id="webAdditionalConfigId" name="webAdditionalConfig" />
                                    </form>
                                    <button type="button" id="download-app-button2" class="btn btn-primary"> 
                                        <span class="glyphicon glyphicon-download-alt"></span>
                                        Download My Web App
                                    </button>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        <span class="glyphicon glyphicon-remove"></span>
                                        Close
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End of Panel -->
                    <!-- Application errors -->
                    <div style="text-align:right; display: none" id="application-error-global-container">
                    	<span class="custom" id="application-error-global" data-placement="left" style="width: 32px; height: 32px; background-image:url('resources/mesofi/img/application-error-v1.png'); display: inline-block;">
                    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	</span>
                    </div>
                    <!-- Application messages -->
                    <div class="alert alert-success alert-dismissable" id="application-alert" style="display:none;">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <span id="application-alert-text" class="glyphicon glyphicon-ok-sign"></span>
                    </div>
                    <!-- Warnings messages when trying to delete a connection -->
                    <div class="alert alert-warning fade in" id="application-confirm" style="display:none;">
                    	<span id="application-confirm-text"></span>
                    	<p>
                    		<button type="button" class="btn btn-warning" id="delete-all-conn-button">Delete</button>
                    		<button type="button" class="btn btn-default" id="cancel-conn-button">Cancel</button>
                    	</p>
                    </div>                  
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <div id="jstree">
                        <!-- TreeView -->
                    </div>
                </div>
                <div class="col-md-8 column">
                    <div class="tabbable" id="tabs-navigation">
                        <ul class="nav nav-tabs">
                            <li id="panel-connection-tab" class="active">
                                <a id="panel-connection-title" href="#panel-connection-tab-content" data-toggle="tab">[Select Connection]</a>
                            </li>
                            
                            <li id="panel-schema-database-tab">
                                <a id="panel-schema-database-title" href="#panel-schema-database-tab-content" data-toggle="tab">[Database / Schema]</a>
                            </li >

                            <li id="panel-tablename-tab">
                                <a id="panel-tablename-title" href="#panel-tablename-tab-content" data-toggle="tab">[Table]</a>
                            </li>
                            
                            <li id="panel-preparation-tab">
                            	<a id="panel-preparation-title" href="#panel-preparation-tab-content" data-toggle="tab">Configured Tables</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-connection-tab-content">
                                <div class="row clearfix">
                                    <div class="col-md-12 column">
                                        <h4 class="text-left text-primary">Database details</h4><hr/>
                                        <dl class="dl-horizontal">
                                            <dt>
                                            Name:
                                            </dt>
                                            <dd id="prop-database-name" />
                                            <dt>
                                            Version:
                                            </dt>
                                            <dd id="prop-database-version"/>
                                            <dt>
                                            Driver Name:
                                            </dt>
                                            <dd id="prop-database-driver-name"/>
                                            <dt>
                                            Driver Version:
                                            </dt>
                                            <dd id="prop-database-driver-version"/>
                                        </dl>
                                        <h4 class="text-left text-primary">Connection details</h4><hr/>
                                        <dl class="dl-horizontal">
                                            <dt>
                                            URL:
                                            </dt>
                                            <dd id="prop-database-url" />
                                            <dt>
                                            Database server:
                                            </dt>
                                            <dd id="prop-database-server"/>
                                            <dt>
                                            Database port:
                                            </dt>
                                            <dd id="prop-database-port"/>
                                            <dt>
                                            Database:
                                            </dt>
                                            <dd id="prop-database-instance"/>
                                            <dt>
                                            User:
                                            </dt>
                                            <dd id="prop-database-user-name"/>
                                        </dl>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane" id="panel-schema-database-tab-content">
                                <div class="table-responsive">
                                	<h4 class="text-left text-primary">Table list</h4><hr/>
                                	<dl class="dl-horizontal">
                                		<dt>
                                		Tables:
                                		</dt>
                                		<dd id="prop-database-total-tables" />
                                		<dt>
                                		Views:
                                		</dt>
                                		<dd id="prop-database-total-views"/>
                                		<dt>
                                		System Tables:
                                		</dt>
                                		<dd id="prop-database-total-system-tables"/>
                                		<dt>
                                		Global Temporary:
                                		</dt>
                                		<dd id="prop-database-total-global-temporary"/>
                                		<dt>
                                		Local Temporary:
                                		</dt>
                                		<dd id="prop-database-total-local-temporary"/>
                                		<dt>
                                		Total Alias:
                                		</dt>
                                		<dd id="prop-database-total-alias"/>
                                		<dt>
                                		Total Synonym:
                                		</dt>
                                		<dd id="prop-database-total-synonym"/>
                                	</dl>
                                	<hr/>
                                	<table id="schema-database-table-container" class="table table-striped table-hover table-condensed">
                                		<thead>
                                			<tr>
                                				<th>Catalog</th>
                                				<th>Schema</th>
                                				<th>Name</th>
                                				<th>Type</th>
                                				<th>Remarks</th>
                                			</tr>
                                		</thead>
                                		<tbody class="table-striped">
                                			
                                		</tbody>
                                	</table>
                                </div>
                            </div>
                            <div class="tab-pane" id="panel-tablename-tab-content">
                                <div class="table-responsive">
                                	<table style="width: 100%">
                                		<tr>
                                			<td>
                                				<h4 class="text-left text-primary">
                                					Column list
                                					<!-- temporal fields for retrieving later -->
                                					<input type="hidden" id="tmp-setup-connId" />
                                					<input type="hidden" id="tmp-setup-databaseSchemaName" />
                                					<input type="hidden" id="tmp-setup-database" />
                                					<input type="hidden" id="tmp-setup-tableName" />
                                				</h4>
                                			</td>
                                			<td style="text-align: right; padding-top: 10px">
                                				<button type="button" id="include-table-button" class="btn btn-danger :active btn-large">
                                					<span class="glyphicon glyphicon-thumbs-down"></span>
                                					Add
                                				</button>
                                			</td>
                                		</tr>
                                	</table>
                                	<hr/>
                                	<dl class="dl-horizontal">
                                		<dt>
                                		Columns:
                                		</dt>
                                		<dd id="prop-database-total-columns" />
                                		<dt>
                                		PK:
                                		</dt>
                                		<dd id="prop-database-total-pk" />
                                	</dl>
                                	<hr/>
                                	<table id="columns-table-container" class="table table-striped table-hover table-condensed">
                                		<thead>
                                			<tr>
                                				<th/>
                                				<th>Name</th>
                                				<th>Type</th>
                                				<th>Size</th>
                                				<th>Scale</th>
                                				<th>Nullable</th>
                                				<th>Remarks</th>
                                			</tr>
                                		</thead>
                                		<tbody class="table-striped">
                                			
                                		</tbody>
                                	</table>
                                </div>
                            </div>
                            <div class="tab-pane" id="panel-preparation-tab-content">
                            	<div class="table-responsive">
                                	<h4 class="text-left text-primary">Summary</h4>
                                	<hr/>
                                	<table id="prepare-app-table-container" class="table table-striped table-hover table-condensed">
                                		<thead>
                                			<tr>
                                				<th>Table Name</th>
                                			</tr>
                                		</thead>
                                		<tbody class="table-striped">
                                			
                                		</tbody>
                                	</table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
