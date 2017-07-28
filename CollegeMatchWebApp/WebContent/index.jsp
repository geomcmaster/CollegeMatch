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
<%@ include file="getUserCookie.jsp" %>

<html>
<head>
	<title>CollegeMatch</title> <!-- this will change with each page -->
</head>
<link rel="stylesheet" type="text/css" href="style.css" />

<body>
	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:choose>
		<c:when test="${user.length > 0}"><%-- SQL query to test validity of username --%>
			<c:import url="menusidebar.html" />
		</c:when>
		<c:otherwise>
			<c:import url="loginsidebar.html" />
		</c:otherwise>
	</c:choose>
	
	<!-- FRONT PAGE -->
	<div id="content">
		<div id="title">
			<h2>Welcome to CollegeMatch!</h2>
		</div>
		<div id="intro"><p>Our goal is to make it easy for you to search for the
			perfect college or university for you and to keep track of your progress
			as you apply to those schools.</p>
			<p>Our search functions cover school location, demographics, and your 
			favorite fields of study to ensure that you find the best match for your
			interests and needs.</p>
			<p>To get started, <a href="register.html">register an account or log in</a>.</p>
		</div>
	</div>
	
	</div></div> <!-- close wrap -->
	
</body>
</html>