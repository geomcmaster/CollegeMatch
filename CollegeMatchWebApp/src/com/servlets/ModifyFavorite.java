package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import main.java.FavoriteSchool;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

public class ModifyFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		String[] lastResults = new String[0];
		String userState = new String();
		boolean fromSearch = false;
		
		String action = request.getParameter("modifyAction");
		int schoolId = Integer.parseInt(request.getParameter("modifyId"));
		String searchResults = request.getParameter("searchresults");
		if (searchResults != null) {
			lastResults = searchResults.split("\\^");
			userState = request.getParameter("userState");
			fromSearch = true;
		}
		
		UserDAO db = new UserDAO();
		
		List<FavoriteSchool> updateFavs = new ArrayList<FavoriteSchool>();
		
		if (action.equals("add")) {
			updateFavs = db.getFavSchools(username);
			int newrank = updateFavs.size() + 1;
			db.addFavSchool(username, schoolId);
			db.updateFavSchool(username, schoolId, newrank, "", 0, 0, 0);
		} else if (action.equals("delete")) {
			updateFavs = db.getFavSchools(username);
			db.deleteFavSchool(username, schoolId);
			int oldrank = 0;
			for (int i = 0; i < updateFavs.size(); i++) {
				int updateId = updateFavs.get(i).getSchool().getId();
				if (updateId == schoolId) {
					oldrank = updateFavs.get(i).getRank();
					updateFavs.remove(i);
					break;
				}
			}
			for (int i = 0; i < updateFavs.size(); i++) {
				FavoriteSchool fs = updateFavs.get(i);
				int updateId = fs.getSchool().getId();
				if (fs.getRank() > oldrank) {
					String app = fs.getStatus();
					int fin = fs.getFinancialAid();
					int loan = fs.getLoan();
					int merit = fs.getMerit();
					int newrank = fs.getRank() - 1;
					db.updateFavSchool(username, updateId, newrank, app, fin, loan, merit);
				}
			}
		}
		
		if (fromSearch) {
			String[] outputResults = Search.getUserFavorites(username);
			
			request.setAttribute("favs", outputResults);
			request.setAttribute("results", lastResults);
			request.setAttribute("userState", userState);
			
			getServletContext().getRequestDispatcher("/results.jsp").forward(request,response);
		} else {
			response.sendRedirect("myfavorites");
		}
	}
}
