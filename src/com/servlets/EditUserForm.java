package com.servlets;

import java.io.IOException;
import main.java.*;
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
		
		request.setAttribute("sat", currentUser.getSatScore());
		request.setAttribute("act", currentUser.getActScore());
		Location loc = currentUser.getLocation();
		request.setAttribute("loczip", loc.getZip());
		request.setAttribute("loccity", loc.getCity());
		request.setAttribute("locstate", loc.getStateInt());
		List<FavoriteFieldOfStudy> favorites = currentUser.getFavoriteFieldsOfStudy();
		String[] fieldArray = new String[favorites.size()];
		for (int i = 0; i < favorites.size(); i++) {
			fieldArray[i] = favorites.get(i).getRank() + "|" + favorites.get(i).getFieldOfStudy();
		}
		request.setAttribute("fields", fieldArray);
		getServletContext().getRequestDispatcher("/userdata.jsp").forward(request,response);
	}
}
