<%@ page import="javax.servlet.http.*" %>
<%
	Cookie cookie = null;
	Cookie[] cookies = request.getCookies();
	
	for (int i = 0; i < cookies.length; i++) {
		cookie = cookies[i];
		if (cookie.getName().equals("collegeMatchLogin")) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.setStatus(response.SC_MOVED_TEMPORARILY); //OK
			response.setHeader("Location","index.jsp");
		}
	}
%>