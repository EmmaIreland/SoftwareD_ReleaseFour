$(function() {
	// onLoad
	updateQuestionType($("#typeDropdown"));
	$(document).on("keyup", ".addOnChange", function(x) {
		var ele = $(x.target);
		if (ele.val().length > 0 && ele.next().length == 0) {
			ele.after("<br /><input type='text' name='" + ele.attr('name') + "' class='addOnChange' />");
		}
	});
});

function showQuestionFields() {
	$("#newQuestionFields").show();
}

function addQuestion() {
	if (validateNewQuestionForm()) {
		var ajaxData = $("#newQuestionForm").serialize();
		jQuery.post("../addQuestion/", ajaxData, function(response) {
			$("#newQuestionForm").parent().append("<span id='" + response.id + "'><br> </span>");
			$("#" + response.id).append($(".deleteIcon").first().clone().show());
			$("#" + response.id).append('<a href="../../question/show/' + response.id + '"> ' + response.prompt + '</a>');
		});
		$("#newQuestionFields").hide();
		clearNewQuestionFields();
	}
}

function clearNewQuestionFields() {
	$("#newQuestionFields").find("[type='text']").val("");
	$("#newQuestionFields").find("[type='text']").not("[id*='Prompt'], [id*='choice'], [id*='Bound']").remove();
	$("#newQuestionFields").find("br").remove();
	$(".errors").hide();
}

function updateQuestionType(dropdown) {
	var type = $(dropdown).val();
	$(".typeRow").hide();
	switch(type) {
	case "existing":
		$("#existingRow").show();
		break;
	case "checkbox":
		$(".checkboxRow").show();
		break;
	case "multipleChoice":
		$(".multipleRow").show();
		break;
	case "shortResponse":
		$(".shortRow").show()
		break;
	case "longResponse":
		$(".longRow").show()
		break;
	case "scale":
		$(".scaleRow").show()
		break;
	}
}

function validateNewQuestionForm() {
	var visiblePromptValue = $("[name$='Prompt']").filter(":visible").val();
	if (visiblePromptValue != null && visiblePromptValue.length < 1) {
		showError("Please enter a prompt.");
		return false;
	}
	
	if ($('#typeDropdown').val() == 'scale' && !boundsAreIntegers()) {
		showError("Please enter valid bounds.");
		return false;
	}

	var choicesLengths = getVisibleChoicesLengths();
	if (allEmpty(choicesLengths) && choicesLengths.length > 0) {
		showError("Please enter at least one valid choice.");
		return false;
	}
	
	return true;
}

function getVisibleChoicesLengths() {
	var visibleChoices = $("[name$='Choices']").filter(":visible");
	// .get() returns a "real" JS array that we can use native functions on (e.g. every())
	return visibleChoices.map(getLength).get();
}

function allEmpty(choicesLengths) {
	return choicesLengths.every(isLessThanOne);
}

// getLength() is for use with jQuery - it gets the value of this from jQuery
function getLength() { return $(this).val().length; }
function isLessThanOne(len) { return len < 1; }

function showError(error) {
	$(".errors").html(error).show();
	$(".errors").parent().parent().show();
}

function boundsAreIntegers() {
	return stringIsInteger($('#scLowerBound').val()) && 
		stringIsInteger($('#scUpperBound').val());
}

function stringIsInteger(str) {
	return !isNaN(parseInt(str));
}


function deleteQuestion(image) {
	if (confirm("Are you sure you want to remove this question from the survey?")) {
		questionId = $(image).parent().attr("id");
		jQuery.post("../../question/delete/" , {id: questionId});
		$(image).parent().remove();
	}
}