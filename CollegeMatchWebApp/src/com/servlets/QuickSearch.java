package com.servlets;

import java.io.IOException;
import main.java.School;
import main.java.SchoolDAO;
import main.java.UserDAO;
import main.java.Location;
import main.java.CondVal;
import main.java.CondType;
import main.java.Condition;
import main.java.FavoriteSchool;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

public class QuickSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		int i = 0;
		
		List<Condition> conditions = new ArrayList<Condition>();
		List<Condition> OrGroup = new ArrayList<Condition>();
		byte tablesToJoin = SchoolDAO.NONE;
		SchoolDAO db = new SchoolDAO();
		
		String colName = School.NAME;
		CondType cType = CondType.LIKE;
		CondVal cValue = CondVal.createStrVal("%" + request.getParameter("qsearch") + "%");
		OrGroup.add(new Condition(colName, cType, cValue));
		
		colName = School.ALIAS;
		OrGroup.add(new Condition(colName, cType, cValue));
		
		cType = CondType.OR_GROUP;
		cValue = CondVal.createORGroupVal(OrGroup);
		conditions.add(new Condition("", cType, cValue));
		
		List<School> results = db.getSchools(conditions, tablesToJoin);
		String[] outputResults = new String[results.size()];
		// data format: id|name|url|admrate|in-tuit|out-tuit|city|stateInt|stateAbbr|satavg|actavg
		for (i = 0; i < results.size(); i++) {
			School curSch = results.get(i);
			int curId = 0; //TODO: fix when possible
			String curName = curSch.getName();
			String curUrl = curSch.getWebsite();
			double curAdmRate = curSch.getAdmissionRate();
			int tuitionIn = curSch.getTuitionIn();
			int tuitionOut = curSch.getTuitionOut();
			Location curLoc = curSch.getLocation();
			String curCity = curLoc.getCity();
			int curStateInt = curLoc.getStateInt();
			String curStateAbbr = curLoc.getStateAbbreviation();
			double curSat = curSch.getSatAvg();
			double curAct = curSch.getActAvg();
			
			String[] allValues = {" " + curId, " " + curName, " " + curUrl,
					" " + curAdmRate, " " + tuitionIn, " " + tuitionOut,
					" " + curCity, " " + curStateInt, " " + curStateAbbr,
					" " + curSat, " " + curAct};
			outputResults[i] = String.join("|", allValues);
		}
		
		request.setAttribute("results", outputResults);
		
		UserDAO userDb = new UserDAO(); 
		Location userResidence = userDb.getResidence(username);
		request.setAttribute("userState", userResidence.getStateInt());
		
		List<FavoriteSchool> userFavs = userDb.getFavSchools(username);
		outputResults = new String[userFavs.size()];
		// data format: id
		for (i = 0; i < userFavs.size(); i++) {
			School curFav = userFavs.get(i).getSchool();
			int curId = 0; //TODO: fix when possible
			outputResults[i] = "" + curId;
		}
		request.setAttribute("favs", outputResults);
		
		getServletContext().getRequestDispatcher("/results.jsp").forward(request,response);
	}
}
