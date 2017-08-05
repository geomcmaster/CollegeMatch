/**
 * 
 */

 function saveFields(fields) {
	var favTD = document.getElementById("fieldlist");
	for (var i = 0; i < fields.length; i++) {
		// copy whole field
		var field = fields[i];
		
		// parse rank and name
		var fieldRank = field.slice(0,field.indexOf("|"));
		var fieldName = field.slice(field.indexOf("|")+1);
		
		newField(favTD, fields.length, fieldRank, fieldName);
	}
}

function newField(favTD, totalCount, fieldRank, fieldName) {
	// create owning DIV
	var newfav = document.createElement("div");
	newfav.classList.add("fav_item");
	
	// create ranking dropdown and select rank
	var favRanker = document.createElement("select");
	favRanker.setAttribute("onchange","reorderFavs(this)");
	for (var j = 1; j <= totalCount; j++) {
		var favOption = document.createElement("option");
		if (j == fieldRank) {
			favOption.selected = true;
		}
		favOption.text = j;
		favOption.value = j;
		favRanker.appendChild(favOption);
	}
	newfav.appendChild(favRanker);
	
	// create name
	var favName = document.createTextNode(fieldName + " ");
	newfav.appendChild(favName);
	
	// create delete link (X)
	var favDelete = document.createElement("a");
	favDelete.href = "JavaScript:void(0);";
	favDelete.setAttribute("onclick","deleteField(this)");
	var deleteImg = document.createElement("img");
	deleteImg.src = "images/delete-128.png";
	deleteImg.width = "14";
	favDelete.appendChild(deleteImg);
	newfav.appendChild(favDelete);
	
	// put DIV in governing TD
	favTD.appendChild(newfav);
	
	// remove from controlling select
	var fieldId = removeFromSelect(favName.data.trim());
	
	// save to hidden field
	var hidInput = document.getElementById("hidField");
	if (hidInput.value.length > 0) {
		hidInput.value = hidInput.value + "," + fieldRank + "|" + fieldId;
	} else {
		hidInput.value = fieldRank + "|" + fieldId;
	}
	
	return newfav;
}

function deleteField(el) {
	var elParent = el.parentNode;
	var favTD = document.getElementById("fieldlist");
	var delRank = reorderAbove(elParent);
	favTD.removeChild(elParent);
	
	// show in select
	var fieldName = getNameFromDiv(elParent).trim();
	var fieldId = showInSelect(fieldName);
	
	// remove from hidden field
	var hidInput = document.getElementById("hidField");
	var hidSplit = hidInput.value.split(",");
	var deleteList = document.getElementById("hidDelete");
	var toDelete = -1;
	for (var i = 0; i < hidSplit.length; i++) {
		var rankSplit = hidSplit[i].split("|");
		if (parseInt(rankSplit[0]) > parseInt(delRank)) {
			rankSplit[0]--;
		} else if (parseInt(rankSplit[0]) == parseInt(delRank)) {
			toDelete = i;
		}
		hidSplit[i] = rankSplit.join("|");
	}
	if (deleteList.value.length > 0) {
		deleteList.value = deleteList.value + "," + fieldId;
	} else {
		deleteList.value = fieldId;
	}
	if (toDelete != -1) {
		hidSplit.splice(toDelete, 1);
	}
	hidInput.value = hidSplit.join(",");
}

function addField() {
	var favSel = document.getElementById("fav_field_add");
	var newOpt = favSel.options[favSel.selectedIndex].text;
	var newOptVal = favSel.options[favSel.selectedIndex].value;
	favSel.selectedIndex = 0;
	var favTD = document.getElementById("fieldlist");
	var totalCount = favTD.children.length + 1;
	
	var newDiv = newField(favTD, totalCount, totalCount, newOpt);
	
	var deleteList = document.getElementById("hidDelete");
	var deleteSplit = deleteList.value.split(",");
	var toDelete = -1;
	for (var i = 0; i < deleteSplit.length; i++) {
		if (deleteSplit[0] == newOptVal) {
			toDelete = i;
		}
	}
	if (toDelete != -1) {
		deleteSplit.splice(toDelete, 1);
	}
	deleteSplit.value = deleteSplit.join(",");
	
	reorderBelow(newDiv, totalCount);
}

