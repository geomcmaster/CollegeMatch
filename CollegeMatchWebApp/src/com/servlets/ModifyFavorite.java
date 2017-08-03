package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import main.java.FavoriteSchool;
import main.java.School;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ModifyFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		String action = request.getParameter("modifyAction");
		int schoolId = Integer.parseInt(request.getParameter("modifyId"));
		String[] lastResults = request.getParameter("comboResults").split("^");
		String userState = request.getParameter("userState");
		
		UserDAO db = new UserDAO();
		
		if (action == "add") {
			db.addFavSchool(username, schoolId);
		} else if (action == "delete") {
			db.deleteFavSchool(username, schoolId);
		}
		
		List<FavoriteSchool> userFavs = db.getFavSchools(username);
		String[] outputResults = new String[userFavs.size()];
		// data format: id
		for (int i = 0; i < userFavs.size(); i++) {
			School curFav = userFavs.get(i).getSchool();
			int curId = 0; //TODO: fix when possible
			outputResults[i] = "" + curId;
		}
		
		request.setAttribute("favs", outputResults);
		request.setAttribute("results", lastResults);
		request.setAttribute("userState", userState);
		
		getServletContext().getRequestDispatcher("/results.jsp").forward(request,response);
	}
}
