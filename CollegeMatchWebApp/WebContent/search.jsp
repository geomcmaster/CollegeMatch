<!-- List o' tasks -->
<!--
TASK				QUERY			SCOPE	
register user		insert			1 table
save user data		insert/update	1 table
delete user data	delete			1 table
save favorites		insert/update	3+ tables (depends on user, field, school)
search	(school)	select
 - location						2 tables
 - demographics					2-3 tables
 - field of study					2 tables
 - based on user location			3 tables, w/ subqueries
search (field)		select			1 table
display search results
display user data
display favorites
-->

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ include file="getUserCookie.jsp" %>
<%@ include file="validUser.jsp" %>
<c:choose>
<c:when test="${validCheck}">

<html>
<head>
	<title>CollegeMatch: Search</title> <!-- this will change with each page -->
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
			<h2>Find Schools</h2>
		</div>
		<!-- advanced form -->
		<form id="advsearch" action="advsearch.jsp">
			<table>
				<tr>
					<td>
						<input type="hidden" id="crit1hid" />
						<select class="sel_type" name="crit1type" id="crit1type" onChange="showKids(this)"></select>
					</td>
					
					<td class="input critvals" id="crit1values">
						<span id="crit1before"></span>
						<select id="crit1dist">
							<option value="10">10mi</option>
							<option value="25">25mi</option>
							<option value="50">50mi</option>
							<option value="100">100mi</option>
						</select>
						<select id="crit1comp" onChange="toggleBetween(this)">
							<option value="lt">less than</option>
							<option value="bet">between</option>
							<option value="gt">greater than</option>
						</select>
						<select id="crit1level">
							<option value="2">2-year</option>
							<option value="4">4-year</option>
						</select>
						<input type="checkbox" name="crit1check" id="crit1check" />
						<input class="number" type="number" name="crit1num1" id="crit1num1" />
						<span id="crit1mid"></span>
						<input id="crit1num2" class="number" type="number" name="crit1num2" />
						<input class="inbox" type="text" name="crit1text" id="crit1text" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="crit2hid" />
						<select class="sel_type" name="crit2type" id="crit2type" onChange="showKids(this)"></select>
					</td>
					
					<td class="input critvals" id="crit2values">
						<span id="crit2before"></span>
						<select id="crit2dist">
							<option value="10">10mi</option>
							<option value="25">25mi</option>
							<option value="50">50mi</option>
							<option value="100">100mi</option>
						</select>
						<select id="crit2comp" onChange="toggleBetween(this)">
							<option value="lt">less than</option>
							<option value="bet">between</option>
							<option value="gt">greater than</option>
						</select>
						<select id="crit2level">
							<option value="2">2-year</option>
							<option value="4">4-year</option>
						</select>
						<input type="checkbox" name="crit2check" id="crit2check" />
						<input class="number" type="number" name="crit2num1" id="crit2num1" />
						<span id="crit2mid"></span>
						<input id="crit2num2" class="number" type="number" name="crit2num2" />
						<input class="inbox" type="text" name="crit2text" id="crit2text" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="crit3hid" />
						<select class="sel_type" name="crit3type" id="crit3type" onChange="showKids(this)"></select>
					</td>
					
					<td class="input critvals" id="crit3values">
						<span id="crit3before"></span>
						<select id="crit3dist">
							<option value="10">10mi</option>
							<option value="25">25mi</option>
							<option value="50">50mi</option>
							<option value="100">100mi</option>
						</select>
						<select id="crit3comp" onChange="toggleBetween(this)">
							<option value="lt">less than</option>
							<option value="bet">between</option>
							<option value="gt">greater than</option>
						</select>
						<select id="crit3level">
							<option value="2">2-year</option>
							<option value="4">4-year</option>
						</select>
						<input type="checkbox" name="crit3check" id="crit3check" />
						<input class="number" type="number" name="crit3num1" id="crit3num1" />
						<span id="crit3mid"></span>
						<input id="crit3num2" class="number" type="number" name="crit3num2" />
						<input class="inbox" type="text" name="crit3text" id="crit3text" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="crit4hid" />
						<select class="sel_type" name="crit4type" id="crit4type" onChange="showKids(this)"></select>
					</td>
					
					<td class="input critvals" id="crit4values">
						<span id="crit4before"></span>
						<select id="crit4dist">
							<option value="10">10mi</option>
							<option value="25">25mi</option>
							<option value="50">50mi</option>
							<option value="100">100mi</option>
						</select>
						<select id="crit4comp" onChange="toggleBetween(this)">
							<option value="lt">less than</option>
							<option value="bet">between</option>
							<option value="gt">greater than</option>
						</select>
						<select id="crit4level">
							<option value="2">2-year</option>
							<option value="4">4-year</option>
						</select>
						<input type="checkbox" name="crit4check" id="crit4check" />
						<input class="number" type="number" name="crit4num1" id="crit4num1" />
						<span id="crit4mid"></span>
						<input id="crit4num2" class="number" type="number" name="crit4num2" />
						<input class="inbox" type="text" name="crit4text" id="crit4text" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="crit5hid" />
						<select class="sel_type" name="crit5type" onChange="showKids(this)" id="crit5type"></select>
					</td>
					
					<td class="input critvals" id="crit5values">
						<span id="crit5before"></span>
						<select id="crit5dist">
							<option value="10">10mi</option>
							<option value="25">25mi</option>
							<option value="50">50mi</option>
							<option value="100">100mi</option>
						</select>
						<select id="crit5comp" onChange="toggleBetween(this)">
							<option value="lt">less than</option>
							<option value="bet">between</option>
							<option value="gt">greater than</option>
						</select>
						<select id="crit5level">
							<option value="2">2-year</option>
							<option value="4">4-year</option>
						</select>
						<input type="checkbox" name="crit5check" id="crit5check" />
						<input class="number" type="number" name="crit5num1" id="crit5num1" />
						<span id="crit5mid"></span>
						<input id="crit5num2" class="number" type="number" name="crit5num2" />
						<input class="inbox" type="text" name="crit5text" id="crit5text" />
					</td>
				</tr><tr>
					<td></td>
					<td class="input"><input type="submit" value="Search" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	</div></div> <!-- close wrap -->
	
<script type="text/javascript" src="searchpage.js"></script>

</body>
</html>

</c:when>
<c:otherwise>
	<c:redirect url="error401.jsp" />
</c:otherwise>
</c:choose>