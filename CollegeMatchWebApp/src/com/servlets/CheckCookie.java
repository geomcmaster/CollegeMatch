package com.servlets;

import java.io.IOException;
import main.java.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

public class CheckCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				
				if (cookie.getName().equals("collegeMatchLogin")) {
					String username = cookie.getValue();
					
					UserDAO db = new UserDAO();
					User user = db.getUser(username);
					
					if (user.isValid()) {
						request.setAttribute("user", username);
					} else {
						response.sendRedirect("error401.jsp");
					}
					break;
				}
			}
		}
	}
}
