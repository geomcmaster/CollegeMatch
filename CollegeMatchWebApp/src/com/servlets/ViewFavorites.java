package com.servlets;

import java.io.IOException;
import main.java.*;
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
		User user = db.getUser(username);
		
		List<FavoriteSchool> favs = user.getFavoriteSchools();
		
		String[] favSchools = new String[favs.size()];
		
		// array of schools
		// data format: rank|id|name|url|admrate|in-tuit|out-tuit|city|stateInt|stateAbbr|satavg|actavg|appstatus|finaid|loanamt|meritamt
		for (int i = 0; i < favs.size(); i++) {
			
			// TODO: implement IsNull functions
			
			FavoriteSchool favSch = favs.get(i);
			int rank = favSch.getRank();
			String appStatus = favSch.getStatus();
			int finAid = favSch.getFinancialAid();
			int loanAmt = favSch.getLoan();
			int meritAmt = favSch.getMerit();
			
			School sch = favSch.getSchool();
			// TODO: populate the sch object with needed data
			int id = 0;
			// int/String id = sch.getId();
			String name = sch.getName();
			String url = sch.getWebsite();
			
			double admRate = sch.getAdmissionRate();
			int intuit = sch.getTuitionIn();
			int outtuit = sch.getTuitionOut();
			
			Location loc = sch.getLocation();
			String city = loc.getCity();
			int stateInt = loc.getStateInt();
			String stateAbbr = loc.getStateAbbreviation();
			
			double SATavg = sch.getSatAvg();
			double ACTavg = sch.getActAvg();
			
			// spaces make sure JSTL parses out each piece as discrete, even if the value is a null string
			String[] schoolData = {" " + rank, " " + id, " " + name, " " + url, " " + admRate, " " + intuit,
					" " + outtuit, " " + city, " " + stateInt, " " + stateAbbr, " " + SATavg, " " + ACTavg, 
					" " + appStatus, " " + finAid, " " + loanAmt, " " + meritAmt};
			String output = String.join("|",schoolData);
			favSchools[i] = output;
		}
		
		request.setAttribute("schools", favSchools);
		request.setAttribute("userState", user.getLocation().getStateInt());
		
		getServletContext().getRequestDispatcher("/favschools.jsp").forward(request,response);
	}
}
