package com.servlets;

import java.io.IOException;
import main.java.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		UserDAO db = new UserDAO(); // open connection
		
		int SATscore = Integer.parseInt(request.getParameter("edit_sat"));
		int ACTscore = Integer.parseInt(request.getParameter("edit_act"));
		
		String city = request.getParameter("edit_loc_city");
		int stateInt = Integer.parseInt(request.getParameter("edit_loc_state"));
		String ZIPcode = request.getParameter("edit_loc_zip");
		
		String strAllFields = request.getParameter("hidField");
		String[] allFields = strAllFields.split("[,]");
		
		String strDeleteFields = request.getParameter("hidDelete");
		String[] deleteFields = strDeleteFields.split("[,]");
		
		// save sat, act scores
		db.updateUser(username, SATscore, ACTscore);
		
		// save location
		if (city.length() > 0 && stateInt != 0 && ZIPcode.length() > 0) {
			db.modifyResidence(username, city, stateInt, Integer.parseInt(ZIPcode));
		}
		
		// save favorite fields
		if (allFields[0].length() > 0) {
			for (int i = 0; i < allFields.length; i++) {
				String[] field = allFields[i].split("[|]");
				int rank = Integer.parseInt(field[0]);
				int fieldId = Integer.parseInt(field[1]);
				db.modifyFavField(username, fieldId, rank);
			}
		}
		
		if (deleteFields[0].length() > 0) {
			for (int i = 0; i < deleteFields.length; i++) {
				db.deleteFavField(username, Integer.parseInt(deleteFields[i]));
			}
		}
		
		response.sendRedirect("editmyuser?success");
	}
}
