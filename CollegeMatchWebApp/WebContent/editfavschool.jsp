<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>CollegeMatch: Edit Favorite Details</title>
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:import url="menusidebar.html" />
	
	<c:set var="rank" value="${fn:trim(favDetails[0])}" />
	<c:set var="appStatus" value="${fn:trim(favDetails[1])}" />
	<c:set var="finAid" value="${fn:trim(favDetails[2])}" />
	<c:set var="loans" value="${fn:trim(favDetails[3])}" />
	<c:set var="merit" value="${fn:trim(favDetails[4])}" />
	
	<!-- EDIT INDIVIDUAL FAVORITE -->
	<div id="content">
		<div id="title">
			<h2>Edit Favorite: <c:out value="${fullName}" /></h2>
		</div>
		<!-- form for scholarships, application status, etc. -->
		<form action="saveFav" id="editfav" method="POST">
			<input type="hidden" name="schoolId" id="schoolId" value='<c:out value="${id}" />' />
			<table id="editfav">
				<tr>
					<td class="label"><label for="rank">Rank:</label></td>
					<td class="input"><input class="number" type="number" id="rank" name="rank" value='<c:out value="${rank}" />' min="1" /></td>
				</tr><tr>
					<td class="label"><label for="appstatus">Application Status:</label></td>
					<td class="input">
						<select class="inbox" id="appstatus" name="appstatus">
							<option>Not applied</option>
							<option>Applied early admission</option>
							<option>Applied</option>
							<option>Waitlisted</option>
							<option>Rejected</option>
							<option>Accepted</option>
						</select>
					</td>
				</tr><tr>
					<td class="label"><label for="finaid">Financial Aid:</label></td>
					<td class="input">$<input class="number" type="number" step="1" id="finaid" name="finaid" value='<f:formatNumber type="NUMBER" groupingUsed="false" maxFractionDigits="2" value="${finAid}" />' onBlur="calcTotal()" /></td>
				</tr><tr>
					<td class="label"><label for="loans">Loan Amount:</label></td>
					<td class="input">$<input class="number" type="number" step="1" id="loans" name="loans" value='<f:formatNumber type="NUMBER" groupingUsed="false" maxFractionDigits="2" value="${loans}" />' onBlur="calcTotal()" /></td>
				</tr><tr>
					<td class="label"><label for="merit">Merit Scholarships:</label></td>
					<td class="input">$<input class="number" type="number" step="1" id="merit" name="merit" value='<f:formatNumber type="NUMBER" groupingUsed="false" maxFractionDigits="2" value="${merit}" />' onBlur="calcTotal()" /></td>
				</tr><tr id="calculation">
					<td class="label"><span>Total Needed to Attend One Year:</span></td>
					<td class="input"><span id="costCounter"></span><input type="hidden" id="costStart" value='<c:out value="${cost}" />' /></td>
				</tr><tr>
					<td></td>
					<td class="input"><input type="submit" value="Save" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	</div></div> <!-- close wrap -->
	
<script type="text/javascript">
	var cost = '${cost}'; // handles if the cost from the server for the school is null -- we don't want it looking free to attend
	if (cost == 0) {
		document.getElementById("calculation").classList.add("hidden");
	} else {
		document.getElementById("calculation").classList.remove("hidden");
		calcTotal();
	}
	
	// select the correct app status
	var appStatus = '${appStatus}';
	var elSelect = document.getElementById("appstatus");
	var elOptions = elSelect.children;
	for (var i = 0; i < elOptions.length; i++) {
		var el = elOptions[i];
		if (el.text == appStatus) {
			el.selected = true;
			break;
		}
	}
	
	function calcTotal() {
		var strStart = document.getElementById("costStart").value;
		var strFinaid = document.getElementById("finaid").value;
		var strLoans = document.getElementById("loans").value;
		var strMerit = document.getElementById("merit").value;
		var elOp = document.getElementById("costCounter");
		var ndOpText = document.createTextNode((Math.max(strStart - strFinaid - strLoans - strMerit,0)).toLocaleString("en-US", {style: "currency", currency: "USD", minimumFractionDigits: 2}));
		
		while (elOp.firstChild) {
			elOp.removeChild(elOp.firstChild);
		}
		elOp.appendChild(ndOpText);
	}
</script>
	
</body>
</html>