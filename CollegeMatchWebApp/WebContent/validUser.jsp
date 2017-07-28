<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:set var="validCheck" />
<c:choose>
	<c:when test="${false}"> <%-- !isValid(user) --%>
		<c:set var="validCheck" scope="request" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="validCheck" scope="request" value="true" />
	</c:otherwise>
</c:choose>