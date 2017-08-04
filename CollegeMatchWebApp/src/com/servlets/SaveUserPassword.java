package com.servlets;

import java.io.IOException;

import main.java.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUserPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		UserDAO db = new UserDAO();
		
		String oldPw = request.getParameter("old_pw");
		String newPw = request.getParameter("new_pw");
		
		boolean validPw;
		if (oldPw.length() > 0) {
			validPw = db.verifyPassword(username, oldPw);
		} else {
			validPw = true;
		}
		if (!validPw) {
			response.sendRedirect("editmyuser?pwfail");
		} else {
			db.updatePassword(username, newPw);
			response.sendRedirect("editmyuser?success");
		}
	}
}
