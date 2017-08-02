<table class="college_list">
	<tr>
		<td>Name:</td>
		<td class="input"><c:out value="${fullName}" /></td>
	</tr><tr>
		<td>Website:</td>
		<td class="input"><a href='<c:out value="${url}" />'><c:out value="${url}" /></a></td>
	</tr><tr>
		<td>Location:</td>
		<td class="input"><c:out value="${loc}" /></td>
	</tr><tr>
		<td>Cost of Attendance:</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${cost}" /></td>
	</tr><tr>
		<td>Average Net Price:<br>(Cost of Attendance minus Average Grants/Scholarships/Aid)</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${price}" /></td>
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
				<f:formatNumber type="NUMBER" groupingUsed="false" value="${sat25}" /><br />
				<f:formatNumber type="NUMBER" groupingUsed="false" value="${satAvg}" /><br />
				<f:formatNumber type="NUMBER" groupingUsed="false" value="${sat75}" />
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
				<f:formatNumber type="NUMBER" groupingUsed="false" value="${act25}" /><br />
				<f:formatNumber type="NUMBER" groupingUsed="false" value="${actAvg}" /><br />
				<f:formatNumber type="NUMBER" groupingUsed="false" value="${act75}" />
				</td>
			</tr>
		</table></td>
	</tr><tr>
		<td>Median Graduate Earnings (Annual):</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${earnings}" /></td>
	</tr><tr>
		<td>Size of Undergraduate Student Body:</td>
		<td class="input"><f:formatNumber type="NUMBER" value="${size}" /></td>
	</tr><tr>
		<td>Most Popular Programs:</td>
		<td><ol>
			<li><c:out value="${prog1}" /></li>
			<li><c:out value="${prog2}" /></li>
			<li><c:out value="${prog3}" /></li>
			<li><c:out value="${prog4}" /></li>
			<li><c:out value="${prog5}" /></li>
		</ol></td>
	</tr><tr>
		<td>Admission Rate:</td>
		<td class="input"><f:formatNumber type="PERCENT" minFractionDigits="2" value="${admrate}" /></td>
	</tr><tr>
		<td>Average Family Income:</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${avgInc}" /></td>
	</tr><tr>
		<td>Median Family Income:</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${medInc}" /></td>
	</tr><tr>
		<td>In-State Tuition:</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${tuitIn}" /></td>
	</tr><tr>
		<td>Out-of-State Tuition:</td>
		<td class="input"><f:formatNumber type="CURRENCY" value="${tuitOut}" /></td>
	</tr><tr>
		<td>Average Age of Entry:</td>
		<td class="input"><f:formatNumber type="NUMBER" value="${avgAge}" /></td>
	</tr><tr>
		<td>Percentage of First-Generation Students:</td>
		<td class="input"><f:formatNumber type="PERCENT" minFractionDigits="1" value="${firstGen}" /></td>
	</tr><tr>
		<td>Level of Institution:</td>
		<td class="input"><c:out value="${level}" /></td>
	</tr><tr>
		<td>Distance-Education (Online) Only?</td>
		<td class="input"><c:out value="${online}" /></td>
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
				<f:formatNumber type="PERCENT" minFractionDigits="2" value="${nra}" /><br />
				<f:formatNumber type="PERCENT" minFractionDigits="2" value="${unk}" />
				</td>
			</tr>
		</table></td>
	</tr>
</table>