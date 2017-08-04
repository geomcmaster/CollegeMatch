<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>CollegeMatch: Search Results</title> <!-- this will change with each page -->
</head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:import url="menusidebar.html" />
	
	<!-- DISPLAY COLLEGE RESULTS -->
	<div id="content">
		<div id="title">
			<h2>Search Results</h2>
		</div>
		
		
		<!-- header -->
		<div id="topbar"><form action="quicksearch" method="POST"><input type="text" class="inbox" name="qsearch" id="qsearch" /> <input type="submit" value="Quick Search" /><span class="alt-action"><a href="schoolsearch">Advanced Search</a></span></form> </div>
		
		<form action="modifyFavs" id="modifyFavs" method="POST">
			<c:set var="comboResults" value='${fn:join(results,"^")}' />
			<input type="hidden" name="searchresults" id="searchresults" value='<c:out value="${comboResults}" />' />
			<input type="hidden" name="userState" id="userState" value='<c:out value="${userState}" />' />
			<input type="hidden" name="modifyAction" id="modifyAction" />
			<input type="hidden" name="modifyId" id="modifyId" />
		<table class="college_list">
			<c:forEach items="${results}" var="school">
				<c:set var="schoolSplit" value="${fn:split(school, '|')}" />
				<c:set var="id" value="${fn:trim(schoolSplit[0])}" />
				<c:set var="name" value="${fn:trim(schoolSplit[1])}" />
				<c:set var="url" value="${fn:trim(schoolSplit[2])}" />
				<c:set var="admRate" value="${fn:trim(schoolSplit[3])}" />
				<c:set var="inTuition" value="${fn:trim(schoolSplit[4])}" />
				<c:set var="outTuition" value="${fn:trim(schoolSplit[5])}" />
				<c:set var="city" value="${fn:trim(schoolSplit[6])}" />
				<c:set var="stateId" value="${fn:trim(schoolSplit[7])}" />
				<c:set var="stateAbbr" value="${fn:trim(schoolSplit[8])}" />
				<c:set var="sat" value="${fn:trim(schoolSplit[9])}" />
				<c:set var="act" value="${fn:trim(schoolSplit[10])}" />
				
				<c:set var="isFav" value="${false}" />
				<c:forEach items="${favs}" var="favId">
					<c:if test="${id == favId}">
						<c:set var="isFav" value="${true}" />
					</c:if>
				</c:forEach>
				
				<tr>
					<td>
						<table class="college">
							<tr> <!-- basic data w/ link to view more detailed info -->
								<td><a href='viewSchool?id=<c:out value="${id}" />'><c:out value="${name}" /></a></td>
								<td class="input">
									<c:choose>
										<c:when test="${isFav}">
											<a href='JavaScript:void(0)' onclick='deleteFav(<c:out value="${id}" />)'><img class="star" src="images/Golden_star.svg"/></a>
										</c:when>
										<c:otherwise>
											<a href='JavaScript:void(0)' onclick='addFav(<c:out value="${id}" />)'><img class="star" src="images/gray_star.svg"/></a>
										</c:otherwise>
									</c:choose>
								</td>
							</tr><tr>
								<td>
									<dt>School URL</dt>
										<dd><a href='<c:out value="${url}" />'><c:out value="${url}" /></a></dd>
										
									<dt>Admission Rate</dt>
										<dd><f:formatNumber type="PERCENT" minFractionDigits="2" value="${admRate}" /></dd>
										
									<dt>Tuition</dt>
										<dd>
											<c:choose>
												<c:when test="${userState == stateId}">
													<f:formatNumber type="CURRENCY" value="${inTuition}" />
												</c:when>
												<c:otherwise>
													<f:formatNumber type="CURRENCY" value="${outTuition}" />
												</c:otherwise>
											</c:choose>
											 / year
										</dd>
								</td>
								<td>
									<dt>Location</dt>
										<dd><c:out value="${city}" />, <c:out value="${stateAbbr}" /></dd>
									
									<dt>Average Test Scores</dt>
										<dd>
											SAT: <f:formatNumber type="NUMBER" maxFractionDigits="0" groupingUsed="false" value="${sat}" /><br>
											ACT: <f:formatNumber type="NUMBER" maxFractionDigits="0" groupingUsed="false" value="${act}" />
										</dd>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:forEach>
		</table>
		</form>
	</div>
	
	</div></div> <!-- close wrap -->
	
<script type="text/javascript">
	function deleteFav(schoolId) {
		var form = document.getElementById("modifyFavs");
		var action = document.getElementById("modifyAction");
		var idInput = document.getElementById("modifyId");
		
		action.value = "delete";
		idInput.value = schoolId;
		
		form.submit();
	}
	
	function addFav(schoolId) {
		var form = document.getElementById("modifyFavs");
		var action = document.getElementById("modifyAction");
		var idInput = document.getElementById("modifyId");
		
		action.value = "add";
		idInput.value = schoolId;
		
		form.submit();
	}
</script>
	
</body>
</html>