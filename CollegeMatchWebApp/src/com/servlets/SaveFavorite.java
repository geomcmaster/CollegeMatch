package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		int schoolId = Integer.parseInt(request.getParameter("schoolId"));
		int rank = Integer.parseInt(request.getParameter("rank"));
		String appStatus = request.getParameter("appstatus");
		int finAid = Integer.parseInt(request.getParameter("finaid"));
		int loans = Integer.parseInt(request.getParameter("loans"));
		int merit = Integer.parseInt(request.getParameter("merit"));
		
		UserDAO db = new UserDAO();
		db.updateFavSchool(username, schoolId, rank, appStatus, finAid, loans, merit);
		
		response.sendRedirect("myfavorites");
	}
}
