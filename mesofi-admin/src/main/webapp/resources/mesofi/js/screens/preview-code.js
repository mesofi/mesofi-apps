$(document).ready(function() {
	/**
	 * Before the preview code screen is loaded 'Preview Code'.
	 */
	$('#modal-container-preview-code').on('show.bs.modal', function(e) {
		var valueSelected = $("#section-type-id-select option:selected").val();
		$('#preview-code-title').text('Preview Code for ['+valueSelected+']');
		var container = $('#preview-all-code-generated');
	});
	/**
	 * When the screen 'Preview Code' is about to be closed.
	 */
	$('#modal-container-preview-code').on('hidden.bs.modal', function(e) {
		$('#preview-all-code-generated').html("");
	});
});