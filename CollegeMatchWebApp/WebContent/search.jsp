<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>CollegeMatch: Search</title> <!-- this will change with each page -->
	<link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:import url="menusidebar.html" />
	
	<!-- SEARCH FOR COLLEGES -->
	<div id="content">
		<div id="title">
			<h2>Find Schools</h2>
		</div>
		<!-- advanced form -->
		<form id="advsearch" action="advsearch" method="POST">
			<table>
			
				<c:forEach begin="1" end="5" var="i">
					<tr>
						<td>
							<input type="hidden" id='crit<c:out value="${i}" />hid' name='crit<c:out value="${i}" />hid' />
							<select class="sel_type" name='crit<c:out value="${i}" />type' id='crit<c:out value="${i}" />type' onChange="showKids(this)">
								<c:import url="searchcriteria.html" />
							</select>
						</td>
						
						<td class="input critvals" id='crit<c:out value="${i}" />values'>
							<span id='crit<c:out value="${i}" />before'></span>
							<select id='crit<c:out value="${i}" />dist'>
								<option value="10">10mi</option>
								<option value="25">25mi</option>
								<option value="50">50mi</option>
								<option value="100">100mi</option>
							</select>
							<select id='crit<c:out value="${i}" />comp' name='crit<c:out value="${i}" />comp' onChange="toggleBetween(this)">
								<option value="lt">less than</option>
								<option value="bet">between</option>
								<option value="gt">greater than</option>
							</select>
							<select id='crit<c:out value="${i}" />level'>
								<option value="1">4-year</option>
								<option value="2">2-year</option>
								<option value="3">&lt;2-year</option>
							</select>
							<input class="number" type="number" name='crit<c:out value="${i}" />num1' id='crit<c:out value="${i}" />num1' />
							<span id='crit<c:out value="${i}" />mid'></span>
							<input id='crit<c:out value="${i}" />num2' class="number" type="number" name='crit<c:out value="${i}" />num2' />
							<input class="inbox" type="text" name='crit<c:out value="${i}" />text' id='crit<c:out value="${i}" />text' />
							<select class="inbox" name='crit<c:out value="${i}" />sel1' id='crit<c:out value="${i}" />sel1'>
								<c:import url="stateselect.html" />
							</select>
							<select class="sel_type" name='crit<c:out value="${i}" />sel2' id='crit<c:out value="${i}" />sel2'>
								<c:import url="fieldselect.html" />
							</select>
							<select class="sel_type" name='crit<c:out value="${i}" />sel3' id='crit<c:out value="${i}" />sel3'>
								<c:import url="regionselect.html" />
							</select>
							<br id='crit<c:out value="${i}" />checkBreak' />
							<input type="checkbox" name='crit<c:out value="${i}" />check' id='crit<c:out value="${i}" />check' value="1" onclick="nonum(this)" />
							<label for='crit<c:out value="${i}" />check' id='crit<c:out value="${i}" />checkLab'></label>
						</td>
					</tr>
				</c:forEach>
					
				<tr>
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