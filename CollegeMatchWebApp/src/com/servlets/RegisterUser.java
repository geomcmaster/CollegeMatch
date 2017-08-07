package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("reg_un");
		String password = request.getParameter("reg_pw");

		UserDAO db = new UserDAO();
		boolean success = db.createUser(username, password);
		
		if (success) {
			request.setAttribute("sb_username", username);
			request.setAttribute("sb_password", password);
			getServletContext().getRequestDispatcher("/login").forward(request,response);
		} else {
			response.sendRedirect("register.html?registerfailed");
		}
	}
}
