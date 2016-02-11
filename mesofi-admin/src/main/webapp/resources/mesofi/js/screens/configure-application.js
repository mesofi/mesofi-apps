$(document).ready(function() {
	var initialTextButton = 'Download My Web App';
	$("button[id^='download-app-button']").text(initialTextButton);
	
	/**
	 * Before the configure application screen is loaded 'Configure My Application'.
	 * We can populate this screen with data from back end.
	 */
	$('#modal-container-configure-application').on('show.bs.modal', function(e) {
    	// before showing the dialog to configure my application
		var hasErrors = false;
		
		// initializes the button to download the app
		$("button[id^='download-app-button']").attr('disabled', false);
		$("button[id^='download-app-button']").html('<span class="glyphicon glyphicon-download-alt"></span>&nbsp;&nbsp;'+initialTextButton);
		$("button[id^='download-app-button']").removeClass('btn-danger').addClass('btn-primary');
		$("#app-status-msg").popover('destroy');
		$("#app-status-msg").css('color', '#000000');
		
    	$.fn.blockScreen(true);
    	$.ajax({
    		type: 'GET',
    		url: 'configurator.msf',
    		dataType: 'json',
    		data: {'action': 'builder-app-controller:pre-process-app'},
    		headers: {'async': 'true'}
    	}).done(function(info) {
    		$.fn.blockScreen(false);
    		if (info.success) {
    			// got a success response from back end.
    			
	    		// ------------------------| database connection section |--------------------------
    			var tableConnSelected = $('#connection-database-table-container > tbody');
    			tableConnSelected.html("");
    			
    			if (info.data.connections.length == 0) {
    				tableConnSelected.append("<tr id='0'><td colspan = '4'><span class='glyphicon glyphicon-remove-circle label-error'>&nbsp;No connections available</span></td></tr>");
    				hasErrors = true;
    			} else {
    				var contentRadio = null;
    				var tables = null;
    				var databases = null;
    				var hiddenText = null;
    				$.each(info.data.connections, function (i, val) {
    					contentRadio = '<input type="radio" id="conn-selected-'+i+'" name="conn-selected" value="'+val.id+'">';
    					databases = '<select id="database-select-'+val.id+'"></select>';
    					tables = '<select id="table-select-'+val.id+'" multiple="multiple"></select>';
    					hiddenText = '<input type="hidden" id="conn-description-'+val.id+'" value="'+val.description+'">';
    					tableConnSelected.append("<tr id='"+i+"'><td>" + contentRadio + "</td><td>" + val.databaseFormalName + "</td><td>" + hiddenText + val.description + "</td><td>" + databases + "</td><td>" + tables + "</td></tr>");
    					
    					$("#database-select-"+val.id).multiselect({
    						buttonWidth: '200px',
    						disableIfEmpty: true,
    						onChange: function(option, checked, select) {
    							$("#conn-selected-"+i).prop("checked", true);
    			                $.fn.populatesTables(val.id, $(option).val());
    			            }
    					});
    					$("#table-select-"+val.id+"").multiselect({
    						buttonWidth: '360px',
    						disableIfEmpty: true,
    						onChange: function(option, checked, select) {
    							$("#conn-selected-"+i).prop("checked", true);
    							var tableId = $(option).val();
    							if (tableId != undefined) {
    								$.fn.updateTableProcessed(tableId, checked);	
    							}
    			            }
    					});
    				});
    				// invokes the click event on the table for the database type
    	    		$(':radio[id="conn-selected-0"]').trigger( "click" );
    			}
    			
    			// ------------------------| plugin section |--------------------------
    			var tablePluginSelected = $('#plugin-container > tbody');
    			tablePluginSelected.html("");
    			
    			if (info.data.plugins.length == 0) {
    				tablePluginSelected.append("<tr id='0'><td colspan = '4'><span class='glyphicon glyphicon-remove-circle label-error'>&nbsp;No plugins available</span></td></tr>");
    				hasErrors = true;
    			} else {
    				var contentRadio = null;
    				var hiddenText = null;
    				var radioIdSelected = null;
    				
    				$.each(info.data.plugins, function (i, val) {
    					if (val.selected) {
    						radioIdSelected = i;
    					} 
    					contentRadio = '<input type="radio" id="plugin-selected-'+i+'" name="plugin-selected" value="'+val.id+'">';
    					tablePluginSelected.append("<tr id='"+i+"'><td>" + contentRadio + "</td><td>" + val.title + "</td><td>" + val.description + "</td></tr>");
    				});
    				// invokes the click event on the table for the plugin
    	    		$(':radio[id="plugin-selected-'+radioIdSelected+'"]').trigger( "click" );
    	    		
    	    		// sets general settings
    	    		$('#authModuleNameId').val(info.data.mainPreferences.authModuleName);
    	    		$('#mainModuleNameId').val(info.data.mainPreferences.moduleName);
    			}
    		} else {
    			$.fn.showApplicationError(info.message.text, 'application-error-global', false);	
    		}
    		$("button[id^='download-app-button']").attr('disabled', hasErrors);
    	}).fail(function(info) {
    		$.fn.blockScreen(false);
    		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
    	});
    });
	
	/**
     * Process my application. This event is fired when we click on the 'download' button.
     */
	$("button[id^='download-app-button']").click(function(e) {
		// process my application.
		var connSelected = $('input[name=conn-selected]:checked').val();
		var pluginSelected = $('input[name=plugin-selected]:checked').val();
		var projectName = $('#webProjectNameId').val();
		
		var oldText = $("button[id^='download-app-button']").html();
		$("button[id^='download-app-button']").attr('disabled', true);
        $("button[id^='download-app-button']").text('Processing ...');
		
		$.fn.blockScreen(true);
    	$.ajax({
    		type: 'POST',
    		url: 'configurator.msf?conn-selected='+connSelected+'&'+'plugin-selected='+pluginSelected,
    		dataType: 'json',
    		data: $('#project-details-form').serialize(),
    		headers: {'async': 'true'}
    	}).done(function(info) {
    		$.fn.blockScreen(false);
    		if (info.success) {
    			$("button[id^='download-app-button']").text('Zipping ...');
    			$.ajax({
    				type: 'GET',
    	    		url: 'configurator.msf?action=builder-app-controller:post-process-app&webProjectName='+projectName,
    	    		dataType: 'json',
    	    		headers: {'async': 'true'}
    			}).done(function(info) {
    				$.fn.blockScreen(false);
    	    		$("button[id^='download-app-button']").html(oldText);
    	    		$("button[id^='download-app-button']").attr('disabled', false);
    	    		
    				$.fn.initFormBuilder();
        			// downloads the app to client.
        			window.location.replace("configurator.msf?action=builder-app-controller:download-app");
    			}).fail(function(info) {
    	    		$.fn.blockScreen(false);
    	    		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
    	    	});
    		} else {
    			if (info.message.errorType === 'BUSINESS') {
    				if (info.message.fields != null) {
    					$.fn.createBuilderValidations('project-details-form', info.message.fields);	
    					$("button[id^='download-app-button']").attr('disabled', false);
    					$("button[id^='download-app-button']").html(oldText);
    				} else {
    					$("button[id^='download-app-button']").attr('disabled', false);
    				    $("button[id^='download-app-button']").html('<span class="glyphicon glyphicon-download-alt"></span>&nbsp;&nbsp;Download Error');
    				    $("button[id^='download-app-button']").removeClass('btn-primary').addClass('btn-danger');
    				    // show the error to download the file
                        var errorApp = info.message.field == null ? 'Opps something went wrong!!!' : info.message.field;
                        var pretitle = '<b style="color: red">An error occurred while generating the web application:</b><br/>';
                        errorApp = pretitle + errorApp;
                        $("#app-status-msg").popover('destroy');
                        $("#app-status-msg").popover({
                            title: 'Web Application',
                            content: errorApp,
                            html: true,
                            trigger: 'hover'
                        }).popover('show');
                        $("#app-status-msg").css('color', '#b94a48');
    				}
    			} else {
    				$("button[id^='download-app-button']").attr('disabled', true);
    				$("button[id^='download-app-button']").html('<span class="glyphicon glyphicon-download-alt"></span>&nbsp;&nbsp;Download Error');
    				$("button[id^='download-app-button']").removeClass('btn-primary').addClass('btn-danger');
    			    var description = info.message.text;
        		    $.fn.showApplicationError(description, 'application-error-global', false);
    			}
    		}
    	}).fail(function(info) {
    		$.fn.blockScreen(false);
    		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
    	});
	});
	/**
     * Validates the form for the application. 
     */
    $.fn.createBuilderValidations = function(form, validations) {
        $.fn.initFormBuilder();
        var templateTooltip = '<div class="popover"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>';
        $.each(validations, function(fieldName, obj) {
            $("#" + form + " select[name=" + fieldName + "]").closest('.input-group').addClass('has-error');
            $("#" + form + " input[name=" + fieldName + "]").closest('.input-group').addClass('has-error');

            $("#" + fieldName + "Icon").removeClass('glyphicon-pencil').addClass('glyphicon-remove-circle');
            $("#" + fieldName + "Icon").popover({template: templateTooltip, content: obj[0], trigger: 'hover'}).popover('show');
        });
    };
    /**
     * This function inits the values and fields in the modal window for builder.
     */
    $.fn.initFormBuilder = function() {
        // removes all tool tips if any
        $('#project-details-form input, #project-details-form select').each(function(index) {
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
    };
	/**
	 * When we want to populate the tables
	 */
	$.fn.populatesTables = function(idConn, database) {
		var radioSelected = idConn;
		
		$.fn.blockScreen(true);
		$.ajax({
			type: 'GET',
			url: 'configurator.msf',
			dataType: 'json',
			data: {'action': 'builder-app-controller:retrieve-tables', 'conn-selected': radioSelected, 'database-schema': database},
			headers: {'async': 'true'}
		}).done(function(info) {
			$.fn.blockScreen(false);
			if (info.success) {
				// got a success response from back end.
				var selectedCombo = $("#table-select-"+radioSelected);
				selectedCombo.find('option').remove().end();
				$.each(info.data, function (i, val) {
					selectedCombo.append("<option value='"+ val.id +"' selected='selected'>"+val.tableName+"</option>");
				});
				selectedCombo.multiselect('rebuild');
			} else {
				$.fn.showApplicationError(info.message.text, 'application-error-global', false);	
			}
		}).fail(function(info) {
			$.fn.blockScreen(false);
			$.fn.showApplicationError(info.responseText, 'application-error-global', false);
		});
	};
	/**
	 * When we want to update the status of the table
	 */
	$.fn.updateTableProcessed = function(tableId, checked) {
		
		$.fn.blockScreen(true);
		$.ajax({
			type: 'POST',
			url: 'configurator.msf',
			dataType: 'json',
			data: {'action': 'builder-app-controller:update-table-processed', 'table-id': tableId, 'checked' :checked},
			headers: {'async': 'true'}
		}).done(function(info) {
			$.fn.blockScreen(false);
			if (info.success) {
				// got a success response from back end.
			} else {
				$.fn.showApplicationError(info.message.text, 'application-error-global', false);	
			}
		}).fail(function(info) {
			$.fn.blockScreen(false);
			$.fn.showApplicationError(info.responseText, 'application-error-global', false);
		});
	};
});
/**
 * When the radio button is changed from the database type column.
 */
$(document).on('change', 'input[id^="conn-selected-"]', function(e) {
	var radioSelectedVal = $('input[id^="conn-selected-"]:checked').val();
	var connNameVal = $('#conn-description-'+radioSelectedVal).val();
	$('#webProjectNameId').val(connNameVal);
	
	$.fn.blockScreen(true);
	$.ajax({
		type: 'GET',
		url: 'configurator.msf',
		dataType: 'json',
		data: {'action': 'builder-app-controller:retrieve-databases', 'conn-selected': radioSelectedVal},
		headers: {'async': 'true'}
	}).done(function(info) {
		$.fn.blockScreen(false);
		if (info.success) {
			// got a success response from back end.
			var disabled = true;
			if (info.data.length == 0) {
				disabled = true;
			} else {
				var selectedCombo = $("#database-select-"+radioSelectedVal);
				selectedCombo.find('option').remove().end();
				$.each(info.data, function (i, val) {
					selectedCombo.append("<option value='"+ val.databaseSchema +"'>"+val.databaseSchema+"</option>");
				});
				selectedCombo.multiselect('rebuild');
				// selects the first option so that we get records for tables
				var firstOption = selectedCombo.find('option:eq(0)');
				firstOption.prop('selected', true);
				$.fn.populatesTables(radioSelectedVal, firstOption.val());
				disabled = false;
			}
			$("button[id^='download-app-button']").attr('disabled', disabled);
		} else {
			$.fn.showApplicationError(info.message.text, 'application-error-global', false);	
		}
	}).fail(function(info) {
		$.fn.blockScreen(false);
		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
	});
});
/**
 * When the radio button is changed from the plugin.
 */
$(document).on('change', 'input[id^="plugin-selected-"]', function(e) {
	var radioSelectedVal = $('input[id^="plugin-selected-"]:checked').val();
	
	$('#webGroupIdId').val('');
	$('#webArtifactIdId').val('');
	$('#webVersionId').val('');
	
	$.fn.blockScreen(true);
	$.ajax({
		type: 'GET',
		url: 'configurator.msf',
		dataType: 'json',
		data: {'action': 'builder-app-controller:retrieve-plugin-info', 'plugin-selected': radioSelectedVal},
		headers: {'async': 'true'}
	}).done(function(info) {
		$.fn.blockScreen(false);
		$('#additionalConfigId').html('');
		if (info.success) {
			// got a success response from back end.
			$('#webGroupIdId').val(info.data.plugin.groupId);
			$('#webArtifactIdId').val(info.data.plugin.artifactId);
			$('#webVersionId').val(info.data.plugin.version);
			$('#webAdditionalConfigId').val(info.data.additionalConfig.totalFields != 0); // flag to indicate existence of dynamic content
			if (info.data.additionalConfig.createCode) {
				// creates custom code based on plugin needs
				var html = "";
				var webFieldTypeVal = "";
				
				html += '<div class="alert alert-info" role="alert" style="padding: 4px"><div class="glyphicon glyphicon-wrench"></div>&nbsp;';
				html += 'More settings';
				html += '</div>';
				
				var gridClass = info.data.additionalConfig.totalFields != 1;
				$.each(info.data.additionalConfig.fields, function( i, arr ) {
					if (gridClass) html += '<div class="row">';
					$.each(arr, function( index, field ) {
						if (gridClass)  html += '<div class="col-xs-'+field.width+'">';
						html += '<div class="input-group input-group-sm">';
						
						if (field.field.type == 'text') {
							//html += '<span class="input-group-addon ">';
							//html += '<span class="glyphicon glyphicon-pencil" id="authModuleNameIcon" data-placement="top"></span>';
							//html += '</span>';
						}
						
						if (field.field.type == 'checkbox') {
							html += '<span id="'+field.field.name+'Icon" data-placement="top"></span>';
							html += '<div class="checkbox"><label>';
						}
						
						html += field.html;
						webFieldTypeVal += '|'+field.field.type +'~'+ field.field.name; 
						
						if (field.field.type == 'checkbox') {
							html += '</label></div>';
						}
						
						html += '</div>';
						if (gridClass) html += '</div>';
					});
					if (gridClass) html += '</div>';
				    html += '<br/>';
				});
				html += '<input type="hidden" name="webFieldType" value="'+webFieldTypeVal+'">'; // holds the relation between field and type
				html += '<br/>';
				$('#additionalConfigId').html(html);
			}
		} else {
			$.fn.showApplicationError(info.message.text, 'application-error-global', false);
		}
	}).fail(function(info) {
		$.fn.blockScreen(false);
		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
	});
});
/**
 * When a row is clicked from the database table. 
 */
$(document).on('click', '#connection-database-table-container tr', function(e) {
	var radioSelected = $("#conn-selected-"+this.id);
	if (!radioSelected.is(':checked')) {
		radioSelected.prop("checked", true).trigger("change"); 
	}
});
/**
 * When a row is clicked from the plugin table. 
 */
$(document).on('click', '#plugin-container tr', function(e) {
	var radioSelected = $("#plugin-selected-"+this.id);
	if (!radioSelected.is(':checked')) {
		radioSelected.prop("checked", true).trigger("change"); 
	}
});