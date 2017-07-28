<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*" %>
<%
	Cookie cookie = null;
	Cookie[] cookies = request.getCookies();
	String cookieValue = new String();
	
	if(cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			cookieValue = cookie.getValue();
			if (cookie.getName().equals("collegeMatchLogin")) { %>
				<c:set var="user" value="${cookieValue}" scope="request" />
<%			}
		}
	}
	
%>