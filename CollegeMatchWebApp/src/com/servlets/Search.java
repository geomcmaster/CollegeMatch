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

public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		String username = (String)request.getAttribute("user");
		
		int i = 0;
		String[] criteria = new String[5];
		for (i = 0; i < criteria.length; i++) {
			criteria[i] = request.getParameter("crit" + (i + 1) + "hid");
		}
		List<Condition> conditions = new ArrayList<Condition>();
		byte tablesToJoin = SchoolDAO.NONE;
		SchoolDAO db = new SchoolDAO();
		
		for (i = 0; i < criteria.length; i++) {
			String criterion = criteria[i];
			String type = valTyp(criterion);
			String colName = getColumnName(criterion);
			String opener = "crit" + (i + 1);
			String value = new String();
			String comparison = new String();
			CondVal cValue = null;
			CondType cType = null;
			List<Condition> OrGroup = new ArrayList<Condition>();
			switch(type) {
			case "distance":
				// TODO: API?
				
				break;
			case "citystate":
				String city = request.getParameter(opener + "text");
				int stateInt = Integer.parseInt(request.getParameter(opener + "sel1"));
				
				cValue = CondVal.createStrVal(city);
				conditions.add(new Condition(colName.split("|")[0], CondType.EQ, cValue));
				
				cValue = CondVal.createIntVal(stateInt);
				conditions.add(new Condition(colName.split("|")[1], CondType.EQ, cValue));
				break;
			case "region":
				cValue = CondVal.createStrVal(request.getParameter(opener + "sel3"));
				conditions.add(new Condition(colName,CondType.EQ,cValue));
				tablesToJoin |= SchoolDAO.REGION;
				break;
			case "id":
				value = request.getParameter(opener + "level");
				cValue = CondVal.createIntVal(Integer.parseInt(value));
				conditions.add(new Condition(colName,CondType.EQ,cValue));
				break;
			case "programs":
				value = request.getParameter(opener + "sel2");
				cValue = CondVal.createIntVal(Integer.parseInt(value));
				for (i = 0; i < 5; i++) {
					Condition cond = new Condition(colName.split("|")[i],CondType.EQ,cValue);
					OrGroup.add(cond);
				}
				conditions.add(new Condition("",CondType.OR_GROUP,CondVal.createORGroupVal(OrGroup)));
				break;
			case "string":
				cValue = CondVal.createStrVal("%" + request.getParameter(opener + "text") + "%");
				Condition cond1 = new Condition(colName.split("|")[0],CondType.LIKE,cValue);
				Condition cond2 = new Condition(colName.split("|")[1],CondType.LIKE,cValue);
				OrGroup.add(cond1);
				OrGroup.add(cond2);
				conditions.add(new Condition("",CondType.OR_GROUP,CondVal.createORGroupVal(OrGroup)));
				break;
			case "int":
				comparison = request.getParameter(opener + "comp");
				if (comparison == "bet") {
					int val1 = Integer.parseInt(request.getParameter(opener + "num1"));
					int val2 = Integer.parseInt(request.getParameter(opener + "num2"));
					cValue = CondVal.createIntRangeVal(val1, val2);
					cType = CondType.RANGE;
				} else {
					cValue = CondVal.createIntVal(Integer.parseInt(request.getParameter(opener + "num1")));
					if (comparison == "lt") {
						cType = CondType.LT;
					} else {
						cType = CondType.GT;
					}
				}
				conditions.add(new Condition(colName, cType, cValue));
				break;
			case "double":
				if (colName.contains("|")) {
					if (colName.split("|")[1] == "GENDER") {
						tablesToJoin |= SchoolDAO.GENDER;
					} else {
						tablesToJoin |= SchoolDAO.ETHNIC;
					}
					colName = colName.split("|")[0];
				}
				comparison = request.getParameter(opener + "comp");
				if (comparison == "bet") {
					double val1 = Double.parseDouble(request.getParameter(opener + "num1"));
					double val2 = Double.parseDouble(request.getParameter(opener + "num2"));
					cValue = CondVal.createDoubleRangeVal(val1, val2);
					cType = CondType.RANGE;
				} else {
					cValue = CondVal.createDoubleVal(Double.parseDouble(request.getParameter(opener + "num1")));
					if (comparison == "lt") {
						cType = CondType.LT;
					} else {
						cType = CondType.GT;
					}
				}
				conditions.add(new Condition(colName, cType, cValue));
				break;
			case "special":
				if (criterion == "favorites") {
					conditions.add(db.favsInOffers(username));
				} else {
					conditions.add(db.favsInTopFive(username));
				}
				break;
			case "boolean":
				cValue = CondVal.createIntVal(Integer.parseInt(request.getParameter(opener + "check")));
				conditions.add(new Condition(colName,CondType.EQ,cValue));
				break;
			}
		}
		
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
	
	private String valTyp(String criterion) {
		switch(criterion) {
		case "locationz":
			return "distance";
		case "locationcs":
			return "citystate";
		case "programs":
		case "level":
			return "id";
		case "name":
			return "string";
		case "cost":
		case "earnings":
		case "size":
		case "avginc":
		case "medinc":
		case "tuitionin":
		case "tuitionout":
		case "age":
			return "int";
		case "sat":
		case "act":
		case "admrate":
		case "firstgen":
		case "men":
		case "women":
		case "white":
		case "black":
		case "hispanic":
		case "asian":
		case "aian":
		case "nhpi":
		case "multi":
		case "nonres":
			return "double";
		case "online":
			return "boolean";
		case "favorites":
		case "fav5":
			return "special";
		case "region":
			return "region";
		}
		
		return "";
	}
	
	private String getColumnName(String criterion) {
		switch(criterion) {
		case "locationz":
			return "ZIP?";
		case "locationcs":
			return "location.city|location.state";
		case "programs":
			return School.PROG_1 + "|" + School.PROG_2 + "|" +
				School.PROG_3 + "|" + School.PROG_4 + "|" +
				School.PROG_5;
		case "level":
			return School.LEVEL;
		case "region":
			return "region_name";
		case "name":
			return School.NAME + "|" + School.ALIAS;
		case "cost":
			return School.AVG_COST;
		case "sat":
			return School.SAT_AVG;
		case "act":
			return School.ACT_AVG;
		case "earnings":
			return School.AVG_EARN;
		case "size":
			return School.STD_BODY_SIZE;
		case "admrate":
			return School.ADM_RATE;
		case "avginc":
			return School.AVG_FAM_INC;
		case "medinc":
			return School.MED_FAM_INC;
		case "tuitionin":
			return School.TUITION_IN;
		case "tuitionout":
			return School.TUITION_OUT;
		case "age":
			return School.AVG_AGE_ENTRY;
		case "firstgen":
			return School.FIRST_GEN;
		case "men":
			return "male|GENDER";
		case "women":
			return "female|GENDER";
		case "white":
			return "white|ETHNIC";
		case "black":
			return "black|ETHNIC";
		case "hispanic":
			return "hispanic|ETHNIC";
		case "asian":
			return "asian|ETHNIC";
		case "aian":
			return "american_indian_alaskan_native|ETHNIC";
		case "nhpi":
			return "native_hawaiian_pacific_islander|ETHNIC";
		case "multi":
			return "two_or_more|ETHNIC";
		case "nonres":
			return "nonresident|ETHNIC";
		case "online":
			return School.DIST_LEARNING;
	}
		return "";
	}
}
