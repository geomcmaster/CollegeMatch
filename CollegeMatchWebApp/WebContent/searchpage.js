/**
 * Search page update code
 * 
 * MAIN section hides all criteria value fields
 * 
 * SHOWKIDS function shows the appropriate criteria value fields for the criterion selected
 * 
 * TOGGLEBETWEEN function shows extra number value fields when "between" comparison value is selected
 * 
 * HIDEOPTIONS function hides the option selected on one criterion from all other criteria lists
 * 
 * SHOWOPTIONS function shows an option deselected from one criterion on all other criteria lists
 * 
 * NONUM disables relevant fields when users make certain selections
 * 
 * SHOWTHISOPTION disables option-hiding HTML/CSS to put a drop-down option back in the exact place it was originally.
 * 
 */

var valTds = document.getElementsByClassName("critvals");
for(var i = 0; i < valTds.length; i++) {
	hideAll(valTds[i]);
}

function hideAll(parent) {
	var kids = parent.children;
	for(var i = 0; i < kids.length; i++) {
		kids[i].classList.add("hidden");
		kids[i].disabled = false;
		if (kids[i].nodeName == "INPUT") {
			if (kids[i].type == "text" || kids[i].type == "number") {
				kids[i].value = "";
			} else if (kids[i].type == "checkbox") {
				kids[i].checked = false;
			}
		} else if (kids[i].nodeName == "LABEL") {
			kids[i].innerHTML = "";
		} else if (kids[i].nodeName == "SELECT") {
			kids[i].selectedIndex = 0;
			var spans = kids[i].getElementsByTagName("span");
			for (var j = 0; j < spans.length; j++) {
				showThisOption(spans[j]);
			}
		}
	}
}

function showKids(source) {
	hideOptions(source);
	var tdParent = source.parentNode.nextElementSibling;
	var strOpen = tdParent.id.slice(0,5);
	var selValue = source.options[source.selectedIndex].value;
	var elHid = document.getElementById(strOpen + "hid");
	var checkLab = document.getElementById(strOpen + "checkLab");
	showOptions(source, elHid.value);
	elHid.value = selValue;
	hideAll(tdParent);
	switch(selValue) {
		case "locationz":
			var elBefore = document.getElementById(strOpen + "before");
			elBefore.classList.remove("hidden");
			while (elBefore.firstChild) {
				elBefore.removeChild(elBefore.firstChild);
			}
			elBefore.appendChild(document.createTextNode("within"));
			document.getElementById(strOpen + "dist").classList.remove("hidden");
			var elMid = document.getElementById(strOpen + "mid");
			elMid.classList.remove("hidden");
			while (elMid.firstChild) {
				elMid.removeChild(elMid.firstChild);
			}
			elMid.appendChild(document.createTextNode("of ZIP"));
			document.getElementById(strOpen + "text").classList.remove("hidden");
			document.getElementById(strOpen + "checkBreak").classList.remove("hidden");
			checkLab.classList.remove("hidden");
			checkLab.appendChild(document.createTextNode("Use my location"));
			document.getElementById(strOpen + "check").classList.remove("hidden");
			break;
		case "locationcs":
			document.getElementById(strOpen + "text").classList.remove("hidden");
			document.getElementById(strOpen + "sel1").classList.remove("hidden");
			break;
		case "programs":
			document.getElementById(strOpen + "sel2").classList.remove("hidden");
			break;
		case "region":
			document.getElementById(strOpen + "sel3").classList.remove("hidden");
			break;
		case "name":
			document.getElementById(strOpen + "text").classList.remove("hidden");
			break;
		case "sat":
		case "act":
			document.getElementById(strOpen + "comp").classList.remove("hidden");
			document.getElementById(strOpen + "num1").classList.remove("hidden");
			document.getElementById(strOpen + "check").classList.remove("hidden");
			checkLab.appendChild(document.createTextNode("Use my scores"));
			checkLab.classList.remove("hidden");
			document.getElementById(strOpen + "checkBreak").classList.remove("hidden");
			document.getElementById(strOpen + "num1").step = "1";
			document.getElementById(strOpen + "num2").step = "1";
			break;
		case "cost":
		case "earnings":
		case "size":
		case "avginc":
		case "medinc":
		case "tuitionin":
		case "tuitionout":
		case "age":
		case "meddebt":
			document.getElementById(strOpen + "num1").step = "1";
			document.getElementById(strOpen + "num2").step = "1";
			document.getElementById(strOpen + "comp").classList.remove("hidden");
			document.getElementById(strOpen + "num1").classList.remove("hidden");
			break;
		case "admrate":
		case "firstgen":
		case "men":
		case "women":
		case "white":
		case "black":
		case "hispanic":
		case "asian":
		case "aian":
		case "nhpi":
		case "multi":
		case "nonres":
			document.getElementById(strOpen + "num1").step = "0.01";
			document.getElementById(strOpen + "num2").step = "0.01";
			document.getElementById(strOpen + "comp").classList.remove("hidden");
			document.getElementById(strOpen + "num1").classList.remove("hidden");
			break;
		case "level":
			document.getElementById(strOpen + "level").classList.remove("hidden");
			break;
		case "favorites":
		case "fav5":
		case "online":
			document.getElementById(strOpen + "check").classList.remove("hidden");
			document.getElementById(strOpen + "check").checked = true;
			document.getElementById(strOpen + "checkLab").classList.remove("hidden");
			break;
	}
}

