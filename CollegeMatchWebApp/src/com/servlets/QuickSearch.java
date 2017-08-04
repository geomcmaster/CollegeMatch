package com.servlets;

import java.io.IOException;
import main.java.School;
import main.java.SchoolDAO;
import main.java.CondVal;
import main.java.CondType;
import main.java.Condition;
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
		cValue = CondVal.createStrVal("%" + request.getParameter("qsearch") + "%");
		OrGroup.add(new Condition(colName, cType, cValue));
		
		cType = CondType.OR_GROUP;
		cValue = CondVal.createORGroupVal(OrGroup);
		conditions.add(new Condition("", cType, cValue));
		
		List<School> results = db.getSchools(conditions, tablesToJoin);
		String[] outputResults = new String[results.size()];
		// data format: id|name|url|admrate|in-tuit|out-tuit|city|stateInt|stateAbbr|satavg|actavg
		for (i = 0; i < results.size(); i++) {
			School curSch = results.get(i);
			String[] allValues = Search.getSchoolDetails(curSch);
			outputResults[i] = String.join("|", allValues);
		}
		
		request.setAttribute("results", outputResults);

		int userStateInt = Search.getUserStateInt(username);
		request.setAttribute("userState", userStateInt);

		outputResults = Search.getUserFavorites(username);
		request.setAttribute("favs", outputResults);
		
		getServletContext().getRequestDispatcher("/results.jsp").forward(request,response);
	}
}
