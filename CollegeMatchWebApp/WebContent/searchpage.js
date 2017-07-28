var valTds = document.getElementsByClassName("critvals");
for(var i = 0; i < valTds.length; i++) {
	hideAll(valTds[i]);
}
var elsSel = document.getElementsByClassName("sel_type");
for(var i = 0; i < elsSel.length; i++) {
	resetOptions(elsSel[i]);
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
	switch(selValue) {
		case "location":
			var elBefore = document.getElementById(strOpen + "before")
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
			// note that we don't break here, because we want the text field
		case "name":
		case "programs":
			document.getElementById(strOpen + "text").classList.remove("hidden");
			break;
		case "cost":
		case "price":
		case "sat":
		case "act":
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
			document.getElementById(strOpen + "comp").classList.remove("hidden");
			document.getElementById(strOpen + "num1").classList.remove("hidden");
			break;
		case "level":
			document.getElementById(strOpen + "level").classList.remove("hidden");
			break;
		case "online":
			document.getElementById(strOpen + "check").classList.remove("hidden");
			break;
	}
}

function toggleBetween(source) {
	var strOpen = source.id.slice(0,5);
	var elMid = document.getElementById(strOpen + "mid")
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
			//resetOptions(el);
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
				for (j = 0; j < spans.length; j++) {
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

function addOption(el, optval) {
	var txt, targetIndex;
	switch(optval) {
		case "name":
			txt = "Name";
			targetIndex = 1;
			break;
		case "location":
			txt = "Location";
			targetIndex = 2;
			break;
		case "cost":
			txt = "Cost of Attendance";
			targetIndex = 3;
			break;
		case "price":
			txt = "Average Net Price";
			targetIndex = 4;
			break;
		case "sat":
			txt = "Average SAT Scores";
			targetIndex = 5;
			break;
		case "act":
			txt = "Average ACT Scores";
			targetIndex = 6;
			break;
		case "earnings":
			txt = "Average Graduate Earnings";
			targetIndex = 7;
			break;
		case "size":
			txt = "Size of Student Body";
			targetIndex = 8;
			break;
		case "programs":
			txt = "Most Popular Programs";
			targetIndex = 9;
			break;
		case "admrate":
			txt = "Admission Rate";
			targetIndex = 10;
			break;
		case "avginc":
			txt = "Average Family Income";
			targetIndex = 11;
			break;
		case "medinc":
			txt = "Median Family Income";
			targetIndex = 12;
			break;
		case "tuitionin":
			txt = "In-State Tuition";
			targetIndex = 13;
			break;
		case "tuitionout":
			txt = "Out-of-State Tuition";
			targetIndex = 14;
			break;
		case "age":
			txt = "Average Age of Entry";
			targetIndex = 15;
			break;
		case "firstgen":
			txt = "Percentage of First-Generation Students";
			targetIndex = 16;
			break;
		case "level":
			txt = "Level of Institution";
			targetIndex = 17;
			break;
		case "online":
			txt = "Distance-Education Only";
			targetIndex = 18;
			break;
		case "men":
			txt = "Percentage of Male Students";
			targetIndex = 19;
			break;
		case "women":
			txt = "Percentage of Female Students";
			targetIndex = 20;
			break;
		case "white":
			txt = "Percentage of White Students";
			targetIndex = 21;
			break;
		case "black":
			txt = "Percentage of Black Students";
			targetIndex = 22;
			break;
		case "hispanic":
			txt = "Percentage of Hispanic Students";
			targetIndex = 23;
			break;
		case "asian":
			txt = "Percentage of Asian Students";
			targetIndex = 24;
			break;
		case "aian":
			txt = "Percentage of American Indian/American Native Students";
			targetIndex = 25;
			break;
		case "nhpi":
			txt = "Percentage of Native Hawaiian/Pacific Islander Students";
			targetIndex = 26;
			break;
		case "multi":
			txt = "Percentage of Multiracial Students";
			targetIndex = 27;
			break;
		case "nonres":
			txt = "Percentage of Non-Resident Students";
			targetIndex = 28;
			break;
	}
	
	var option = document.createElement("option");
	option.value = optval;
	option.text = txt;
	el.add(option,targetIndex);
}

function resetOptions(elSelect) {
	var currentIndex = elSelect.selectedIndex;
	
	while (elSelect.options.length > 0) {
		elSelect.remove(0);
	}
	
	var option = document.createElement("option");
	option.value = "null";
	option.text = "";
	elSelect.add(option,0);
	
	addOption(elSelect, "name");
	addOption(elSelect, "location");
	addOption(elSelect, "cost");
	addOption(elSelect, "price");
	addOption(elSelect, "sat");
	addOption(elSelect, "act");
	addOption(elSelect, "earnings");
	addOption(elSelect, "size");
	addOption(elSelect, "programs");
	addOption(elSelect, "admrate");
	addOption(elSelect, "avginc");
	addOption(elSelect, "medinc");
	addOption(elSelect, "tuitionin");
	addOption(elSelect, "tuitionout");
	addOption(elSelect, "age");
	addOption(elSelect, "firstgen");
	addOption(elSelect, "level");
	addOption(elSelect, "online");
	addOption(elSelect, "men");
	addOption(elSelect, "women");
	addOption(elSelect, "white");
	addOption(elSelect, "black");
	addOption(elSelect, "hispanic");
	addOption(elSelect, "asian");
	addOption(elSelect, "aian");
	addOption(elSelect, "nhpi");
	addOption(elSelect, "multi");
	addOption(elSelect, "nonres");
	
	elSelect.selectedIndex = currentIndex;
}