function toggleBetween(source) {
	var strOpen = source.id.slice(0,5);
	var elMid = document.getElementById(strOpen + "mid");
	if (source.options[source.selectedIndex].value == "bet") {
		elMid.classList.remove("hidden");
		while (elMid.firstChild) {
			elMid.removeChild(elMid.firstChild);
		}
		elMid.appendChild(document.createTextNode("and"));
		document.getElementById(strOpen + "num2").classList.remove("hidden");
	} else {
		elMid.classList.add("hidden");
		document.getElementById(strOpen + "num2").classList.add("hidden");
	}
}

function hideOptions(source) {
	var strValueToRemove = source.options[source.selectedIndex].value;
	var strIdToSkip = source.id;
	var els = document.getElementsByClassName("sel_type");
	for (var i = 0; i < els.length; i++) {
		var el = els[i];
		if (el.id != strIdToSkip) {
			if (strValueToRemove != "null") {
				for (var j = 0; j < el.options.length; j++) {
					if (el.options[j].value == strValueToRemove) {
						el.options[j].classList.add("hidden");
						var wrap = document.createElement("span");
						wrap.innerHTML = el.options[j].outerHTML;
						el.options[j].parentNode.insertBefore(wrap,el.options[j]);
						el.options[j].remove();
						break;
					}
				}
			}
		}
	}
}

function showOptions(source, optval) {
	var strIdToSkip = source.id;
	var els = document.getElementsByClassName("sel_type");
	if (optval != "") {
		for (var i = 0; i < els.length; i++) {
			var el = els[i];
			if (el.id != strIdToSkip) {
				var spans = el.getElementsByTagName("span");
				for (var j = 0; j < spans.length; j++) {
					var opt = spans[j].children[0];
					if (opt.value == optval) {
						showThisOption(spans[j]);
						break;
					}
				}
			}
		}
	}
}

function nonum(elCheck) {
	var elGroupOpener = elCheck.id.substring(0,5);
	var elSelect = document.getElementById(elGroupOpener + "type");
	var elCompare = document.getElementById(elGroupOpener + "comp");
	var wrap;
	if (elSelect.options[elSelect.selectedIndex].value == "sat" || 
			elSelect.options[elSelect.selectedIndex].value == "act") {
		if (elCheck.checked) {
			document.getElementById(elGroupOpener + "num1").disabled = true;
			if (elCompare.selectedIndex == 1) {
				elCompare.selectedIndex = 0;
				toggleBetween(elCompare);
			}
			elCompare.options[1].classList.add("hidden");
			wrap = document.createElement("span");
			wrap.innerHTML = elCompare.options[1].outerHTML;
			elCompare.options[1].parentNode.insertBefore(wrap,elCompare.options[1]);
			elCompare.options[1].remove();
		} else {
			wrap = elCompare.getElementsByTagName("span")[0];
			showThisOption(wrap);
			document.getElementById(elGroupOpener + "num1").disabled = false;
		}
	} else if (elSelect.options[elSelect.selectedIndex].value == "locationz") {
		if (elCheck.checked) {
			document.getElementById(elGroupOpener + "text").disabled = true;
		} else {
			document.getElementById(elGroupOpener + "text").disabled = false;
		}
	}
}

function showThisOption(elSpan) {
	var opt = elSpan.getElementsByTagName("option")[0];
	opt.classList.remove("hidden");
	elSpan.outerHTML = opt.outerHTML;
}