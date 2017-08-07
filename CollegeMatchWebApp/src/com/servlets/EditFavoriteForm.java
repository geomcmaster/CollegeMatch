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

public class EditFavoriteForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		int schoolId = Integer.parseInt(request.getParameter("id")); 
		int schoolCost = 0;
		String schoolName = new String();
		
		UserDAO db = new UserDAO();
		FavoriteSchool editFav = null;

		List<FavoriteSchool> allFavs = db.getFavSchools(username);
		for (int i = 0; i < allFavs.size(); i++) {
			FavoriteSchool fav = allFavs.get(i);
			if (fav.isSchoolNotNull()) {
				School sch = fav.getSchool();
				if (sch.isIdNotNull()) {
					int curId = sch.getId();
					if (curId == schoolId) {
						editFav = fav;
						if (sch.isAvgCostNotNull()) {
							schoolCost = sch.getAvgCost();
						}
						if (sch.isNameNotNull()) {
							schoolName = sch.getName();
						}
						break;
					}
				}
			}
		}
		
		if (editFav == null) {
			// redirect and tell user that there was an error editing this favorite; make sure is favorite and try again
		} else {
			String[] favDetails = ViewFavorites.getFavoriteDetails(editFav);
			
			request.setAttribute("favDetails", favDetails);
			request.setAttribute("cost", schoolCost);
			request.setAttribute("fullName", schoolName);
			request.setAttribute("id", schoolId);

			getServletContext().getRequestDispatcher("/editfavschool.jsp").forward(request,response);
		}
	}
}
