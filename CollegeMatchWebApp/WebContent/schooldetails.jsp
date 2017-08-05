<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%--
	request.setAttribute("name","University of Wisconsin-Madison");
	request.setAttribute("url","http://www.wisc.edu");
	request.setAttribute("city","Madison");
	request.setAttribute("state","WI");
	request.setAttribute("region","Great Lakes");
	request.setAttribute("cost","23839");
	request.setAttribute("sat25","1770");
	request.setAttribute("satAvg","1929");
	request.setAttribute("sat75","2100");
	request.setAttribute("satE25","600");
	request.setAttribute("satEAvg","650");
	request.setAttribute("satE75","700");
	request.setAttribute("satM25","590");
	request.setAttribute("satMAvg","636");
	request.setAttribute("satM75","710");
	request.setAttribute("satW25","580");
	request.setAttribute("satWAvg","643");
	request.setAttribute("satW75","690");
	request.setAttribute("act25","26");
	request.setAttribute("actAvg","29");
	request.setAttribute("act75","31");
	request.setAttribute("actE25","25");
	request.setAttribute("actEAvg","29");
	request.setAttribute("actE75","31");
	request.setAttribute("actM25","27");
	request.setAttribute("actMAvg","30");
	request.setAttribute("actM75","30");
	request.setAttribute("actW25","26");
	request.setAttribute("actWAvg","28");
	request.setAttribute("actW75","32");
	request.setAttribute("earnings","51600");
	request.setAttribute("size","29302");
	request.setAttribute("prog_1","Social Sciences");
	request.setAttribute("prog_2","Biological and Biomedical Sciences");
	request.setAttribute("prog_3","Business, Management, Marketing, and Related Support Services");
	request.setAttribute("prog_4","Engineering");
	request.setAttribute("prog_5","Communication, Journalism, and Related Programs");
	request.setAttribute("admRate","0.5694");
	request.setAttribute("avgInc","101479");
	request.setAttribute("medInc","86640");
	request.setAttribute("tuitionIn","10410");
	request.setAttribute("tuitionOut","26660");
	request.setAttribute("medDebt","22028");
	request.setAttribute("age","20");
	request.setAttribute("firstGen","0.192");
	request.setAttribute("level","4-Year");
	request.setAttribute("distOnly","No");
	request.setAttribute("male","0.489");
	request.setAttribute("female","0.511");
	request.setAttribute("white","0.7705");
	request.setAttribute("black","0.0212");
	request.setAttribute("hispanic","0.0479");
	request.setAttribute("asian","0.0549");
	request.setAttribute("aian","0.0022");
	request.setAttribute("nhpi","0.0013");
	request.setAttribute("multi","0.0276");
	request.setAttribute("nonres","0.0714");
	request.setAttribute("unknown","0.003");
--%>

