<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>CollegeMatch</title> <!-- this will change with each page -->
</head>
<link rel="stylesheet" type="text/css" href="style.css" />

<body>
	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:import url="loginsidebar.html" />
	
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