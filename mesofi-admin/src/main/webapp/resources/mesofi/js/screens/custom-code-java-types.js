var codemirrorCodeType = new Object();
$(document).ready(function() {
	var availableJavaTypes = new Object();
	/**
	 * Before the custom code type screen is loaded 'Code Type Association'.
	 * We can populate this screen with data from back end.
	 */
	$('#modal-container-code-types').on('show.bs.modal', function(e) {
		// removes all 'div' elements that starts with code-type-container-generated
		$("div[id^='code-type-container-generated']").remove();
		$("#preview-code-button").attr('disabled', true); // disabled
		
		// before showing the dialog to configure types
    	$.fn.blockScreen(true);
    	$.ajax({
    		type: 'GET',
    		url: 'configurator.msf',
    		dataType: 'json',
    		data: {'action': 'configurator-app-controller:retrieve-sections-and-more'},
    		headers: {'async': 'true'}
    	}).done(function(response) {
    		$.fn.blockScreen(false);
    		if (response.success) {
    			availableJavaTypes = response.data[1];
    			// got a success response from back end.
    			var codeSections = $('#section-type-id-select');
    			
    			codeSections.find('option').remove().end().append('<option value="0">Select section code</option>');
    			$.each(response.data[0], function (i, val) {
    				codeSections.append("<option value='"+ val +"'>"+val+"</option>");
    			});
    		} else {
    			$.fn.showApplicationError(response.message.text, 'application-error-global', false);	
    		}
    	}).fail(function(response) {
    		$.fn.blockScreen(false);
    		$.fn.showApplicationError(response.responseText, 'application-error-global', false);
    	});
	});
	/**
     * When a code section is changed.
     */
	$('#section-type-id-select').on('change', function(e) {
		$("#preview-code-button").attr('disabled', false); // enabled
		var pointcutSelected = this.value; 
		if(pointcutSelected == '0') {
			$("#preview-code-button").attr('disabled', true); // disabled
			return;
		}
		// gets the code related to this identifier.
		$.fn.blockScreen(true);
		$.ajax({
			type: 'GET',
			url: 'configurator.msf',
			dataType: 'json',
			data: {'action': 'configurator-app-controller:retrieve-code-by-section', 'pointcut-name': pointcutSelected, 'only-code': true},
			headers: {'async': 'true'}
		}).done(function(response) {
			$.fn.blockScreen(false);
			if (response.success) {
				// got a success response from back end.
				var dynaForm = $('#config-database-types-form');
				// remove all elements (if any) except the first one.
				while (dynaForm.children().length > 1) {
					dynaForm.children().next().remove();
				}
				dynaForm.append("<br/>");
				
				var code = null;
				var totalLines = 0;
				var maxWidth = 936;
				var vars = {};
				for(var i = 0; i< response.data.totalCode; i++) {
					var genHtml = "";
            		codeId = response.data.customCode[i].id;
            		codeDesc = response.data.customCode[i].description;
    				totalLines = (codeDesc.match(/<br\/>/g) || []).length;
    				
    				codeDesc = codeDesc.replace(/<br\/>/g, "\n");
    				
    				genHtml += "<div id='code-type-container-generated" + codeId + "' class='input-group input-group-sm'>";
    				genHtml += "<textarea id='custom-code-java-mng" + codeId + "'>" + codeDesc + "</textarea>";
    				genHtml += "<br/>";
    				genHtml += "<table width='" + maxWidth + "'><tr><td>"+ $.fn.createAvailableTypesByCode(codeId) +"</td></tr></table>";
    				genHtml += "<hr/>";
    				genHtml += "</div>";
    				dynaForm.append(genHtml);
    				var editorCodeRead = CodeMirror.fromTextArea(document.getElementById("custom-code-java-mng" + codeId), {
        				theme: "night",
        		        lineNumbers: true,
        		        lineWrapping: true,
        		        indentUnit: 4,
        		        readOnly: true,
        		        mode: "text/velocity",
        		      });
    				editorCodeRead.setSize(maxWidth, 100);
    				vars['editorCode' + codeId] = editorCodeRead;
    				codemirrorCodeType[codeId] = vars['editorCode' + codeId];
    				
    				var select = $('#select-available-types' + codeId).selectize({
    					create: false,
    					preload: true,
    					maxItems: null,
    					allowEmptyOption: false,
    					// User defined settings
    					multiSelectId: codeId,
    					multiPointcut: pointcutSelected,
    					initItem: true,
    					load: function (query, callback) {
    						var selectize = this;
    						if (selectize.settings.initItem === false) {
    							// the options are being loaded from server.
    							return;
    						}
    						$.fn.blockScreen(true);
    						$.ajax({
    					        type: 'GET',
    					        url: 'configurator.msf',
    					        headers: {'async': 'true'},
    					        data: { 'action': 'configurator-app-controller:retrieve-types-related-by-code', 'pointcut-name': selectize.settings.multiPointcut, 'id-code': selectize.settings.multiSelectId },
    					        success: function(response) {
    					        	$.fn.blockScreen(false);
    					            if (response.success) {
    					            	callback(response.data);
    					            	$.each(response.data, function( index, value ) {
    					            		var compoundKey = 'rel_type_'+value.id+'_'+value.javaTypeId;
    					            		
    					            		selectize.addOption({value: compoundKey, text: value.javaTypeName});
    					            		selectize.createItem(compoundKey);
    					            		if (value.used) {
    					            			selectize.addItem(compoundKey);	
    					            		}
    					            	});
    					            	selectize.settings.initItem = false;
    					            } else {
    					            	if (response.message.errorType === 'BUSINESS') {
    					            		$.fn.showApplicationError('Unknown error', 'application-error-global', false);
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
    					},
    					onDropdownOpen: function (e) {
    					},
    					onDelete: function (valueToBeDeleted) {
    						var selectize = this;
    						$.fn.availableTypesItemRemove(valueToBeDeleted, selectize, selectize.getItem(valueToBeDeleted).text());
    					},
    					onItemAdd: function (valueToBeSelected, $item) {
    						var selectize = this;
    						if (selectize.settings.initItem === true) {
    							// the options are being loaded from server.
    							return;
    						}
    						var codeId = selectize.settings.multiSelectId;
    						$.fn.blockScreen(true);
    						$.fn.availableTypesItemAdd(codeId, valueToBeSelected, selectize, selectize.getItem(valueToBeSelected).text());
    					}
    					
    				});
    				
    				(select[0].selectize).on('change', $.fn.availableTypesChange );
        		}
        	} else {
        		$.fn.showApplicationError(response.message.text, 'application-error-global', false);
        	}
		}).fail(function(response) {
			$.fn.blockScreen(false);
			$.fn.showApplicationError(response.responseText, 'application-error-global', false);
		});
	});
	/**
	 * When the screen 'Code Type Association' is about to be closed.
	 */
	$('#modal-container-code-types').on('hidden.bs.modal', function(e) {
		codemirrorCodeType = new Object();
	});
	/**
	 * When the available types 'changes'.
	 */
	$.fn.availableTypesChange =  function(e) {
	}
	/**
	 * When we added a new selection
	 */
	$.fn.availableTypesItemAdd = function(codeId, valueToBeSelected, currentSelectize, existingText) {
		var tmp = valueToBeSelected.substring("rel_type_".length);
		tmp = tmp.split("_");
		var typeId = tmp[1];
		$.ajax({
	        type: 'POST',
	        url: 'configurator.msf',
	        headers: {'async': 'true'},
	        data: { 'action': 'configurator-app-controller:create-types-related-by-code', 'id-code': codeId, 'id-type': typeId },
	        success: function(response) {
	        	$.fn.blockScreen(false);
	            if (response.success) {
	            	// relation added
	            	var idCreated = response.data;
	            	var existingValue = 'rel_type_0_' + typeId;
	            	var newValue = 'rel_type_' + idCreated + '_' + typeId;
	            	var data   = {value: newValue, text: existingText};
	            	currentSelectize.updateOption(existingValue, data);
	            } else {
	            	if (response.message.errorType === 'BUSINESS') {
	            		$.fn.showApplicationError('Unknown error', 'application-error-global', false);
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
	}
	/**
	 * When the available types 'item_remove'.
	 */
	$.fn.availableTypesItemRemove = function(valuesToBeDeleted, currentSelectize, existingText) {
		var relationId = new Array();
		var tmp = null;
		$.each(valuesToBeDeleted, function( index, value ) {
			tmp = value.substring("rel_type_".length);
			tmp = tmp.split("_");
			relationId[index] = tmp[0];
		});
		// prepare the relations to be deleted
		var params = {
			"action": "configurator-app-controller:remove-types-related-by-code",
			"relation-id": relationId
		};
		jQuery.ajaxSettings.traditional = true; // !!! take care of this
		var valuesToBeDeletedFinal = new Array();
		$.each(valuesToBeDeleted, function( index, value ) {
			valuesToBeDeletedFinal[index] = valuesToBeDeleted[index];
		});
		$.ajax({
	        type: 'POST',
	        url: 'configurator.msf',
	        headers: {'async': 'true'},
	        data: params,
	        success: function(response) {
	        	$.fn.blockScreen(false);
	            if (response.success) {
	            	// relation removed from database
	            	var tmp2 = null;
	            	$.each(valuesToBeDeletedFinal, function( index, value ) {
	            		tmp2 = value.substring("rel_type_".length);
	            		tmp2 = tmp2.split("_");
	            		var typeId = tmp2[1];
	            		var newValue = 'rel_type_0_' + typeId;
	            		var data   = {value: newValue, text: existingText};
	            		currentSelectize.updateOption(value, data);
	            	});
	            } else {
	            	if (response.message.errorType === 'BUSINESS') {
	            		$.fn.showApplicationError('Unknown error', 'application-error-global', false);
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
	}
	$('#preview-code-button').click(function(e) {
		// before showing the preview code dialog
    	$.fn.blockScreen(true);
    	var pointcutSelected = $("#section-type-id-select option:selected").val();
    	$.ajax({
    		type: 'GET',
    		url: 'configurator.msf',
    		dataType: 'json',
    		data: {'action': 'configurator-app-controller:preview-code-by-pointcut', 'pointcut-selected': pointcutSelected},
    		headers: {'async': 'true'}
    	}).done(function(response) {
    		$.fn.blockScreen(false);
    		if (response.success) {
    			$('#modal-container-preview-code').modal('show');
    		} else {
    			$.fn.showApplicationError(response.message.text, 'application-error-global', false);	
    		}
    	}).fail(function(response) {
    		$.fn.blockScreen(false);
    		$.fn.showApplicationError(response.responseText, 'application-error-global', false);
    	});
	});
	/**
	 * Creates a set of controls for every custom code.
	 */
	$.fn.createAvailableTypesByCode =  function(id) {
		var text = '';
		text += '<div class="control-group">';
		text += '<select id="select-available-types' + id + '" multiple ></select>';
		text += '</div>';
    	return text;
    }
});