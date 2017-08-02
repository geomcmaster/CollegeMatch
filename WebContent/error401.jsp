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

<html>
<head>
	<title>CollegeMatch: Error 401 (Unauthorized)</title> <!-- this will change with each page -->
</head>
<link rel="stylesheet" type="text/css" href="style.css" />

<body>
	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<%@ include file="loginsidebar.html" %>
	
	<div id="content">
		<div id="title">
			<h2>Error 401 (Unauthorized)</h2>
		</div>
		<div id="intro"><p>It looks like you entered the wrong
			username and password combination. Try again to the right,
			<a href="register.html">register a new account</a>, or
			<a href="index.jsp">return to the home page</a>.</p>
		</div>
	</div>
	
	</div></div> <!-- close wrap -->
	
</body>
</html>