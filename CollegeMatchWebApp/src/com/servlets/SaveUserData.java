package com.servlets;

import java.io.IOException;
import main.java.*;
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
		
		String oldPw = request.getParameter("old_pw");
		String newPw = request.getParameter("new_pw");
		
		boolean validPw;
		if (oldPw.length() > 0) {
			validPw = db.verifyPassword(username, oldPw);
		} else {
			validPw = true;
		}
		if (!validPw) {
			response.sendRedirect("editmyuser?pwfail");
		} else {
			// save pw if appropriate
			if (oldPw.length() > 0) {
				db.updatePassword(username, newPw);
			}
			
			// save sat, act scores
			db.updateUser(username, SATscore, ACTscore);
			
			// save location
			db.modifyResidence(username, city, stateInt, Integer.parseInt(ZIPcode));
			
			// save favorite fields
			for (int i = 0; i < allFields.length; i++) {
				String[] field = allFields[i].split("[|]");
				int rank = Integer.parseInt(field[0]);
				int fieldId = Integer.parseInt(field[1]);
				db.modifyFavField(username, fieldId, rank);
			}
			response.sendRedirect("editmyuser");
		}
	}
}
