package com.servlets;

import java.io.IOException;
import main.java.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("sb_username");
		String password = request.getParameter("sb_password");
		
		if (username == null) {
			username = (String)request.getAttribute("sb_username");
			password = (String)request.getAttribute("sb_password");
		}

		UserDAO db = new UserDAO();
		boolean success = db.verifyPassword(username, password);
		
		if (success) {
			Cookie saveLogin = new Cookie("collegeMatchLogin",username);
			saveLogin.setMaxAge(60*60*24*7);
			response.addCookie(saveLogin);
			response.sendRedirect("editmyuser");
		} else {
			response.sendRedirect("register.html?loginfailed");
		}
	}
}