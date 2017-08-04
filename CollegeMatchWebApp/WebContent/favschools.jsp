<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<%--
//data format: rank|id|name|url|admrate|in-tuit|out-tuit|city|stateInt|stateAbbr|satavg|actavg|appstatus|finaid|loanamt|meritamt
	String[] schools = {" 1| 123| University of Wisconsin-Madison| http://www.wisc.edu| 0.557| 10410| 26660| Madison| 55| WI| 1285.9| 29.2| Applied!| 0| 0| 50",
			" 2| 456| University of Illinois| http://illinois.edu/| 0.593| 12345| 29646| Champaign| 17| IL| 1326| 29| Not applied yet.| 0| 8000| 0"};	request.setAttribute("schools", schools);
	request.setAttribute("userState",55);
--%>

<html>
<head>
	<title>CollegeMatch: My Favorite Colleges</title> <!-- this will change with each page -->
</head>
<link rel="stylesheet" type="text/css" href="style.css" />

<body>

	<div id="wrap"> <!-- open wrap -->
	<div id="header"></div>
	<div id="contentwrap">
	
	<c:import url="menusidebar.html" />
	
	<!-- VIEW FAVORITE COLLEGES -->
	<div id="content">
		<div id="title">
			<h2>My Favorite Colleges</h2>
		</div>
		
		<!-- Warnings -->
		<div id="nofavwarning" class="warning hidden">You don't have any favorite schools.</div>
		
		<!-- link to search for more->find schools -->
		<p>Want to find more? Go to <a href="schoolsearch">search</a>!</p>
		<table class="college_list">
		
			<!-- loop through list w/ edit buttons->edit individual favorite, delete buttons -->
			<c:forEach items="${schools}" var="school">
				<c:set var="schoolSplit" value="${fn:split(school, '|')}" />
				<c:set var="rank" value="${fn:trim(schoolSplit[0])}" />
				<c:set var="id" value="${fn:trim(schoolSplit[1])}" />
				<c:set var="name" value="${fn:trim(schoolSplit[2])}" />
				<c:set var="url" value="${fn:trim(schoolSplit[3])}" />
				<c:set var="admRate" value="${fn:trim(schoolSplit[4])}" />
				<c:set var="inTuition" value="${fn:trim(schoolSplit[5])}" />
				<c:set var="outTuition" value="${fn:trim(schoolSplit[6])}" />
				<c:set var="city" value="${fn:trim(schoolSplit[7])}" />
				<c:set var="stateId" value="${fn:trim(schoolSplit[8])}" />
				<c:set var="stateAbbr" value="${fn:trim(schoolSplit[9])}" />
				<c:set var="sat" value="${fn:trim(schoolSplit[10])}" />
				<c:set var="act" value="${fn:trim(schoolSplit[11])}" />
				<c:set var="status" value="${fn:trim(schoolSplit[12])}" />
				<c:set var="finAid" value="${fn:trim(schoolSplit[13])}" />
				<c:set var="loans" value="${fn:trim(schoolSplit[14])}" />
				<c:set var="merit" value="${fn:trim(schoolSplit[15])}" />
				
				<tr>
					<td>
						<table class="college">
							<tr>
								<td>#<c:out value="${rank}" /> <a href='viewSchool?id=<c:out value="${id}" />'><c:out value="${name}" /></a></td>
								<td class="input"><a href=""><!--delete action--><img src="images/delete-128.png" width="14"></a></td>
							</tr><tr>
								<td>
									<dt>Application Status</dt>
										<dd><c:out value="${status}" /></dd>
										
									<dt>Financial Aid</dt>
										<dd>
											<c:choose>
												<c:when test="${finAid > 0}">
													<f:formatNumber type="CURRENCY" value="${finAid}" />
												</c:when>
												<c:otherwise>
													None.
												</c:otherwise>
											</c:choose>
										</dd>
										
									<dt>Loan Amount</dt>
										<dd>
											<c:choose>
												<c:when test="${loans > 0}">
													<f:formatNumber type="CURRENCY" value="${loans}" />
												</c:when>
												<c:otherwise>
													None.
												</c:otherwise>
											</c:choose>
										</dd>
										
									<dt>Merit Scholarships</dt>
										<dd>
											<c:choose>
												<c:when test="${merit > 0}">
													<f:formatNumber type="CURRENCY" value="${merit}" />
												</c:when>
												<c:otherwise>
													None.
												</c:otherwise>
											</c:choose>
										</dd>
										
								</td>
								<td class="input alt-action"><a href='editFav?id=<c:out value="${id}" />'>Edit details</a></td>
								
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
	</div>
	
	</div></div> <!-- close wrap -->
		
<script type="text/javascript">
	<!-- JavaScript to show warnings when applicable -->
	var URL = window.location.href;
	var param = URL.slice(URL.indexOf("?") + 1);
	if (param.includes("nofavs")) {
		document.getElementById("nofavwarning").classList.remove("hidden");
	}
</script>

</body>
</html>