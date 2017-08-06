package com.servlets;

import java.io.IOException;
import main.java.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

public class ReplacePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("retrieve_un");
		
		UserDAO db = new UserDAO();
		User user = db.getUser(username);
		
		if (user.isValid()) {
			Random rand = new Random();
			
			String newPw = new String();
			while (newPw.length() < 8) {
				char newChar = (char)(rand.nextInt(93) + 34);
				newPw = newPw + newChar;
			}
			
			db.updatePassword(username, newPw);
			
			request.setAttribute("newPw",newPw);
			
			getServletContext().getRequestDispatcher("/newpw.jsp").forward(request,response);
		} else {
			response.sendRedirect("retrievepw.jsp?nouser");
		}
	}
}
