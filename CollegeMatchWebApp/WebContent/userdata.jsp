<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
	<title>CollegeMatch: Edit My Data</title>
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:import url="menusidebar.html" />
	
	<!-- SAVE USER DATA -->
	<div id="content">
		<div id="title">
			<h2>Edit My Data</h2>
		</div>

		<!-- Warnings -->
		<div id="pwwarning" class="warning hidden">The password you entered as your old password was incorrect. No changes have been made to your user record.</div>
		<div id="successnotice" class="notice hidden">Your data has been saved successfully!</div>

		<form id="editdata" action="savedata" method="POST">
		<table class="userdata">
			<tr class="newsection">
				<td class="label">Username:</td>
				<td class="input"><c:out value="${user}" /></td>
			</tr>
			<!-- SAT / ACT Scores -->
			<tr class="newsection">
				<td class="label"><label for="edit_sat">Composite SAT Score:</label></td>
				<td class="input"><input class="number" type="number" name="edit_sat" id="edit_sat" min="400" max="1600" step="1" value='<f:formatNumber groupingUsed="false" maxFractionDigits="0" value="${sat}" />' />
			</tr><tr>
				<td class="label"><label for="edit_act">Composite ACT Score:</label></td>
				<td class="input"><input class="number" type="number" name="edit_act" id="edit_act" min="1" max="36" step="1" value='<f:formatNumber groupingUsed="false" maxFractionDigits="0" value="${act}" />' /> <!--[my current ACT score]-->
			</tr>
			<!-- Location -->
			<tr class="newsection">
				<td class="label"><label for="edit_loc_city">City:</label></td>
				<td class="input"><input class="inbox" type="text" id="edit_loc_city" name="edit_loc_city" value='<c:out value="${loccity}" />' /></td>
			</tr><tr>
				<td class="label"><label for="edit_loc_state">State:</label></td>
				<td class="input">
					<select name="edit_loc_state" id="edit_loc_state" class="inbox">
						<c:import url="stateselect.html" />
					</select>
				</td>
			</tr><tr>
				<td class="label"><label for="edit_loc_zip">ZIP Code:</label></td>
				<td class="input"><input class="inbox" style="text-align:right;" type="text" id="edit_loc_zip" name="edit_loc_zip" value='<f:formatNumber type="NUMBER" groupingUsed="false" minIntegerDigits="5" value="${loczip}"/>' /></td>
			</tr>
			<!-- Fields of Study -->
			<tr class="newsection">
				<td class="label"><label for="fav_field_add">Favorite Fields of Study:</label></td>
				<td class="input">
					<select class="inbox" id="fav_field_add" name="fav_field_add" onchange="addField()">
						<option value=""></option>
						<c:import url="fieldselect.html" />
					</select>
					<input type="hidden" name="hidField" id="hidField" />
					<input type="hidden" name="hidDelete" id="hidDelete" />
				</td>
			</tr><tr>
				<td></td>
				<td class="input" id="fieldlist"></td> <!-- list favorites here -->
			</tr><tr>
				<td></td>
				<td class="input"><input type="submit" id="edit_submit" value="Save Data" /></td>
			</tr>
		</table>
		</form>
		<form id="editpw" action="savepw" method="POST">
		<table class="userdata">
			<!-- Password -->
			<tr class="newsection">
				<td colspan="2" style="border-top: 1px darkgray solid;">To change your password, fill in all three of the following fields; otherwise, leave them all blank.</td>
			</tr><tr>
				<td class="label"><label for="old_pw">Old Password:</label></td>
				<td class="input"><input class="inbox" type="password" id="old_pw" name="old_pw" />
			</tr><tr>
				<td class="label"><label for="new_pw">New Password:</label></td>
				<td class="input"><input class="inbox" type="password" id="new_pw" name="new_pw" onblur="comparePWs()" />
			</tr><tr>
				<td class="label"><label for="pwcheck">Re-enter New Password:</label></td>
				<td class="input"><input class="inbox" type="password" id="pwcheck" name="pwcheck" onblur="comparePWs()" />
			</tr><tr>
				<td class="label"></td>
				<td class="input"><input type="submit" id="pw_submit" value="Save Password" onclick="return checkForm()" /></td>
			</tr>
		</table>
		<div style="float:left;" id="nomatch" class="hidden alert">!! The passwords you have entered do not match.</div>
		
		</form>
	</div>
	
	</div></div> <!-- close wrap -->

<script type="text/javascript" src="userdata.js"></script>
<script type="text/javascript">
	var state = '${locstate}';
	var elSelect = document.getElementById("edit_loc_state");
	var elSelectOptGroups = elSelect.children;
	for (var i = 0; i < elSelectOptGroups.length; i++) {
		var elOptions = elSelectOptGroups[i].children;
		for (var j = 0; j < elOptions.length; j++) {
			var el = elOptions[j];
			if (el.value == state) {
				el.selected = true;
				break;
			}
		}
	}
	
	var fields = [];
	<c:forEach items="${fields}" var="field">
		fields.push("${field}");
	</c:forEach>
	saveFields(fields);
	
	// JavaScript to show warnings when applicable
	var URL = window.location.href;
	var param = URL.slice(URL.indexOf("?") + 1);
	if (param.includes("pwfail")) {
		document.getElementById("pwwarning").classList.remove("hidden");
	} else if (param.includes("success")) {
		document.getElementById("successnotice").classList.remove("hidden");
	}
</script>
	
</body>
</html>