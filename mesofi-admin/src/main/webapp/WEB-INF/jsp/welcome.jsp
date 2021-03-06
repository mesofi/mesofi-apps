<!DOCTYPE html>
<html>
<head>
<title>Layout Example</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript" src="resources/js/jquery.layout-latest.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$('body').layout({ applyDemoStyles: true });
});
</script>
</head>
<body>
<div class="ui-layout-center">Center
	<p><a href="http://layout.jquery-dev.net/demos.html">Go to the Demos page</a></p>
	<p>* Pane-resizing is disabled because ui.draggable.js is not linked</p>
	<p>* Pane-animation is disabled because ui.effects.js is not linked</p>
</div>
<div class="ui-layout-north">North</div>
<div class="ui-layout-west">WestWestWestWestWestWestWestWestWestWestWestWestWestWestWest</div>
</body>
</html>
