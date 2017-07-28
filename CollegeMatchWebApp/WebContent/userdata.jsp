
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:import url="getUserCookie.jsp" />
<c:import url="validUser.jsp" />

<c:choose>

<c:when test="${validCheck}">

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
		<form id="editdata" action="savedata.jsp" method="POST">
																<!-- !!! We need to retrieve SQL data for this based on cookies. -->
		<table id="userdata">
			<tr>
				<td class="label"><label for="edit_un">Username:</label></td>
				<td class="input"><input class="inbox" type="text" id="edit_un" name="edit_un" value='<c:out value="${user}"/>' />
			</tr><tr>
				<td class="label"><label for="edit_sat">Composite (Reading/Writing &amp; Math) SAT Score:</label></td>
				<td class="input"><input class="number" type="number" name="edit_sat" id="edit_sat" min="400" max="1600" value="500" /> <!--[my current SAT score]-->
			</tr><tr>
				<td class="label"><label for="edit_act">Composite ACT Score:</label></td>
				<td class="input"><input class="number" type="number" name="edit_act" id="edit_act" min="1" max="36" value="5" /> <!--[my current ACT score]-->
			</tr><tr>
				<td class="label"></td>
				<td class="input"><input type="submit" id="edit_submit" value="Save" /></td>
			</tr>
		</table>
		
		</form>
	</div>
	
	</div></div> <!-- close wrap -->
	
</body>
</html>

</c:when>
<c:otherwise>
	<c:redirect url="error401.jsp" />
</c:otherwise>
</c:choose>