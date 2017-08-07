package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import main.java.User;
import main.java.Location;
import main.java.FavoriteFieldOfStudy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditUserForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		UserDAO db = new UserDAO();
		User currentUser = db.getUser(username);
		
		double SAT = 0;
		double ACT = 0;
		int ZIP = 0;
		int stateInt = 0;
		String city = new String();
		String[] fieldArray = new String[0];
		
		if (currentUser.isSatScoreNotNull()) {
			SAT = currentUser.getSatScore();
		}
		if (currentUser.isActScoreNotNull()) {
			ACT = currentUser.getActScore();
		}
		if (currentUser.isLocationNotNull()) {
			Location loc = currentUser.getLocation();
			if (loc.isCityNotNull()) {
				city = loc.getCity();
			}
			if (loc.isStateIntNotNull()) {
				stateInt = loc.getStateInt();
			}
			if (loc.isZipNotNull()) {
				ZIP = loc.getZip();
			}
		} else {
			Location loc = db.getResidence(username);
			if (loc.isCityNotNull()) {
				city = loc.getCity();
			}
			if (loc.isStateIntNotNull()) {
				stateInt = loc.getStateInt();
			}
			if (loc.isZipNotNull()) {
				ZIP = loc.getZip();
			}
		}
		if (currentUser.isFavoriteFieldsOfStudyNotNull()) {
			List<FavoriteFieldOfStudy> favorites = currentUser.getFavoriteFieldsOfStudy();
			fieldArray = new String[favorites.size()];
			for (int i = 0; i < favorites.size(); i++) {
				if (favorites.get(i).isRankNotNull() && favorites.get(i).isFieldOfStudyNotNull()) {
					fieldArray[i] = favorites.get(i).getRank() + "|" + favorites.get(i).getFieldOfStudy();
				}
			}
		} else {
			List<FavoriteFieldOfStudy> favorites = db.getFavFields(username);
			fieldArray = new String[favorites.size()];
			for (int i = 0; i < favorites.size(); i++) {
				if (favorites.get(i).isRankNotNull() && favorites.get(i).isFieldOfStudyNotNull()) {
					fieldArray[i] = favorites.get(i).getRank() + "|" + favorites.get(i).getFieldOfStudy();
				}
			}
		}
		
		request.setAttribute("sat", SAT);
		request.setAttribute("act", ACT);
		request.setAttribute("loczip", ZIP);
		request.setAttribute("loccity", city);
		request.setAttribute("locstate", stateInt);
		request.setAttribute("fields", fieldArray);
		request.setAttribute("user",username);
		getServletContext().getRequestDispatcher("/userdata.jsp").forward(request,response);
	}
}
