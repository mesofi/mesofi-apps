$(document).ready(function() {
	/**
	 * Before the java types screen is loaded 'Available SQL and Java Types'.
	 * We can populate this screen with data from back end.
	 */
	$('#modal-container-java-types').on('show.bs.modal', function(e) {
    	// before showing the dialog to configure java types
    	$.fn.blockScreen(true);
    	$.ajax({
    		type: 'GET',
    		url: 'configurator.msf',
    		dataType: 'json',
    		data: {'action': 'configurator-app-controller:retrieve-databases-and-more'},
    		headers: {'async': 'true'}
    	}).done(function(info) {
    		$.fn.blockScreen(false);
    		if (info.success) {
    			// got a success response from back end.
    			var databaseType = $('#database-type-id-select');
    			
    			databaseType.find('option').remove().end().append('<option value="0">Select existing database</option>');
    			$.each(info.data, function (i, val) {
    				databaseType.append("<option value='"+ val.split('~')[0] +"'>"+val.split('~')[1]+"</option>");
    			});
    			// empty the current table
    			var tableSelected = $('#java-table-container > tbody');
    			tableSelected.html("");
    		} else {
    			$.fn.showApplicationError(info.message.text, 'application-error-global', false);	
    		}
    	}).fail(function(info) {
    		$.fn.blockScreen(false);
    		$.fn.showApplicationError(info.responseText, 'application-error-global', false);
    	});
    });
});