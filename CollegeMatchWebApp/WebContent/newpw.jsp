<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>CollegeMatch: Register/Log In</title> <!-- this will change with each page -->
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

	<div id="wrap"> <!-- open wrap -->
		<div id="header"></div>
		<div id="contentwrap">
	
		<c:import url="loginsidebar.html" />
		
		<!-- FRONT PAGE -->
			<div id="content">
				<div id="title">
					<h2>Retrieve/Replace My Password</h2>
				</div>
				
				Your new password is <strong><c:out value="${newPw}" /></strong>. Please save 
				this or use it immediately to log in and change your password to something you
				can remember.
			</div>
		</div>
	</div> <!-- close wrap -->
	
<script type="text/javascript">
	<!-- JavaScript to show warnings when applicable -->
	var URL = window.location.href;
	var param = URL.slice(URL.indexOf("?") + 1);
	if (param.includes("nouser")) {
		document.getElementById("usernamewarning").classList.remove("hidden");
	}
</script>
	
</body>
</html>