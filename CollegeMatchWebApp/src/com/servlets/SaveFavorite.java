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
		int rank = 0;
		int finAid = 0;
		int loans = 0;
		int merit = 0;
		String rankInput = request.getParameter("rank");
		if (rankInput.length() > 0) {
			rank = Integer.parseInt(rankInput);
		}
		String appStatus = request.getParameter("appstatus");
		String finAidInput = request.getParameter("finaid");
		if (finAidInput.length() > 0) {
			finAid = Integer.parseInt(finAidInput);
		}
		
		String loanInput = request.getParameter("loans");
		if (loanInput.length() > 0) {
			loans = Integer.parseInt(loanInput);
		}
		
		String meritInput = request.getParameter("merit");
		if (meritInput.length() > 0) {
			merit = Integer.parseInt(meritInput);
		}
		
		UserDAO db = new UserDAO();
		db.updateFavSchool(username, schoolId, rank, appStatus, finAid, loans, merit);
		
		response.sendRedirect("myfavorites?success=1");
	}
}