<html>
<head>
	<title>CollegeMatch: <c:out value="${name}" /> Details</title>
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
			<h2>School Details: <c:out value="${name}" /></h2>
		</div>
		<!-- all details -->
		<table class="college_list">
			<!-- BASIC DETAILS -->
			<tr>
				<td>Name:</td>
				<td class="input"><c:out value="${name}" /></td>
			</tr><tr>
				<td>Website:</td>
				<td class="input"><a href='<c:out value="${url}" />'><c:out value="${url}" /></a></td>
			</tr><tr>
				<td>Location:</td>
				<td class="input"><c:out value="${city}" />, <c:out value="${state}" /></td>
			</tr><tr>
				<td>Region:</td>
				<td class="input"><c:out value="${region}" /></td>
			</tr><tr>
				<td>Level of Institution:</td>
				<td class="input"><c:out value="${level}" /></td>
			</tr><tr>
				<td>Distance-Education (Online) Only?</td>
				<td class="input"><c:out value="${distOnly}" /></td>
			</tr>
			
			<!-- FINANCES -->
			<tr>
				<td>In-State Tuition:</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${tuitionIn}" /></td>
			</tr><tr>
				<td>Out-of-State Tuition:</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${tuitionOut}" /></td>
			</tr><tr>
				<td>Average Cost of Attendance:</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${cost}" /></td>
			</tr><tr>
				<td>Average Graduate Earnings (Annual) 6 Years after Admittance:<br />(The most recent data is from 2012)</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${earnings}" /></td>
			</tr><tr>
				<td>Median Graduate Debt:</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${medDebt}" /></td>
			</tr><tr>
				<td>Average Family Income:</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${avgInc}" /></td>
			</tr><tr>
				<td>Median Family Income:</td>
				<td class="input"><f:formatNumber type="CURRENCY" value="${medInc}" /></td>
			</tr>
			
			<!-- ACADEMICS -->
			<tr>
				<td>Admission Rate:</td>
				<td class="input"><f:formatNumber type="PERCENT" minFractionDigits="2" value="${admRate}" /></td>
			</tr><tr>
				<td>SAT Scores:</td>
				<td><table>
					<tr>
						<td><ul>
					<li>25th Percentile:</li>
					<li>Average:</li>
					<li>75th Percentile:</li>
						</ul></td>
						<td class="input">
							<span class="hoversource">
								<f:formatNumber type="NUMBER" groupingUsed="false" value="${sat25}" />
								<span class="target">
									English 25th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satE25}" /><br />
									Math 25th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satM25}" /><br />
									Writing 25th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satW25}" />
								</span>
							</span><br />
							<span class="hoversource">
								<f:formatNumber type="NUMBER" groupingUsed="false" value="${satAvg}" />
								<span class="target">
									English Average: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satEAvg}" /><br />
									Math Average: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satMAvg}" /><br />
									Writing Average: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satWAvg}" />
								</span>
							</span><br />
							<span class="hoversource">
								<f:formatNumber type="NUMBER" groupingUsed="false" value="${sat75}" />
								<span class="target">
									English 75th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satE75}" /><br />
									Math 75th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satM75}" /><br />
									Writing 75th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${satW75}" />
								</span>
							</span>
						</td>
					</tr>
				</table></td>
			</tr><tr>
				<td>ACT Scores:</td>
				<td><table>
					<tr>
						<td><ul>
					<li>25th Percentile:</li>
					<li>Average:</li>
					<li>75th Percentile:</li>
						</ul></td>
						<td class="input">
							<span class="hoversource">
								<f:formatNumber type="NUMBER" groupingUsed="false" value="${act25}" />
								<span class="target">
									English 25th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actE25}" /><br />
									Math 25th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actM25}" /><br />
									Writing 25th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actW25}" />
								</span>
							</span><br />
							<span class="hoversource">
								<f:formatNumber type="NUMBER" groupingUsed="false" value="${actAvg}" />
								<span class="target">
									English Average: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actEAvg}" /><br />
									Math Average: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actMAvg}" /><br />
									Writing Average: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actWAvg}" />
								</span>
							</span><br />
							<span class="hoversource">
								<f:formatNumber type="NUMBER" groupingUsed="false" value="${act75}" />
								<span class="target">
									English 75th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actE75}" /><br />
									Math 75th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actM75}" /><br />
									Writing 75th Percentile: <f:formatNumber type="NUMBER" groupingUsed="false" value="${actW75}" />
								</span>
							</span>
						</td>
					</tr>
				</table></td>
			</tr><tr>
				<td>Most Popular Programs:</td>
				<td><ol>
					<li><c:out value="${prog_1}" /></li>
					<li><c:out value="${prog_2}" /></li>
					<li><c:out value="${prog_3}" /></li>
					<li><c:out value="${prog_4}" /></li>
					<li><c:out value="${prog_5}" /></li>
				</ol></td>
			</tr>
			
			<!-- STUDENTS -->
			<tr>
				<td>Size of Undergraduate Student Body:</td>
				<td class="input"><f:formatNumber type="NUMBER" value="${size}" /></td>
			</tr><tr>
				<td>Average Age of Entry:</td>
				<td class="input"><f:formatNumber type="NUMBER" value="${age}" /></td>
			</tr><tr>
				<td>Percentage of First-Generation Students:</td>
				<td class="input"><f:formatNumber type="PERCENT" minFractionDigits="1" value="${firstGen}" /></td>
			</tr><tr>
				<td>Gender Demographics:</td>
				<td><table>
					<tr>
						<td><ul>
					<li>Male:</li>
					<li>Female:</li>
						</ul></td>
						<td class="input">
						<f:formatNumber type="PERCENT" minFractionDigits="1" value="${male}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="1" value="${female}" />
						</td>
					</tr>
				</table></td>
			</tr><tr>
				<td>Ethnic Demographics:</td>
				<td><table>
					<tr>
						<td><ul>
					<li>White:</li>
					<li>Black:</li>
					<li>Hispanic:</li>
					<li>Asian:</li>
					<li>American Indian:</li>
					<li>Pacific Islander:</li>
					<li>2 or more races:</li>
					<li>Non-Residents:</li>
					<li>Race is unknown:</li>
						</ul></td>
						<td class="input">
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${white}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${black}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${hispanic}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${asian}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${aian}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${nhpi}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${multi}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${nonres}" /><br />
						<f:formatNumber type="PERCENT" minFractionDigits="2" value="${unknown}" />
						</td>
					</tr>
				</table></td>
			</tr>
		</table>
	</div>
	
	</div></div> <!-- close wrap -->
	
</body>
</html>