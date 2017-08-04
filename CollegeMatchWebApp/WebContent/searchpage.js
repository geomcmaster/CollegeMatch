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
 */

var valTds = document.getElementsByClassName("critvals");
for(var i = 0; i < valTds.length; i++) {
	hideAll(valTds[i]);
}

function hideAll(parent) {
	var kids = parent.children;
	for(var i = 0; i < kids.length; i++) {
		kids[i].classList.add("hidden");
	}
}

function showKids(source) {
	hideOptions(source);
	var tdParent = source.parentNode.nextElementSibling;
	var strOpen = tdParent.id.slice(0,5);
	var selValue = source.options[source.selectedIndex].value;
	var elHid = document.getElementById(strOpen + "hid");
	showOptions(source, elHid.value);
	elHid.value = selValue;
	hideAll(tdParent);
	document.getElementById(strOpen + "checkLab").innerHTML = "";
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
			document.getElementById(strOpen + "checkLab").appendChild(document.createTextNode("Use my scores"));
			document.getElementById(strOpen + "checkLab").classList.remove("hidden");
			document.getElementById(strOpen + "checkBreak").classList.remove("hidden");
			break;
		case "cost":
		case "earnings":
		case "size":
		case "admrate":
		case "avginc":
		case "medinc":
		case "tuitionin":
		case "tuitionout":
		case "age":
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
		case "meddebt":
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
						opt.classList.remove("hidden");
						spans[j].outerHTML = opt.outerHTML;
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
	if (elSelect.options[elSelect.selectedIndex].value == "sat" || 
			elSelect.options[elSelect.selectedIndex].value == "act") {
		if (elCheck.checked) {
			document.getElementById(elGroupOpener + "num1").disabled = true;
			if (elCompare.selectedIndex == 1) {
				elCompare.selectedIndex = 0;
				toggleBetween(elCompare);
			}
			elCompare.options[1].classList.add("hidden");
			var wrap = document.createElement("span");
			wrap.innerHTML = elCompare.options[1].outerHTML;
			elCompare.options[1].parentNode.insertBefore(wrap,elCompare.options[1]);
			elCompare.options[1].remove();
		} else {
			var wrap = elCompare.getElementsByTagName("span")[0];
			var opt = wrap.getElementsByTagName("option")[0];
			opt.classList.remove("hidden");
			wrap.outerHTML = opt.outerHTML;
			document.getElementById(elGroupOpener + "num1").disabled = false;
		}
	}
}