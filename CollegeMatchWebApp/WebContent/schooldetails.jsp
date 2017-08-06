<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
				<td class="input">
					<c:choose>
						<c:when test="${not empty level}">
							<c:out value="${level}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Distance-Education (Online) Only?</td>
				<td class="input"><c:out value="${distOnly}" /></td>
			</tr>
			
			<!-- FINANCES -->
			<tr>
				<td>In-State Tuition:</td>
				<td class="input">
					<c:choose>
						<c:when test="${tuitionIn != 0}">
							<f:formatNumber type="CURRENCY" value="${tuitionIn}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Out-of-State Tuition:</td>
				<td class="input">
					<c:choose>
						<c:when test="${tuitionOut != 0}">
							<f:formatNumber type="CURRENCY" value="${tuitionOut}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Average Cost of Attendance:</td>
				<td class="input">
					<c:choose>
						<c:when test="${cost != 0}">
							<f:formatNumber type="CURRENCY" value="${cost}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Average Graduate Earnings (Annual) 6 Years after Admittance:<br />(The most recent data is from 2012)</td>
				<td class="input">
					<c:choose>
						<c:when test="${earnings != 0}">
							<f:formatNumber type="CURRENCY" value="${earnings}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Median Graduate Debt:</td>
				<td class="input">
					<c:choose>
						<c:when test="${medDebt != 0}">
							<f:formatNumber type="CURRENCY" value="${medDebt}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Average Family Income:</td>
				<td class="input">
					<c:choose>
						<c:when test="${avgInc != 0}">
							<f:formatNumber type="CURRENCY" value="${avgInc}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Median Family Income:</td>
				<td class="input">
					<c:choose>
						<c:when test="${medInc != 0}">
							<f:formatNumber type="CURRENCY" value="${medInc}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			<!-- ACADEMICS -->
			<tr>
				<td>Admission Rate:</td>
				<td class="input">
					<c:choose>
						<c:when test="${admRate != 0}">
							<f:formatNumber type="PERCENT" minFractionDigits="2" value="${admRate}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
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
							<c:choose>
								<c:when test="${act25 != 0}">
									<f:formatNumber type="NUMBER" groupingUsed="false" value="${sat25}" /><br />
								</c:when>
								<c:otherwise>
									No data<br />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${actAvg != 0}">
									<f:formatNumber type="NUMBER" groupingUsed="false" value="${satAvg}" /><br />
								</c:when>
								<c:otherwise>
									No data<br />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${act75 != 0}">
									<f:formatNumber type="NUMBER" groupingUsed="false" value="${sat75}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
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
							<c:choose>
								<c:when test="${act25 != 0}">
									<f:formatNumber type="NUMBER" groupingUsed="false" value="${act25}" /><br />
								</c:when>
								<c:otherwise>
									No data<br />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${actAvg != 0}">
									<f:formatNumber type="NUMBER" groupingUsed="false" value="${actAvg}" /><br />
								</c:when>
								<c:otherwise>
									No data<br />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${act75 != 0}">
									<f:formatNumber type="NUMBER" groupingUsed="false" value="${act75}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table></td>
			</tr><tr>
				<td>Most Popular Programs:</td>
				<td>
					<c:choose>
						<c:when test="${not empty prog_1}">
							<ol>
								<li><c:out value="${prog_1}" /></li>
								<c:if test="${not empty prog_2}">
									<li><c:out value="${prog_2}" /></li>
									<c:if test="${not empty prog_3}">
										<li><c:out value="${prog_3}" /></li>
										<c:if test="${not empty prog_4}">
											<li><c:out value="${prog_4}" /></li>
											<c:if test="${not empty prog_5}">
												<li><c:out value="${prog_5}" /></li>
											</c:if>
										</c:if>
									</c:if>
								</c:if>
							</ol>
						</c:when>
						<c:otherwise>
							<span style="float:right;">No data</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
			<!-- STUDENTS -->
			<tr>
				<td>Size of Undergraduate Student Body:</td>
				<td class="input">
					<c:choose>
						<c:when test="${size != 0}">
							<f:formatNumber type="NUMBER" value="${size}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Average Age of Entry:</td>
				<td class="input">
					<c:choose>
						<c:when test="${age != 0}">
							<f:formatNumber type="NUMBER" value="${age}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Percentage of First-Generation Students:</td>
				<td class="input">
					<c:choose>
						<c:when test="${firstGen != 0}">
							<f:formatNumber type="PERCENT" minFractionDigits="1" value="${firstGen}" />
						</c:when>
						<c:otherwise>
							No data
						</c:otherwise>
					</c:choose>
				</td>
			</tr><tr>
				<td>Gender Demographics:</td>
				<td><table>
					<tr>
						<td><ul>
					<li>Male:</li>
					<li>Female:</li>
						</ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${male != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="1" value="${male}" /><br />
								</c:when>
								<c:otherwise>
									No data<br />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${female != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="1" value="${female}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table></td>
			</tr><tr>
				<td>Ethnic Demographics:</td>
				<td><table class="demolist">
					<tr>
						<td><ul><li>White:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${white != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${white}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>Black:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${black != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${black}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>Hispanic:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${hispanic != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${hispanic}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>Asian:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${asian != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${asian}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>American Indian / Alaskan Native:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${aian != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${aian}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>Native Hawaiian / Pacific Islander:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${nhpi != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${nhpi}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>2 or more races:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${multi != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${multi}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>Non-residents:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${nonres != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${nonres}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td><ul><li>Race is unknown:</li></ul></td>
						<td class="input">
							<c:choose>
								<c:when test="${unknown != 0}">
									<f:formatNumber type="PERCENT" minFractionDigits="2" value="${unknown}" />
								</c:when>
								<c:otherwise>
									No data
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</div>
	
	</div></div> <!-- close wrap -->
	
</body>
</html>