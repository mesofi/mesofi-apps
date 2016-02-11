$(document).ready(function() {
    var latestConn = '';
    $('#delete-conn-button').tooltip();
    $('#refresh-conn-button').tooltip();
    $('#duplicate-conn-button').tooltip();
    $('#close-conn-button').tooltip();

    $('#modal-container-new-edit-conn').on('show.bs.modal', function(e) {
        // shows the dialog to create a new connection
        $("#new-edit-connection-form input[name=dbConnName]").val('');
        $("#new-edit-connection-form select[name=databaseType]").val(0);
        $("#new-edit-connection-form input[name=host]").val('');
        $("#new-edit-connection-form input[name=databaseName]").val('');
        $("#new-edit-connection-form input[name=port]").val('');
        $("#new-edit-connection-form input[name=user]").val('');
        $("#new-edit-connection-form input[name=pass]").val('');
        $("#new-edit-connection-form input[name=serviceName]").prop('checked', false);
        $("#new-edit-connection-form input[name=is-new-record]").val(true);
        $("#new-edit-connection-form input[name=conn-id]").val('');
        $("#modal-title-label").text('New Connection');
        $("#new-edit-conn-button").html('<span class="glyphicon glyphicon-ok"></span> Create');
        $("#new-edit-conn-button").attr('disabled', false);

        $.fn.initFormConnections(true);
    });
    /**
     * When a database connection is changed.
     */
    $(function() {
    	$('#database-type-id-select').on('change', function(e) {
    		if(this.value == '0') {
    			return;
    		}
    		// gets the types related to this identifier.
    		$.fn.blockScreen(true);
        	$.ajax({
        		type: 'GET',
        		url: 'configurator.msf?id-database=' + this.value,
        		dataType: 'json',
        		data: {'action': 'configurator-app-controller:retrieve-java-types-by-database'},
        		headers: {'async': 'true'}
        	}).done(function(info) {
        		$.fn.blockScreen(false);
        		if (info.success) {
        			// got a success response from back end.
        			var tableSelected = $('#java-table-container > tbody');
        			tableSelected.html("");
        			var hasRecords = false;
            		$.each(info.data, function (i, val) {
            			hasRecords = true;
            			tableSelected.append("<tr>" +
            					"<td>" + $.fn.createLabelDbByType(val.database, val.id) + "</td>" +
            					"<td>" + $.fn.createLabelSqlByType(val.sql, val.idSql) + "</td>" +
            					"<td>" + $.fn.createInputByType(val.java, 'javaType', val.id) + "</td>" +
            					"<td>" + $.fn.createCheckBoxByType(val.effectiveType, 'effectiveType', val.id) + "</td></tr>");
            		});
            		if (hasRecords) {
            			tableSelected.append("<tr><td colspan='4' align='right'>" + $.fn.createButtonsByType() + "</td></tr>");
            		}
        		} else {
        			$.fn.showApplicationError(info.message.text, 'application-error-global', false);	
        		}
        	}).fail(function(info) {
        		$.fn.blockScreen(false);
        		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
    	});
    });
    
    /**
     * Delete connection
     */
    $(function() {
        $('#delete-conn-button').click(function(e) {
        	$('#application-confirm').hide();
        	var idSelectedArr = $('#jstree').jstree("get_selected", true);
        	if (idSelectedArr.length != 0) {
        		var nodeSelected = null;
        		var totalSelected = 0;
        		var textSelected = null;
        		$.each(idSelectedArr, function( index, value ) {
        			nodeSelected = value.id;
        			if ( nodeSelected.indexOf('connection') !== -1 ) {
        				totalSelected++;
        				textSelected = value.text;
        			}
        		});
        		if (totalSelected > 0) {
        			var message = "Are you sure you want to delete ";
        			if (totalSelected == 1) {
        				message = message + '<b>' + textSelected + '</b> connection?';
        			} else {
        				message = message + '<b>' + totalSelected + '</b> existing connections?';
        			}
        			$('#application-confirm-text').html(message);
        			$('#application-confirm').show();
        		}
        	} else {
        		// no connection was selected.
        	}
        })
    });
    
    /**
     * Update connection tree
     */
    $(function() {
    	$('#refresh-conn-button').click(function(e) {
    		$('#jstree').jstree("refresh");  // refreshes the tree
    	})
    });
    
    /**
     * Duplicate existing connection in tree
     */
    $(function() {
    	$('#duplicate-conn-button').click(function(e) {
    		$('#application-confirm').hide();
        	var idSelectedArr = $('#jstree').jstree("get_selected");
        	if (idSelectedArr.length != 0) {
        		var totalSelected = 0;
        		var list = new Array();
        		$.each(idSelectedArr, function( index, value ) {
        			if ( value.indexOf('connection') !== -1 ) {
        				totalSelected++;
        				list.push(value);
        			}
        		});
        		if (totalSelected > 0) {
        			// duplicates the connections.
        			$.fn.blockScreen(true);
        			$.ajax({
                        type: 'GET',
                        url: 'duplicate-datasource.msf?action=data-source-controller:duplicate-connections',
                        data: {'conn-id': list.join(',')},
                        headers: {'async': 'true'},
                        success: function(response) {
                        	$.fn.blockScreen(false);
                        	if (response.success) {
                        		var total = response.data;
                        		var description = total + ' connection(s) has been duplicated';
                        		$.fn.showMessageAlert(description, 'success');
                        		$('#jstree').jstree("refresh");  // after the copy, refreshes the tree
                        	} else {
                        		var description = response.message.text;
                        		$.fn.showApplicationError(description, 'application-error-global', false);
                        	}
                        },
                        error: function(response) {
                        	$.fn.blockScreen(false);
                        	$.fn.showApplicationError(response.responseText, 'application-error-global', false);
                        }
                	});
        		}
        	}
    	})
    });
    /**
     * Duplicate existing connection in tree
     */
    $(function() {
    	$('#close-conn-button').click(function(e) {
    		$('#jstree').jstree("close_all");  // closes all nodes
    	})
    });
    $(function() {
        $('#delete-all-conn-button').click(function(e) {
        	$('#application-confirm').hide();
        	var idSelectedArr = $('#jstree').jstree("get_selected");
        	var nodeSelected = null;
        	var list = new Array();
        	$.each(idSelectedArr, function( index, value ) {
        		if ( value.indexOf('connection') !== -1 ) {
        			list.push(value);
        		}
        	});
        	// deletes the current selected connections.
        	$.fn.blockScreen(true);
        	$.ajax({
                type: 'GET',
                url: 'delete-datasource.msf?action=data-source-controller:delete-connections',
                data: {'conn-id': list.join(',')},
                headers: {'async': 'true'},
                success: function(response) {
                	$.fn.blockScreen(false);
                	if (response.success) {
                		var total = response.data;
                		var description = total + ' connection(s) has been deleted';
                		$.fn.showMessageAlert(description, 'success');
                		$('#jstree').jstree("refresh");  // after deletion, refreshes the tree
                	} else {
                		var description = response.message.text;
                		$.fn.showApplicationError(description, 'application-error-global', false);
                	}
                },
                error: function(response) {
                	$.fn.blockScreen(false);
                	$.fn.showApplicationError(response.responseText, 'application-error-global', false);
                }
        	});
        })
    });
    $(function() {
        $('#cancel-conn-button').click(function(e) {
        	$('#application-confirm').hide();
        })
    });
    $(function() {
    	$('#include-table-button').click(function(e) {
    		// saves the current configuration.
    		$.fn.blockScreen(true);
    		$('#include-table-button').prop('disabled', true);
        	$.ajax({
        		type: 'POST',
        		url: 'configurator.msf',
        		dataType: 'json',
        		data: {
        			'action': 'builder-app-controller:pre-build-app',
        			'conn-id': $('#tmp-setup-connId').val(), 
        			'database-schema': $('#tmp-setup-databaseSchemaName').val(),
        			'is-database': $('#tmp-setup-database').val(),
        			'table-name': $('#tmp-setup-tableName').val()},
        		headers: {'async': 'true'}
        	}).done(function(info) {
        		$.fn.blockScreen(false);
        		$('#include-table-button').prop('disabled', false);
        		if (info.success) {
	        		// modify the button label
        			$.fn.allowToAddConfig(false, true);
        		} else {
        			var description = info.message.text;
            		$.fn.showApplicationError(description, 'application-error-global', false);
        		}
        	}).fail(function(info) {
        		$.fn.blockScreen(false);
        		$('#include-table-button').prop('disabled', false);
        		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
    	})
    });
    
    $(function() {
        $('#new-edit-conn-button').click(function(e) {
            e.preventDefault();
            var oldText = $("#new-edit-conn-button").html();
            $("#new-edit-conn-button").attr('disabled', true);
            $("#new-edit-conn-button").text('Connecting ...');
            
            $.fn.blockScreen(true);
            $.ajax({
                type: 'POST',
                url: 'new-edit-datasource.msf',
                headers: {'async': 'true'},
                data: $('#new-edit-connection-form').serialize(),
                success: function(response) {
                	$.fn.blockScreen(false);
                    $("#new-edit-conn-button").html(oldText);
                    if (!response.success) {
                        // some error occurred, business or some other
                        if (response.message.errorType === 'BUSINESS') {
                            $.fn.createConnValidations('new-edit-connection-form', response.message.fields);
                            $("#new-edit-conn-button").attr('disabled', false);
                            $("#new-edit-conn-button").html(oldText);
                            $("#new-edit-conn-button").removeClass('btn-danger').addClass('btn-primary');
                        } else {
                        	$.fn.showApplicationError('Unknown error', 'application-error-global', false);
                        }
                    } else {
                        $.fn.initFormConnections(false);
                        // receive a correct response
                        if (response.data.success) {
                            var idForm = $("#new-edit-connection-form input[name=conn-id]").val();
                            var connName = $("#new-edit-connection-form input[name=dbConnName").val();
                            var messageSuccess = '';
                            if (idForm === '') {
                                messageSuccess = ' A new connection [<b>' + connName + '</b>] has been created and connected successfully!!';
                            } else {
                                messageSuccess = ' Current connection [<b>' + connName + '</b>] has been updated';
                            }
                            latestConn = 'connection-' + response.data.dataSourceVo.id;

                            $.fn.showMessageAlert(messageSuccess, 'success');
                            $('#application-confirm').hide(); // closes the delete confirmation dialog if any
                            $("#new-edit-conn-button").text('Opening ...');
                            // for new connections...
                            if (idForm === '') {
                                $('#jstree').jstree("close_all");
                                $('#jstree').jstree("refresh", latestConn);
                            } else {
                                $('#jstree').jstree("open_node", latestConn);
                                $('#modal-container-new-edit-conn').modal('hide');
                            }
                        } else {
                            $("#new-edit-conn-button").attr('disabled', false);
                            $("#new-edit-conn-button").html('<span class="glyphicon glyphicon-ok"></span> Reconnect');
                            $("#new-edit-conn-button").removeClass('btn-primary').addClass('btn-danger');
                            // show the error in connection
                            var errorConn = response.data.errorDescription;
                            var pretitle = '<b style="color: red">An error occurred while establishing the connection:</b><br/>';
                            errorConn = pretitle + errorConn;
                            $('#connection-status-msg').popover('destroy');
                            $('#connection-status-msg').popover({
                                title: 'Connection Status',
                                content: errorConn,
                                html: true,
                                trigger: 'hover'
                            }).popover('show');
                            $('#connection-status-msg').css('color', '#b94a48');
                        }
                    }
                },
                error: function(response) {
                	$.fn.blockScreen(false);
                	$.fn.showApplicationError(response.responseText, 'application-error-global', false);
                }
            });
        });
    });
    $(function() {
        $('#jstree').jstree({
            'core': {
                'themes': {'stripes': true},
                'data': {
                    'url': function(node) {
                    	$.fn.blockScreen(true);
                    	var url = null;
                        var id = node.id;
                        if (id === '#') {
                            // returns the root, get all the connections saved
                            url = 'connections.msf?action=data-source-controller:retrieve-connections';
                        } else if (id.indexOf('connection') !== -1) {
                        	// returns the database details for a certain database connection.
                            url = 'connections.msf?action=data-source-controller:retrieve-schema-database&conn-id=' + id + '&conn-text=' + node.text;
                        } else if (id.indexOf('database') !== -1) {
                            url = 'connections.msf?action=data-source-controller:retrieve-table-names&conn-id=' + node.parent + '&database-schema-id=' + id;
                        } else {
                            url = 'connections.msf?action=data-source-controller:retrieve-table-columns&conn-id=' + node.parents[1] + '&database-schema-id=' + node.parent + '&table-name=' + id;
                        }
                        return url;
                    }, 'data': function(node) {
                        // append parameters in the request.
                    }, 'success': function(node) {
                    	$.fn.blockScreen(false);
                        var children = null;
                        
                        if (node.id !== undefined) {
                            // root node
                            children = node.children;
                        } else {
                            // children
                            children = node;
                        }
                        var indexToDelete = -1;
                        $.each(children, function(index, result) {
                            if ((result.id).indexOf('connection-error') !== -1) {
                                indexToDelete = index;
                            } else if ((result.id).indexOf('database-error') !== -1) {
                            	indexToDelete = index;
                            }
                        });
                        if (indexToDelete !== -1) {
                        	// before removing the element, shows the message.
                        	$.fn.showMessageAlert(children[indexToDelete].text, 'danger');
                            children.splice(indexToDelete, 1);
                            
                            //$("#connection-6 > a").css("color","red");
                        	//alert($("#connection-6 > a").attr('class'));
                        }
                    }, 'error': function(jqXHR, textStatus, errorThrown) {
                    	$.fn.blockScreen(false);
                    	var container = '<span id="application-error-tree" data-placement="bottom" style="font-size: 1.4em;"></span>';
                    	
                    	var content = '<ul class="jstree-container-ul jstree-striped"><li role="treeitem" id="root-data-sources" class="jstree-node  jstree-leaf jstree-last" aria-selected="false"><i class="jstree-icon jstree-ocl">';
                    	content = content + '</i>'+container+'<a class="jstree-anchor" href="#"><i class="jstree-icon jstree-themeicon jstree-themeicon-custom" style="background-image: url(http://localhost:8080/mesofi-admin/resources/mesofi/img/disconnect-all.png); ';
                    	content = content + 'background-size: auto; background-position: 50% 50%;"></i>Disconnected</a></li></ul>';
                    	$('#jstree').html(content);  // change the icon in treeview
                    	var message = 'Could not load the tree view due an application problem, please contact the System Administrator';
                    	
                    	$.fn.showApplicationError(message, 'application-error-tree', true);
                    	console.log(jqXHR.responseText);
                    }
                }
            }
        });
    });
   
    /**
     * Triggered when a refresh call completes
     */
    $('#jstree').on("refresh.jstree", function(event, data) {
        $('#jstree').jstree("open_node", '#' + latestConn);
    });
    
    /**
     * Opens a node, revaling its children. If the node is not loaded it will be loaded and opened once ready.
     */
    $('#jstree').on("open_node.jstree", function(event, data) {
    	// it does a async request to populate the tab contents...
    	var node = data.node;
    	var id = node.id;
    	
    	if (id.indexOf('root-data-sources') !== -1 ) {
    		return; // root node
    	}
    	
        if (id.indexOf('connection') !== -1 ) {
        	$.fn.blockScreen(true);
        	$.ajax({
        		type: 'GET',
        		url: 'metadata.msf?action=data-source-controller:retrieve-connection&conn-id=' + id,
        		dataType: 'json',
        		headers: {'async': 'true'}
        	}).done(function(info) {
        		$.fn.blockScreen(false);
        		if (info.success) {
	        		$.fn.activateTabById('panel-connection-tab');
	        		$('#panel-connection-title').text(info.data.dataSourceVo.dbConnName);
	        		
	        		// populates the details...
	        		$('#prop-database-name').text(info.data.metadata.name);
	        		$('#prop-database-version').text(info.data.metadata.version);
	        		$('#prop-database-driver-name').text(info.data.metadata.driverName);
	        		$('#prop-database-driver-version').text(info.data.metadata.driverVersion);
	        		$('#prop-database-url').text(info.data.dataSourceVo.url);
	        		$('#prop-database-server').text(info.data.dataSourceVo.host);
	        		$('#prop-database-port').text(info.data.dataSourceVo.port);
	        		$('#prop-database-instance').text(info.data.dataSourceVo.databaseName);
	        		$('#prop-database-user-name').text(info.data.dataSourceVo.user);
	
	        		if (latestConn !== '') {
	        			// closes the dialog
	        			$('#modal-container-new-edit-conn').modal('hide');
	        			$("#new-edit-conn-button").attr('disabled', false);
	        		}
        		} else {
        			var description = info.message.text;
            		$.fn.showApplicationError(description, 'application-error-global', false);
        		}
        	}).fail(function(info) {
        		$.fn.blockScreen(false);
        		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
        } else if (id.indexOf('database') !== -1) {
        	$.fn.blockScreen(true);
        	$.ajax({
        		type: 'GET',
        		url: 'metadata.msf?action=data-source-controller:retrieve-schema-database-details&conn-id=' + node.parent + '&database-schema-id=' + id,
        		dataType: 'json',
        		headers: {'async': 'true'}
        	}).done(function(info) {
        		$.fn.blockScreen(false);
        		if (info.success) {
        			$.fn.activateTabById('panel-schema-database-tab');
            		$('#panel-schema-database-title').text(info.data.schemaOrDatabase);
            		
            		// populates the details...
            		$('#prop-database-total-tables').text(info.data.totalTables);
            		$('#prop-database-total-views').text(info.data.totalViews);
            		$('#prop-database-total-system-tables').text(info.data.totalSystemTables);
            		$('#prop-database-total-global-temporary').text(info.data.totalGlobalTemporary);
            		$('#prop-database-total-local-temporary').text(info.data.totalLocalTemporary);
            		$('#prop-database-total-alias').text(info.data.totalAlias);
            		$('#prop-database-total-synonym').text(info.data.totalSynonym);
            		
            		var tableSelected = $('#schema-database-table-container > tbody');
            		tableSelected.html("");
            		var commentsContent = null;
            		var idComment = null;
            		$.each(info.data.metadata, function (i, val) {
            			commentsContent = val.remarks;
            			if (commentsContent.length > 0 ) {
            				// contains comments
            				commentsContent = "<span style='color: rgb(255, 153, 51);' data-placement='top' class='glyphicon glyphicon-comment' id='remark-table"+i+"'></span>";
            				idComment = "remark-table"+i;
            			}
            			tableSelected.append("<tr><td>" + val.cat + "</td><td>" + val.schem + "</td><td>" + val.name + "</td><td>" + val.type + "</td><td>" + commentsContent + "</td></tr>");
            			if (commentsContent.length > 0 ) {
            				$("#"+idComment).popover('destroy');
            				$("#"+idComment).popover({
            		            content: val.remarks,
            		            html: true,
            		            trigger: 'hover'
            		        });
            			}
            		});
        		} else {
        			var description = info.message.text;
            		$.fn.showApplicationError(description, 'application-error-global', false);
        		}
        	}).fail(function(info) {
        		$.fn.blockScreen(false);
        		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
        } else {
        	$.fn.blockScreen(true);
        	$.ajax({
        		type: 'GET',
        		url: 'metadata.msf?action=data-source-controller:retrieve-table-names-details&conn-id=' + node.parents[1] + '&database-schema-id=' + node.parent + '&table-name=' + id,
        		dataType: 'json',
        		headers: {'async': 'true'}
        	}).done(function(info) {
        		$.fn.blockScreen(false);
        		if (info.success) {
        			$.fn.activateTabById('panel-tablename-tab');
            		$('#panel-tablename-title').text(info.data.tableName);
            		
            		// populates the details...
            		$('#prop-database-total-columns').text(info.data.totalColumns);
            		$('#prop-database-total-pk').text(info.data.totalPrimaryKeys);
            		
            		var tableSelected = $('#columns-table-container > tbody');
            		tableSelected.html("");
            		var commentsContent = null;
            		var idComment = null;
            		var pk = null;
            		var type = null;
            		
            		$.each(info.data.metadata, function (i, val) {
            			commentsContent = val.remarks;
            			if (commentsContent.length > 0 ) {
            				// contains comments
            				commentsContent = "<span style='color: rgb(255, 153, 51);' data-placement='top' class='glyphicon glyphicon-comment' id='remark-column"+i+"'></span>";
            				idComment = "remark-column"+i;
            			}
            			//primaryKey
            			if (val.primaryKey) {
            				pk = "<span style='color: rgb(255, 153, 51);' class='glyphicon glyphicon-star'></span>";
            			} else {
            				pk = "<span style='color: rgb(255, 153, 51);' class='glyphicon glyphicon-star-empty'></span>";
            			}
            			// description
            			if (val.permittedValues == null) {
            				type = val.typeName;
            			} else {
            				type = val.typeName + ' (' + val.permittedValues + ')';
            			}
            			tableSelected.append("<tr><td>"+pk+"</td><td>" + val.description + "</td><td>" + type + "</td><td>" + val.size + "</td><td>" + val.scale + "</td><td>" + val.nullable + "</td><td>" + commentsContent + "</td></tr>");
            			if (commentsContent.length > 0 ) {
            				$("#"+idComment).popover('destroy');
            				$("#"+idComment).popover({
            		            content: val.remarks,
            		            html: true,
            		            trigger: 'hover'
            		        });
            			}
            		});
            		var canBeSaved = false;
            		// validates whether this table can be saved or not
            		$.each(info.data.metadata, function (i, val) {
            			if (val.primaryKey) {
            				canBeSaved = true;		
            			}
            		});
            		
            		// saves some temporal variables related to the connection.
            		$('#tmp-setup-connId').val(info.data.connId);
            		$('#tmp-setup-databaseSchemaName').val(info.data.databaseSchemaName);
            		$('#tmp-setup-database').val(info.data.database);
            		$('#tmp-setup-tableName').val(info.data.tableName);
            		
            		// change the button to know if the configuration is saved or not
            		$.fn.allowToAddConfig(!info.data.configSaved, canBeSaved);
        		} else {
        			var description = info.message.text;
            		$.fn.showApplicationError(description, 'application-error-global', false);
        		}
        	}).fail(function(info) {
        		$.fn.blockScreen(false);
        		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
        }
    });
    
    /**
     * Triggered when a tab is activated or clicked on it.
     */
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	if ($('#'+e.target.id).parent().attr('id') === 'panel-preparation-tab') {
    		// click on preparation tab.
    		$.fn.blockScreen(true);
        	$.ajax({
        		type: 'GET',
        		url: 'configurator.msf',
        		dataType: 'json',
        		data: {'action': 'builder-app-controller:preview-app'},
        		headers: {'async': 'true'}
        	}).done(function(info) {
        		$.fn.blockScreen(false);
        		if (info.success) {
        			var tableSelected = $('#prepare-app-table-container > tbody');
            		tableSelected.html("");
            		$.each(info.data, function (i, val) {
            			tableSelected.append("<tr><td>" + val.tableName + "</td></tr>");
            		});
        		} else {
        			var description = info.message.text;
            		$.fn.showApplicationError(description, 'application-error-global', false);
        		}
        	}).fail(function(info) {
        		$.fn.blockScreen(false);
        		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
    	}
    });

    /**
     * Triggered when occurs a double click.
     */
    $('#jstree').on("dblclick.jstree", function(event) {
        var currentNode = $(event.target).closest("li");
        var currentNodeId = currentNode.attr("id");
        var parentNode = currentNode.parents('li');
        var parentNodeId = parentNode.attr("id");
        var grandParentNode = parentNode.parents('li');
        var grandParentNodeId = grandParentNode.attr("id");
        if (currentNodeId.indexOf('table') !== -1) {
            // a table has been selected
        } else if (currentNodeId.indexOf('database') !== -1) {
            // a database has been selected
            $("#jstree").jstree("open_node", currentNodeId);
        } else if (currentNodeId.indexOf('connection') !== -1) {
            // now this connection can be editable and can be deleted.
        	$.fn.blockScreen(true);
        	$.ajax({
                type: 'GET',
                url: 'metadata.msf?action=data-source-controller:retrieve-connection&conn-id=' + currentNodeId,
                dataType: 'json'
            }).done(function(info) {
            	$.fn.blockScreen(false);
            	$("#new-edit-connection-form input[name=dbConnName]").val(info.data.dataSourceVo.dbConnName);
                $("#new-edit-connection-form select[name=databaseType]").val(info.data.dataSourceVo.databaseTypeId);
                $("#new-edit-connection-form input[name=host]").val(info.data.dataSourceVo.host);
                $("#new-edit-connection-form input[name=databaseName]").val(info.data.dataSourceVo.databaseName);
                $("#new-edit-connection-form input[name=port]").val(info.data.dataSourceVo.port);
                $("#new-edit-connection-form input[name=user]").val(info.data.dataSourceVo.user);
                $("#new-edit-connection-form input[name=pass]").val(info.data.dataSourceVo.password);
                $("#new-edit-connection-form input[name=serviceName]").prop('checked', info.data.dataSourceVo.serviceNameInUse);
                $("#new-edit-connection-form input[name=is-new-record]").val(false);
                $("#new-edit-connection-form input[name=conn-id]").val(currentNodeId);
                $("#modal-title-label").text('Edit Connection');
                $("#new-edit-conn-button").html('<span class="glyphicon glyphicon-ok"></span> Update');
            }).fail(function(info) {
            	$.fn.blockScreen(false);
            	$.fn.showApplicationError(info.responseText, 'application-error-global', false);
        	});
            // shows the dialog to edit the current record.
            $('#modal-container-new-edit-conn').modal({backdrop: 'static', keyboard: false}).modal('show');
        } else if (currentNodeId.indexOf('root') !== -1) {
            // a root node has been selected
        }
    });

    /**
     * Triggered when selection changes
     */
    $('#jstree').on("changed.jstree", function(e, data) {
    	$('#application-confirm').hide(); //hides the deletion dialog when the selection changes.
    });

    /**
     * Validates the form for the connections. 
     */
    $.fn.createConnValidations = function(form, validations) {
        $.fn.initFormConnections(true);
        var templateTooltip = '<div class="popover"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>';
        $.each(validations, function(fieldName, obj) {
            $("#" + form + " select[name=" + fieldName + "]").closest('.input-group').addClass('has-error');
            $("#" + form + " input[name=" + fieldName + "]").closest('.input-group').addClass('has-error');

            $("#" + fieldName + "Icon").removeClass('glyphicon-pencil').addClass('glyphicon-remove-circle');
            $("#" + fieldName + "Icon").popover({template: templateTooltip, content: obj[0], trigger: 'hover'}).popover('show');
        });
    };
    /**
     * Validates the form for java types. 
     */
    $.fn.createJavaTypeValidations = function(form, validations) {
        $.fn.initFormJavaTypes(true);
        var templateTooltip = '<div class="popover"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>';
        $.each(validations, function(fieldName, obj) {
        	$("#" + form + " input[name='"+fieldName+"']").closest('.input-group').addClass('has-error');
        	$("#" + form + " input[name='"+fieldName+"']").focus();
        	
            $("#" + fieldName + "Icon").removeClass('glyphicon-pencil').addClass('glyphicon-remove-circle');
            $("#" + fieldName + "Icon").popover({template: templateTooltip, content: obj[0], trigger: 'hover'}).popover('show');
        });
    };
    

    /**
     * This function inits the values and fields in the modal window for connections.
     */
    $.fn.initFormConnections = function(initFullContent) {
        // removes all tool tips if any
        $('#new-edit-connection-form input, #new-edit-connection-form select').each(function(index) {
            var input = $(this);
            var fieldName = input.attr('name');
            if (input.attr('type') !== 'hidden') {
                $("#" + fieldName + "Icon").popover('destroy');
                $("#" + fieldName + "Icon").removeClass('glyphicon-remove-circle').addClass('glyphicon-pencil');
            }
        });
        $("div.input-group").each(function() {
            $(this).removeClass('has-error');
        });
        if (initFullContent) {
            $('#connection-status-msg').popover('destroy');
            $('#connection-status-msg').css('color', '#000000');
            $("#new-edit-conn-button").removeClass('btn-danger').addClass('btn-primary');
        }
    };
    /**
     * This function inits the values and fields in the modal window for the java types.
     */
    $.fn.initFormJavaTypes = function(initFullContent) {
        // removes all tool tips if any
        $('#java-database-types-form input').each(function(index) {
            var input = $(this);
            var fieldName = input.attr('name');
            if (input.attr('type') !== 'hidden') {
                $("#" + fieldName + "Icon").popover('destroy');
                $("#" + fieldName + "Icon").removeClass('glyphicon-remove-circle').addClass('glyphicon-pencil');
            }
        });
        $("div.input-group").each(function() {
            $(this).removeClass('has-error');
        });
        if (initFullContent) {
            $("#java-type-save-button").removeClass('btn-danger').addClass('btn-primary');
        }
    };
    /**
     * Show an alert for the applicaction using different types.
     */
    $.fn.showMessageAlert = function(content, type) {
    	var typeAlert = 'alert-'+type;
    	var applicationAlert = '#application-alert'; 
    	if (type === 'success') {
    		$(applicationAlert).removeClass('alert-info').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-warning').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-danger').addClass(typeAlert);
    	} else if (type === 'info') {
    		$(applicationAlert).removeClass('alert-success').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-warning').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-danger').addClass(typeAlert);
    	} else if (type === 'warning') {
    		$(applicationAlert).removeClass('alert-success').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-info').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-danger').addClass(typeAlert);
    	} else if (type === 'danger') {
    		$(applicationAlert).removeClass('alert-success').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-info').addClass(typeAlert);
    		$(applicationAlert).removeClass('alert-warning').addClass(typeAlert);
    	}
    	$('#application-alert-text').html(' '+content);
        $(applicationAlert).show();
    };
    
    /**
     * Shows an icon with the error of the application. 
     */
    $.fn.showApplicationError = function(errorDescription, containerId, showCloseButton) {
    	var imgIcon = "background-image:url('resources/mesofi/img/alert-error.png')";
    	var titleIcon = '<span style='+imgIcon+'>&nbsp;&nbsp;&nbsp;&nbsp;</span>';
    	var titleContent = titleIcon + '&nbsp;&nbsp;&nbsp;<b style="color: red">Application Error</b>'; 
    	var closeButton = '<button type="button" id="close" class="close" onclick="$(&quot;#'+containerId+'&quot;).popover(&quot;hide&quot;);">&times;</button>';
    	if (showCloseButton) {
    		titleContent = titleContent + '&nbsp;&nbsp;' + closeButton;
    	}
    	
    	// finds out the container if any
    	var parentTag = $('#'+containerId).parent();
    	if (parentTag.attr('id') === 'application-error-global-container') {
    		parentTag.css( "display", "block") 
    	}
    	
    	$('#'+containerId).popover('destroy');
        $('#'+containerId).popover({
            title: titleContent,
            content: '<span style="color: rgb(255, 39, 73);">'+errorDescription+'</span>',
            html: true,
            trigger: 'hover',
            delay: { show: 5000, hide: 100 }
        }).popover('show');
    };
    
    /**
     * Activate certain tab in the application by id.
     */
    $.fn.activateTabById = function(idTab) {
    	var navParent = $('#tabs-navigation'); // parent navigator
    	$.each(navParent.children('ul'), function (i, val) {
    		$.each($(this).children(), function (j, tabs) {
    			if ($(this).attr('id') === idTab) {
    				$(this).addClass('active');
    				$('#'+$(this).attr('id') + '-content').addClass('active');
    			} else {
    				$(this).removeClass('active');
    				$('#'+$(this).attr('id') + '-content').removeClass('active');
    			}
    		});
    	});
    };
    $.fn.blockScreen = function(block) {
    	if (block) {
    		$('#body-global-container').addClass('wait-cursor');
    	} else {
    		$('#body-global-container').removeClass('wait-cursor');
    	}
    }
    $.fn.allowToAddConfig = function(allowed, canBeSaved) {
    	var identifier = '#include-table-button';
    	if (allowed) {
    		// shows the button ready to be saved
    		$(identifier).html('<span class="glyphicon glyphicon-thumbs-down"></span> Add');
			$(identifier).prop('disabled', !canBeSaved);
			$(identifier).removeClass('btn-success');
			$(identifier).addClass('btn-danger');
    	} else {
    		$(identifier).html('<span class="glyphicon glyphicon-thumbs-up"></span> Added');
			$(identifier).prop('disabled', true);
			$(identifier).removeClass('btn-danger');
			$(identifier).addClass('btn-success');
    	}
    }
    $.fn.createCheckBoxByType = function(isSelected, name, id) {
    	var selected = '';
    	if (isSelected) {
    		selected = 'checked';
    	}
    	return '<input type="checkbox" name="'+name+'_'+id+'" '+selected+'></input>';
    }
    $.fn.createLabelDbByType = function(dbType, id) {
    	var text = '<span >';
    	text += dbType;
    	text += '</span>';
    	text += '<input type="hidden" name="rowId" value="'+id+'">';
    	text += '</input>';
    	return text;
    }
    $.fn.createInputByType = function(value, name, id) {
    	var text = '<div class="input-group input-group-sm">';
    	text += '<span class="input-group-addon ">';
    	text += '<span class="glyphicon glyphicon-pencil" id="'+name+'_'+id+'Icon" data-placement="left"></span>';
    	text += '</span>';
    	text += '<input class="form-control" placeholder="Valid Java Type" type="input" name="'+name+'_'+id+'" value="'+value+'"></input>';
    	text += '</div>';
    	return text;
    }
    $.fn.createLabelSqlByType = function(sqlType, sqlIdType) {
    	var text = '<span data-toggle="tooltip" data-placement="left" title="Type code: ' + sqlIdType + '">';
    	text += sqlType;
    	text += '</span>';
    	return text;
    }
    $.fn.createButtonsByType =  function() {
    	var text = '<button type="button" class="btn btn-default" data-dismiss="modal">';
    	text += '<span class="glyphicon glyphicon-remove"></span>';
    	text += 'Cancel';
    	text += '</button>';
    	text += '&nbsp;&nbsp;';
    	text += '<button type="button" id="java-type-save-button" class="btn btn-primary">';
    	text += '<span class="glyphicon glyphicon-ok"></span>';
    	text += 'Apply Changes';
    	text += '</button>';
    	return text;
    }
});

$(document).on('keypress', 'input[name^="javaType_"]', function(e) {
	$("#java-type-save-button").attr('disabled', false);
	if (e.which == 13) {
		// invokes the click event on the button
		$("#java-type-save-button").trigger( "click" );
	}
});
$(document).on('click', 'input:checkbox[name^="effectiveType_"]', function(e) {
	$("#java-type-save-button").attr('disabled', false);
});
// for dynamic buttons from Java types.
$(document).on('click', '#java-type-save-button', function(e) { 
	e.preventDefault();
    var oldText = $("#java-type-save-button").html();
    $("#java-type-save-button").attr('disabled', true);
    $("#java-type-save-button").text('Saving ...');
    
    $.fn.blockScreen(true);
    $.ajax({
        type: 'POST',
        url: 'configurator.msf',
        headers: {'async': 'true'},
        data: $('#java-database-types-form').serialize(),
        success: function(response) {
        	$.fn.blockScreen(false);
            $("#java-type-save-button").html(oldText);
            if (response.success) {
            	$.fn.initFormJavaTypes(false);
            } else {
            	if (response.message.errorType === 'BUSINESS') {
            		$.fn.createJavaTypeValidations('java-database-types-form', response.message.fields);
            		$("#java-type-save-button").attr('disabled', false);
                    $("#java-type-save-button").removeClass('btn-danger').addClass('btn-primary');
            	} else if (response.message.errorType === 'DEFAULT') {
            		$.fn.showApplicationError(response.message.text, 'application-error-global', false);
            	} else {
            		$.fn.showApplicationError('Unknown error', 'application-error-global', false);
            	}
            }
        },
        error: function(response) {
        	$.fn.blockScreen(false);
        	$.fn.showApplicationError(response.responseText, 'application-error-global', false);
        }
       
    });
});