var codemirrorCustomCode = new Object();
$(document).ready(function() {
	var selectizeCustomCode = null;
	/**
	 * Before the custom code screen is loaded 'Available Custom Code'.
	 * We can populate this screen with data from back end.
	 */
	$('#modal-container-custom-code').on('show.bs.modal', function(e) {
		// removes all 'div' elements that starts with code-container-generated
		$("div[id^='code-container-generated']").remove();
		$("#custom-code-add-button").attr('disabled', true); // disabled
		
		var select = $('#select-pointcut').selectize({
			create: true,
			preload: true,
			allowEmptyOption: true,
			sortField: {
				field: 'text',
				direction: 'asc'
			},
			// User defined settings
			initItem: true,
			load: function (query, callback) {
				// based on the user query, gets all point cuts from server.
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
			        data: { 'action': 'configurator-app-controller:retrieve-pointcuts-and-more', 'pointcut-name': query },
			        success: function(response) {
			        	$.fn.blockScreen(false);
			            if (response.success) {
			            	callback(response.data);
			            	$.each(response.data, function( index, value ) {
			            		selectize.addOption({value: value, text: value});
			    				selectize.createItem(value);
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
			onOptionAdd: function (value, data) {
				// saves this new option in database.
				var selectize = this;
				if (selectize.settings.initItem === true) {
					// the options are being loaded from server.
					return;
				}
				$.fn.blockScreen(true);
				$.ajax({
			        type: 'POST',
			        url: 'configurator.msf',
			        headers: {'async': 'true'},
			        data: { 'action': 'configurator-app-controller:save-custom-code-changes', 'pointcut-name': value },
			        success: function(response) {
			        	$.fn.blockScreen(false);
			            if (response.success) {
			            	// new option have been saved in server side.
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
		});
		// fetch the instance
		selectizeCustomCode = select[0].selectize;
		/**
		 * When there's a change in the section code or point cut name
		 */
		selectizeCustomCode.on('change', function() {
			$("#custom-code-add-button").attr('disabled', true); // disabled
			var pointcutName = this.getValue();
			if (pointcutName.length != 0 && pointcutName != '0') {
				$("#custom-code-add-button").attr('disabled', false); // enabled
				// fetch from database all custom code.
				$.ajax({
			        type: 'GET',
			        url: 'configurator.msf',
			        headers: {'async': 'true'},
			        data: { 'action': 'configurator-app-controller:retrieve-code-by-section', 'pointcut-name': pointcutName, 'only-code' : true },
			        success: function(response) {
			        	$.fn.blockScreen(false);
			            if (response.success) {
			            	var dynaForm = $('#custom-code-form');
			            	// remove all elements (if any) except the first one.
		        			while (dynaForm.children().length > 1) {
		        				dynaForm.children().next().remove();
		        			}
		        			dynaForm.append("<br/>");
			            	
			            	var codeId = null;
			            	var codeDesc = null;
		        			var totalLines = 0;
		        			var maxWidth = 936;
		        			var vars = {};
			            	for(var i = 0; i< response.data.totalCode; i++) {
			            		var genHtml = "";
			            		codeId = response.data.customCode[i].id;
			            		codeDesc = response.data.customCode[i].description;
		        				totalLines = (codeDesc.match(/<br\/>/g) || []).length;
		        				
		        				codeDesc = codeDesc.replace(/<br\/>/g, "\n");
		        				
		        				genHtml += "<div id='code-container-generated" + codeId + "' class='input-group input-group-sm'>";
		        				genHtml += "<textarea id='custom-code-mng" + codeId + "'>" + codeDesc + "</textarea>";
		        				genHtml += "<br/>";
		        				genHtml += "<table width='" + maxWidth + "'><tr><td align='right'>"+ $.fn.createButtonsByCode(codeId) +"</td></tr></table>";
		        				genHtml += "<hr/>";
		        				genHtml += "</div>";
		        				dynaForm.append(genHtml);
		        				var editorCodeEdit = CodeMirror.fromTextArea(document.getElementById("custom-code-mng" + codeId), {
		            				theme: "night",
		            		        lineNumbers: true,
		            		        lineWrapping: true,
		            		        indentUnit: 4,
		            		        mode: "text/velocity",
		            		        // User defined options
		            		        editorId: codeId
		            		      });
		        				editorCodeEdit.setSize(maxWidth, 100);
		        				editorCodeEdit.on("change", $.fn.codeEditorChange); // on change
		        				vars['editorCode' + codeId] = editorCodeEdit;
		        				codemirrorCustomCode[codeId] = vars['editorCode' + codeId];
			            	}
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
		});
	});
	/**
	 * When the screen 'Available Custom Code' is about to be closed.
	 */
	$('#modal-container-custom-code').on('hidden.bs.modal', function(e) {
		selectizeCustomCode.destroy();
		codemirrorCustomCode = new Object();
	});
	$('#custom-code-add-button').click(function(e) {
		var pointcutName = selectizeCustomCode.getValue();
		if (pointcutName.length != 0 && pointcutName != '0') {
			$.ajax({
		        type: 'POST',
		        url: 'configurator.msf',
		        headers: {'async': 'true'},
		        data: { 'action': 'configurator-app-controller:save-custom-code-changes', 'pointcut-name': pointcutName },
		        success: function(response) {
		        	$.fn.blockScreen(false);
		            if (response.success) {
		            	// new option have been saved in server side.
		            	selectizeCustomCode.trigger( "change" ); // invokes the change event
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
	});
	/**
	 * Creates a set of controls for every custom code.
	 */
	$.fn.createButtonsByCode =  function(id) {
		var text = '';
		text += '<button type="button" class="btn btn-danger disabled">';
    	text += '<span class="glyphicon glyphicon-remove"></span>';
    	text += 'Delete';
    	text += '</button>';
    	text += '&nbsp;&nbsp;';
    	text += '<button type="button" id="custom-code-save-button-'+id+'" class="btn btn-primary">';
    	text += '<span class="glyphicon glyphicon-ok"></span>';
    	text += 'Apply Changes';
    	text += '</button>';
    	return text;
    }
	/**
	 * When the content of the editor changes.
	 */
	$.fn.codeEditorChange =  function(e) {
		var codeChanged = e.options.editorId;
		// enables the save button.
		$("#custom-code-save-button-"+codeChanged).attr('disabled', false);
	}
});

//for dynamic buttons for custom code.
$(document).on('click', 'button[id^="custom-code-save-button-"]', function(e) { 
	e.preventDefault();
	var elementSelected = e.target.id;
	var idCode = elementSelected.substring("custom-code-save-button-".length);
	var codeContent = codemirrorCustomCode[idCode].getValue();
	
	var oldText = $("#custom-code-save-button-"+idCode).html();
	$("#custom-code-save-button-"+idCode).attr('disabled', true); // disabled
	$("#custom-code-save-button-"+idCode).text('Saving ...');
	
	$.fn.blockScreen(true);
	$.ajax({
        type: 'POST',
        url: 'configurator.msf',
        headers: {'async': 'true'},
        data: { 'action': 'configurator-app-controller:update-custom-code-changes', 'id-code': idCode, 'code': codeContent },
        success: function(response) {
        	$.fn.blockScreen(false);
        	$("#custom-code-save-button-"+idCode).html(oldText);
            if (response.success) {
            	// The code has been saved in server side.
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
});