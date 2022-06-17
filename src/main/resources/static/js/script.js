var selectedCurrency = null;
var gifURL = null;

function val() {
	var select = document.getElementById("dropDownList");
	var value = select.options[select.selectedIndex].value;
	selectedCurrency = value;
}

function test() {
	var select = document.getElementById("dropDownList");
	var value = select.options[select.selectedIndex].value;
	var submitButton = document.getElementById("submitButton");
}

function submitButton() {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", "/get_gif?currency=" + selectedCurrency);
	xhr.send();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			gifURL = xhr.responseText;
			document.getElementById("image").src=xhr.responseText;
		}
	};
}

window.onload = test;


