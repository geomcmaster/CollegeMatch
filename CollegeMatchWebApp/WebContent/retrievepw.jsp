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
				<div class="nosidewrap">
				
				<!-- Warnings -->
				<div id="usernamewarning" class="warning hidden">That username does not exist in our database. Please try again or <a href="register.html">register a new account</a>.</div>
				
				<form id="retrieve" action="retrievepw" method="POST">
				<table class="userdata">
					<tr>
						<td class="label"><label for="retrieve_un">Username:</label></td>
						<td class="input"><input type="text" id="retrieve_un" name="retrieve_un" class="inbox" /></td>
					</tr>
					<tr>
						<td></td>
						<td class="input"><input type="submit" id="retrieve_submit" value="Reset my password"></td>
					</tr>
				</table>
				</form>
				</div>
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