function reorderFavs(elChanger) {
	var newRank = elChanger.value;
	var reorderedName = elChanger.nextSibling.textContent.trim();
	var reorderedKey = findKey(reorderedName);
	var oldRank, i, rankSplit, sel, divAfter;
	
	// change hidden field first to get old rank
	var hidInput = document.getElementById("hidField");
	var hidSplit = hidInput.value.split(",");
	for (i = 0; i < hidSplit.length; i++) {
		rankSplit = hidSplit[i].split("|");
		if (rankSplit[1] == reorderedKey) {
			oldRank = rankSplit[0];
			break;
		}
	}
	for (i = 0; i < hidSplit.length; i++) {
		rankSplit = hidSplit[i].split("|");
		if (rankSplit[1] != reorderedKey) {
			if (parseInt(rankSplit[0]) >= parseInt(oldRank)) {
				rankSplit[0]--;
			}
			if (parseInt(rankSplit[0]) >= parseInt(newRank)) {
				rankSplit[0]++;
			}
		} else {
			rankSplit[0] = newRank;
		}
		hidSplit[i] = rankSplit.join("|");
	}
	hidInput.value = hidSplit.join(",");
	
	// correct the siblings
	var elDiv = elChanger.parentNode;
	var sib = elDiv.nextSibling;
	while (sib != null) {
		sel = sib.getElementsByTagName("select")[0];
		if (sel != elChanger) {
			if (parseInt(sel.options[sel.selectedIndex].value) >= parseInt(oldRank)) {
				sel.selectedIndex--;
			}
			if (parseInt(sel.options[sel.selectedIndex].value) >= parseInt(newRank)) {
				sel.selectedIndex++;
			}
		}
		sib = sib.nextSibling;
	}
	var sib = elDiv.previousSibling;
	while (sib != null) {
		sel = sib.getElementsByTagName("select")[0];
		if (sel != elChanger) {
			if (parseInt(sel.options[sel.selectedIndex].value) >= parseInt(oldRank)) {
				sel.selectedIndex--;
			}
			if (parseInt(sel.options[sel.selectedIndex].value) >= parseInt(newRank)) {
				sel.selectedIndex++;
			}
		}
		sib = sib.previousSibling;
	}
	
	// move the div
	var favTD = elDiv.parentNode;
	favTD.removeChild(elDiv);
	var allDivs = favTD.children;
	for (i = 0; i < allDivs.length; i++) {
		sel = allDivs[i].getElementsByTagName("select")[0];
		if (sel.selectedIndex == newRank) {
			divAfter = allDivs[i];
			break;
		}
	}
	favTD.insertBefore(elDiv,divAfter);
}

function reorderAbove(elDeletedDiv) {
	var deletedSel = elDeletedDiv.getElementsByTagName("select")[0];
	var deletedRank = deletedSel.options[deletedSel.selectedIndex].value;
	var sib = elDeletedDiv.nextSibling;
	while (sib != null) {
		var sel = sib.getElementsByTagName("select")[0];
		sel.selectedIndex--;
		var opts = sel.children;
		var removeHighest = opts[opts.length-1];
		sel.removeChild(removeHighest);
		sib = sib.nextSibling;
	}
	sib = elDeletedDiv.previousSibling;
	while (sib != null) {
		var sel = sib.getElementsByTagName("select")[0];
		var opts = sel.children;
		var removeHighest = opts[opts.length-1];
		sel.removeChild(removeHighest);
		sib = sib.previousSibling;
	}
	
	return deletedRank;
}

function reorderBelow(elAddedDiv, totalCount) {
	var sib = elAddedDiv.previousSibling;
	while (sib != null) {
		var sel = sib.getElementsByTagName("select")[0];
		var newOpt = document.createElement("option");
		newOpt.value = totalCount;
		newOpt.text = totalCount;
		sel.appendChild(newOpt);
		sib = sib.previousSibling;
	}
}

function removeFromSelect(fieldName) {
	var favSel = document.getElementById("fav_field_add");
	var opts = favSel.options;
	for (var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		if (opt.text == fieldName) {
			var id = opt.value;
			opt.classList.add("hidden");
			var wrap = document.createElement("span");
			wrap.innerHTML = opt.outerHTML;
			opt.parentNode.insertBefore(wrap,opt);
			opt.remove();
			break;
		}
	}
	return id;
}

function showInSelect(fieldName) {
	var favSel = document.getElementById("fav_field_add");
	var spans = favSel.getElementsByTagName("span");
	for (var i = 0; i < spans.length; i++) {
		var opt = spans[i].children[0];
		if (opt.text == fieldName) {
			opt.classList.remove("hidden");
			spans[i].outerHTML = opt.outerHTML;
			var id = opt.value;
			break;
		}
	}
	return id;
}

function getNameFromDiv(elDiv) {
	var nodes = elDiv.childNodes;
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		if (node.nodeType == 3) {
			return node.textContent.trim();
		}
	}
}

function findKey(fieldName) {
	var elSel = document.getElementById("fav_field_add");
	var opts = elSel.getElementsByTagName("option");
	for (var i = 0; i < opts.length; i++) {
		if (opts[i].text == fieldName) {
			return opts[i].value;
		}
	}
}


function comparePWs() {
	var elFirstPw = document.getElementById("new_pw");
	var elSecondPw = document.getElementById("pwcheck");
	if (elFirstPw.value.length > 0 && elSecondPw.value.length > 0) {
		if (elFirstPw.value != elSecondPw.value) {
			document.getElementById("nomatch").classList.remove("hidden");
			document.getElementById("pw_submit").disabled = true;
			return false;
		} else {
			document.getElementById("nomatch").classList.add("hidden");
			document.getElementById("pw_submit").disabled = false;
			return true;
		}
	} else if (elFirstPw.value.length == 0 && elSecondPw.value.length == 0) {
		document.getElementById("nomatch").classList.add("hidden");
		document.getElementById("pw_submit").disabled = false;
		return true;
	}
}

function checkForm() {
	var elFirstPw = document.getElementById("new_pw");
	var elSecondPw = document.getElementById("pwcheck");
	var elOldPw = document.getElementById("old_pw");
	
	// if at least one of the password fields is null and at least one is not null, fire error
	if (elOldPw.value.length == 0 || 
		elFirstPw.value.length == 0 || 
		elSecondPw.value.length == 0) {
			alert("You must fill out all three password fields.");
			return false;
	} else {
		return true;
	}
}