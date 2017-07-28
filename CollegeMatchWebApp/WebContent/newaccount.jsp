<%@ page import="javax.servlet.http.*" %>
<%
	String username = new String();
	String password = new String();
	String secondPW = new String();
	
	username = request.getParameter("reg_un");
	password = request.getParameter("reg_pw");
	secondPW = request.getParameter("pwcheck");
	
	if (!password.equals(secondPW)) {
		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		response.setHeader("Location","nomatch.jsp");
	} else {
		
	%>
	<%-- SQL query to save new username to database --%>
	<%
		if(true) { //successful
			Cookie saveLogin = new Cookie("collegeMatchLogin",username);
			saveLogin.setMaxAge(60*60*24*7);
			response.addCookie(saveLogin);
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY); //OK
			response.setHeader("Location","userdata.jsp");
		} else { // username already exists
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("Location","exists.jsp");
		}
	}
%>