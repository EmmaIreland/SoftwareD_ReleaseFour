$(function() {
	// onLoad
	
	$("#checkAll").click(function() {
		checkAll("[type='checkbox']");
	});
	
	$("#uncheckAll").click(function() {
		uncheckAll("[type='checkbox']");
	});
	
	$(".teamCheckbox").click(function(event) {
		var wasChecked = $(event.target).attr("checked") != "checked";
		var fn = wasChecked ? uncheckAll : checkAll;
		fn($(event.target).parent().next().find("[type='checkbox']"));
	});
});


function checkAll(jqSelector) {
	$(jqSelector).attr("checked", true);
}

function uncheckAll(jqSelector) {
	$(jqSelector).attr("checked", false);
}