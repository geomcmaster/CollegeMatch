package com.users;

import java.io.IOException;
import main.java.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUser extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("reg_un");
		String password = request.getParameter("reg_pw");

		UserDAO db = new UserDAO();
		boolean success = db.createUser(username, password);
		
		if (success) {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			getServletContext().getRequestDispatcher("/login").forward(request,response);
		} else {
			// notify the user that the username is already in use
		}
	}
}
