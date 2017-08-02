<!-- List o' tasks -->
<!--
TASK				QUERY			SCOPE	
register user		insert			1 table
save user data		insert/update	1 table
delete user data	delete			1 table
save favorites		insert/update	3+ tables (depends on user, field, school)
search	(school)	select
 -- location						2 tables
 -- demographics					2-3 tables
 -- field of study					2 tables
 -- based on user location			3 tables, w/ subqueries
search (field)		select			1 table
display search results
display user data
display favorites
-->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="getUserCookie.jsp" />
<c:import url="validUser.jsp" />

<c:choose>
<c:when test="${validCheck}">

<c:import url="getSchool.jsp">
	<c:param name="schoolId" value="${param.schoolId}" />
</c:import>
<%-- c:import url="getFavorite.jsp">
	<c:param name="user" value="${user}" />
	<c:param name="schoolId" value="${param.schoolId}" />
</ c:import --%>

<html>
<head>
	<title>CollegeMatch: Edit Favorite Details</title> <!-- this will change with each page -->
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<%@ include file="menusidebar.html" %>
	
	<!-- EDIT INDIVIDUAL FAVORITE -->
	<div id="content">
		<div id="title">
			<h2>Edit Favorite: <c:out value="${fullName}" /></h2> <!-- !!! Get name of college via form instead of SQL for performance reasons? Otherwise need SQL -->
		</div>
		<!-- form for scholarships, application status, etc. -->
		<form action="savefavorite.jsp" id="editfav" method="POST">
			<table id="editfav">
				<tr>
					<td class="label"><label for="rank">Rank:</label></td>
					<td class="input"><input class="number" type="number" id="rank" name="rank" value="1" min="1" max="20" /></td>
				</tr><tr>
					<td class="label"><label for="appstatus">Application Status:</label></td>
					<td class="input"><input class="inbox" type="text" id="appstatus" name="appstatus" value="None." /></td>
				</tr><tr>
					<td class="label"><label for="finaid">Financial Aid:</label></td>
					<td class="input">$<input class="number" type="number" id="finaid" name="finaid" value="0" onBlur="calcTotal()" /></td>
				</tr><tr>
					<td class="label"><label for="loans">Loan Amount:</label></td>
					<td class="input">$<input class="number" type="number" id="loans" name="loans" value="0" onBlur="calcTotal()" /></td>
				</tr><tr>
					<td class="label"><label for="merit">Merit Scholarships:</label></td>
					<td class="input">$<input class="number" type="number" id="merit" name="merit" value="0" onBlur="calcTotal()" /></td>
				</tr><tr>
					<td class="label"><span>Total Needed to Attend One Year:</span></td>
					<td class="input"><span id="costCounter"></span><input type="hidden" id="costStart" value='<c:out value="${cost}" />' /></td>
				</tr><tr>
					<td></td>
					<td class="input"><input type="submit" value="Save" /></td>
				</tr>
			</table>
		</form>
		<!-- display known data about school? demographics, et al? -->
		<%@ include file="detailstable.jsp" %>
	</div>
	
	</div></div> <!-- close wrap -->
	
<script type="text/javascript">
	calcTotal();

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

</c:when>
<c:otherwise>
	<c:redirect url="error401.jsp" />
</c:otherwise>
</c:choose>