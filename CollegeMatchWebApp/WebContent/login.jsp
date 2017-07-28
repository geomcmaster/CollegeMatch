<%@ page import="javax.servlet.http.*" %>
<%
	String username = new String();
	String password = new String();
	
	username = request.getParameter("sb_username");
	password = request.getParameter("sb_password");
	
%>
<%-- SQL query to check username/password combination --%>
<%
	if(true) { //validLogin(username, password))
		Cookie saveLogin = new Cookie("collegeMatchLogin",username);
		saveLogin.setMaxAge(60*60*24*7);
		response.addCookie(saveLogin);
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY); //OK
		response.setHeader("Location","userdata.jsp");
	} else {
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location","error401.jsp"); //HTTP Code Unauthorized
	}
%>