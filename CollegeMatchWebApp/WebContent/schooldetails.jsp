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
<%@ include file="getUserCookie.jsp" %>
<%@ include file="validUser.jsp" %>
<c:choose>
<c:when test="${validUser}">

<c:import url="getSchool.jsp">
	<c:param name="schoolId" value="${param.schoolId}" />
</c:import>

<html>
<head>
	<title>CollegeMatch: <c:out value="${shortName}" /> Details</title> <!-- this will change with each page -->
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<%@ include file="menusidebar.html" %>
	
	<!-- SEARCH FOR COLLEGES -->
	<div id="content">
		<div id="title">
			<h2>School Details: <c:out value="${fullName}" /></h2>
		</div>
		<!-- all details -->
		<%@ include file="detailstable.jsp" %>
	</div>
	
	</div></div> <!-- close wrap -->
	
</body>
</html>

</c:when>
<c:otherwise>
	<c:redirect url="error401.jsp" />
</c:otherwise>
</c:choose>