package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import main.java.FavoriteSchool;
import main.java.School;
import main.java.Location;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewFavorites extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		UserDAO db = new UserDAO();
		
		boolean successfulSave = (request.getParameter("success") != null);
		
		List<FavoriteSchool> favs = db.getFavSchools(username);
		
		String[] favSchools = new String[favs.size()];
		
		// array of schools
		// data format: rank|id|name|url|admrate|in-tuit|out-tuit|city|stateInt|stateAbbr|satavg|actavg|appstatus|finaid|loanamt|meritamt
		for (int i = 0; i < favs.size(); i++) {
			FavoriteSchool favSch = favs.get(i);
			String[] schoolValues = {" "," "," "," "," "," "," "," "," "," "," "};
			
			// Get favorite details
			String[] favValues = getFavoriteDetails(favSch);
			
			// Get school record			
			if (favSch.isSchoolNotNull()) {
				School sch = favSch.getSchool();
				schoolValues = Search.getSchoolDetails(sch);
			}
			
			// spaces make sure JSTL parses out each piece as discrete, even if the value is a null string
			String[] schoolData = {favValues[0], schoolValues[0], schoolValues[1], schoolValues[2], schoolValues[3],
					schoolValues[4], schoolValues[5], schoolValues[6], schoolValues[7], schoolValues[8], schoolValues[9],
					schoolValues[10], favValues[1], favValues[2], favValues[3], favValues[4]};
			String output = String.join("|",schoolData);
			favSchools[i] = output;
		}
		
		if (successfulSave) {
			request.setAttribute("success",1);
		} else {
			request.setAttribute("success",0);
		}
		
		if (favSchools.length > 0) {
			request.setAttribute("schools", favSchools);
			Location userResidence = db.getResidence(username);
			request.setAttribute("userState", userResidence.getStateInt());
			getServletContext().getRequestDispatcher("/favschools.jsp").forward(request,response);
		} else {
			response.sendRedirect("favschools.jsp?nofavs");
		}
	}
	
	public static String[] getFavoriteDetails(FavoriteSchool sch) {
		int rank = 0;
		String appStatus = new String();
		int finAid = 0;
		int loanAmt = 0;
		int meritAmt = 0;
		
		// Get rank
		if (sch.isRankNotNull()) {
			rank = sch.getRank();
		}
		
		// Get application status
		if (sch.isStatusNotNull()) {
			appStatus = sch.getStatus();
		}
		
		// Get financial aid
		if (sch.isFinancialAidNotNull()) {
			finAid = sch.getFinancialAid();
		}
		
		// Get loans
		if (sch.isLoanNotNull()) {
			loanAmt = sch.getLoan();
		}
		
		// Get merit scholarships
		if (sch.isMeritNotNull()) {
			meritAmt = sch.getMerit();
		}
		
		String[] outputValues = {" " + rank, " " + appStatus, " " + finAid,
				" " + loanAmt, " " + meritAmt};
		
		return outputValues;
	}
}
