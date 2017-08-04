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
import main.java.SortColumn;
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
			String paramName = "crit" + (i + 1) + "hid";
			criteria[i] = request.getParameter(paramName);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		byte tablesToJoin = SchoolDAO.NONE;
		SchoolDAO db = new SchoolDAO();
		String sortBy = request.getParameter("sortby");
		String sortDir = request.getParameter("sortdir");
		boolean isAsc;
		if (sortDir.equals("asc")) {
			isAsc = true;
		} else {
			isAsc = false;
		}
		
		for (i = 0; i < criteria.length; i++) {
			String criterion = criteria[i];
			if (criterion == null || criterion.equals("")) {
				continue;
			}
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
				conditions.add(new Condition(colName.split("[|]")[0], CondType.EQ, cValue));
				
				cValue = CondVal.createIntVal(stateInt);
				conditions.add(new Condition(colName.split("[|]")[1], CondType.EQ, cValue));
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
				for (i = 0; i < 5; i++) {
					cValue = CondVal.createIntVal(Integer.parseInt(value));
					Condition cond = new Condition(colName.split("[|]")[i],CondType.EQ,cValue);
					OrGroup.add(cond);
				}
				conditions.add(new Condition("",CondType.OR_GROUP,CondVal.createORGroupVal(OrGroup)));
				break;
			case "string":
				cValue = CondVal.createStrVal("%" + request.getParameter(opener + "text") + "%");
				Condition cond1 = new Condition(colName.split("[|]")[0],CondType.LIKE,cValue);
				cValue = CondVal.createStrVal("%" + request.getParameter(opener + "text") + "%");
				Condition cond2 = new Condition(colName.split("[|]")[1],CondType.LIKE,cValue);
				OrGroup.add(cond1);
				OrGroup.add(cond2);
				conditions.add(new Condition("",CondType.OR_GROUP,CondVal.createORGroupVal(OrGroup)));
				break;
			case "int":
				comparison = request.getParameter(opener + "comp");
				
				conditions.add(intCond(colName, comparison, opener, request));
				
				if (comparison.equals("lt")) {
					cType = CondType.NE;
					cValue = CondVal.createIntVal(0);
					conditions.add(new Condition(colName, cType, cValue));
				}
				break;
			case "double":
				if (colName.contains("|")) {
					if (colName.split("[|]")[1].equals("GENDER")) {
						tablesToJoin |= SchoolDAO.GENDER;
					} else {
						tablesToJoin |= SchoolDAO.ETHNIC;
					}
					colName = colName.split("[|]")[0];
				}
				comparison = request.getParameter(opener + "comp");
				
				conditions.add(doubleCond(colName, comparison, opener, request));
				if (comparison.equals("lt")) {
					cType = CondType.NE;
					cValue = CondVal.createDoubleVal(0);
					conditions.add(new Condition(colName, cType, cValue));
				}
				break;
			case "special":
				if (criterion.equals("favorites")) {
					if (request.getParameter(opener + "check").equals("1")) {
						conditions.add(db.favsInOffers(username));
					}
				} else if (criterion.equals("fav5")) {
					if (request.getParameter(opener + "check").equals("1")) {
						conditions.add(db.favsInTopFive(username));
					}
				} else {
					comparison = request.getParameter(opener + "comp");
					if (request.getParameter(opener + "check") != null) {
						if (comparison.equals("lt")) {
							cType = CondType.LT;
						} else {
							cType = CondType.GT;
						}
						Condition cond = null;
						if (criterion.equals("sat")) {
							cond = db.compareMySAT(cType, username);
						} else {
							cond = db.compareMyACT(cType, username);
						}
						conditions.add(cond);
					} else {
						conditions.add(doubleCond(colName, comparison, opener, request));
						
					}
					if (comparison.equals("lt")) {
						cType = CondType.NE;
						cValue = CondVal.createDoubleVal(0);
						conditions.add(new Condition(colName, cType, cValue));
					}
				}
				break;
			case "boolean":
				cValue = CondVal.createIntVal(Integer.parseInt(request.getParameter(opener + "check")));
				conditions.add(new Condition(colName,CondType.EQ,cValue));
				break;
			}
		}
		
		List<School> results = null;
		if (sortBy.equals("") || sortBy == null) {
			results = db.getSchools(conditions, tablesToJoin);			
		} else {
			List<SortColumn> sorts = new ArrayList<SortColumn>();
			switch(sortBy) {
			case "name":
				sorts.add(new SortColumn(School.NAME, isAsc));
				break;
			case "cst":
				sorts.add(new SortColumn(School.L_STATE_STRING, isAsc));
				sorts.add(new SortColumn(School.L_CITY, isAsc));
				break;
			case "adm":
				sorts.add(new SortColumn(School.ADM_RATE, isAsc));
				break;
			case "sat":
				sorts.add(new SortColumn(School.SAT_AVG, isAsc));
				break;
			case "act":
				sorts.add(new SortColumn(School.ACT_AVG, isAsc));
				break;
			case "ist":
				sorts.add(new SortColumn(School.TUITION_IN, isAsc));
				break;
			case "oost":
				sorts.add(new SortColumn(School.TUITION_OUT, isAsc));
				break;
			}
			results = db.getSchools(conditions, tablesToJoin, sorts);
		}
		String[] outputResults = new String[results.size()];
		// data format: id|name|url|admrate|in-tuit|out-tuit|city|stateInt|stateAbbr|satavg|actavg
		for (i = 0; i < results.size(); i++) {
			School curSch = results.get(i);
			String[] allValues = getSchoolDetails(curSch);
			outputResults[i] = String.join("|", allValues);
		}
		
		request.setAttribute("results", outputResults);
		
		outputResults = getUserFavorites(username);
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
		case "sat":
		case "act":
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
			return School.L_CITY + "|" + School.L_STATE;
		case "programs":
			return School.PROG_1 + "|" + School.PROG_2 + "|" +
				School.PROG_3 + "|" + School.PROG_4 + "|" +
				School.PROG_5;
		case "level":
			return School.LEVEL;
		case "region":
			return School.R_REGION;
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
			return School.GD_MALE + "|GENDER";
		case "women":
			return School.GD_FEMALE + "|GENDER";
		case "white":
			return School.ED_WHITE + "|ETHNIC";
		case "black":
			return School.ED_BLACK + "|ETHNIC";
		case "hispanic":
			return School.ED_HISPANIC + "|ETHNIC";
		case "asian":
			return School.ED_ASIAN + "|ETHNIC";
		case "aian":
			return School.ED_AM_IND + "|ETHNIC";
		case "nhpi":
			return School.ED_HAW_PAC_ISL + "|ETHNIC";
		case "multi":
			return School.ED_TWO_OR_MORE + "|ETHNIC";
		case "nonres":
			return School.ED_NONRES + "|ETHNIC";
		case "online":
			return School.DIST_LEARNING;
	}
		return "";
	}

	public static String[] getSchoolDetails(School sch) {
		int curId = 0;
		String curName = new String();
		String curUrl = new String();
		double curAdmRate = 0;
		int tuitionIn = 0;
		int tuitionOut = 0;
		String curCity = new String();
		int curStateInt = 0;
		String curStateAbbr = new String();
		double curSat = 0;
		double curAct = 0;
		
		// Get ID
		if (sch.isIdNotNull()) {
			curId = sch.getId();
		}
		
		// Get Name
		if (sch.isNameNotNull()) {
			curName = sch.getName();
		}
		
		// Get URL
		if (sch.isWebsiteNotNull()) {
			curUrl = sch.getWebsite();
		}
		
		// Get Admission Rate
		if (sch.isAdmissionRateNotNull()) {
			curAdmRate = sch.getAdmissionRate();
		}
		
		// Get In-State Tuition
		if (sch.isTuitionInNotNull()) {
			tuitionIn = sch.getTuitionIn();
		}
		
		// Get Out-of-State Tuition
		if (sch.isTuitionOutNotNull()) {
			tuitionOut = sch.getTuitionOut();
		}
		
		// Get location
		if (sch.isLocationNotNull()) {
			Location curLoc = sch.getLocation();
			if (curLoc.isCityNotNull()) {
				curCity = curLoc.getCity();
			}
			if (curLoc.isStateIntNotNull()) {
				curStateInt = curLoc.getStateInt();
			}
			if (curLoc.isStateStrNotNull()) {
				curStateAbbr = curLoc.getStateAbbreviation();
			}
		}
		
		// Get SAT
		if (sch.isSatAvgNotNull()) {
			curSat = sch.getSatAvg();
		}
		
		if (sch.isActAvgNotNull()) {
			curAct = sch.getActAvg();
		}
		
		String[] allValues = {" " + curId, " " + curName, " " + curUrl,
				" " + curAdmRate, " " + tuitionIn, " " + tuitionOut,
				" " + curCity, " " + curStateInt, " " + curStateAbbr,
				" " + curSat, " " + curAct};
		
		return allValues;
	}

	public static String[] getUserFavorites(String username) {
		UserDAO userDb = new UserDAO();
		List<FavoriteSchool> userFavs = userDb.getFavSchools(username);
		String[] outputResults = new String[userFavs.size()];
		// data format: id
		for (int i = 0; i < userFavs.size(); i++) {
			School curFav = userFavs.get(i).getSchool();
			int curId = 0;
			if (curFav.isIdNotNull()) {
				curId = curFav.getId();
			}
			outputResults[i] = "" + curId;
		}
		return outputResults;
	}

	private Condition intCond(String colName, String comparison, String opener, HttpServletRequest request) {
		CondType cType = null;
		CondVal cValue = null;
		if (comparison.equals("bet")) {
			int val1 = Integer.parseInt(request.getParameter(opener + "num1"));
			int val2 = Integer.parseInt(request.getParameter(opener + "num2"));
			cValue = CondVal.createIntRangeVal(val1, val2);
			cType = CondType.RANGE;
		} else {
			cValue = CondVal.createIntVal(Integer.parseInt(request.getParameter(opener + "num1")));
			if (comparison.equals("lt")) {
				cType = CondType.LT;
			} else {
				cType = CondType.GT;
			}
		}
		return new Condition(colName, cType, cValue);
	}
	
	private Condition doubleCond(String colName, String comparison, String opener, HttpServletRequest request) {
		CondType cType = null;
		CondVal cValue = null;
		if (comparison.equals("bet")) {
			double val1 = Double.parseDouble(request.getParameter(opener + "num1"));
			double val2 = Double.parseDouble(request.getParameter(opener + "num2"));
			cValue = CondVal.createDoubleRangeVal(val1, val2);
			cType = CondType.RANGE;
		} else {
			cValue = CondVal.createDoubleVal(Double.parseDouble(request.getParameter(opener + "num1")));
			if (comparison.equals("lt")) {
				cType = CondType.LT;
			} else {
				cType = CondType.GT;
			}
		}
		return new Condition(colName, cType, cValue);
	}
}
