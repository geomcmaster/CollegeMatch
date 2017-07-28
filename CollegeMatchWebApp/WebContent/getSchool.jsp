<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<% 
	String schoolId = request.getParameter("schoolId");
	// SQL query for school
	// SELECT School.*,Location.*,GenderDemographics.*,RaceDemographics.*
	// FROM School, Location, GenderDemographics, RaceDemographics
	// WHERE School.id = Location.schoolId AND School.id = GenderDemographics.id AND School.id = RaceDemographics.id
%>
	<c:set var="shortName" value="UW-Madison" scope="request" />
	<c:set var="fullName" value="University of Wisconsin-Madison" scope="request" />
	<c:set var="url" value="http://www.wisc.edu" scope="request" />
	<c:set var="loc" value="Madison, WI" scope="request" />
	<c:set var="cost" value="23839" scope="request" />
	<c:set var="price" value="18397" scope="request" /> 
	<c:set var="sat25" value="1180" scope="request" />
	<c:set var="satAvg" value="1286" scope="request" />
	<c:set var="sat75" value="1400" scope="request" />
	<c:set var="act25" value="26" scope="request" />
	<c:set var="actAvg" value="29" scope="request" />
	<c:set var="act75" value="31" scope="request" />
	<c:set var="earnings" value="51600" scope="request" />
	<c:set var="size" value="29302" scope="request" />
	<c:set var="prog1" value="Social Sciences" scope="request" />
	<c:set var="prog2" value="Biological and Biomedical Sciences" scope="request" />
	<c:set var="prog3" value="Business" scope="request" />
	<c:set var="prog4" value="Engineering" scope="request" />
	<c:set var="prog5" value="Communication" scope="request" />
	<c:set var="admrate" value="0.5694" scope="request" />
	<c:set var="avgInc" value="101479" scope="request" />
	<c:set var="medInc" value="86640" scope="request" />
	<c:set var="tuitIn" value="10410" scope="request" />
	<c:set var="tuitOut" value="26660" scope="request" />
	<c:set var="avgAge" value="20" scope="request" />
	<c:set var="firstGen" value="0.192" scope="request" />
	<c:set var="level" value="4-Year" scope="request" />
	<c:set var="online" value="No" scope="request" />
	<c:set var="male" value="0.489" scope="request" />
	<c:set var="female" value="0.511" scope="request" />
	<c:set var="white" value="0.7705" scope="request" />
	<c:set var="black" value="0.0212" scope="request" />
	<c:set var="hispanic" value="0.0479" scope="request" />
	<c:set var="asian" value="0.0549" scope="request" />
	<c:set var="aian" value="0.0022" scope="request" />
	<c:set var="nhpi" value="0.0013" scope="request" />
	<c:set var="multi" value="0.0276" scope="request" />
	<c:set var="nra" value="0.0714" scope="request" />
	<c:set var="unk" value="0.003" scope="request" />