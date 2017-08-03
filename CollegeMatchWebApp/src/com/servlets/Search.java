package com.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.CondType;
import main.java.CondVal;
import main.java.Condition;
import main.java.School;
import main.java.SchoolDAO;

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
				// TODO: finish
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
		
		// TODO: send in query with completed list of conditions, tablesToJoin
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
		case "locationcs":
			return "LOCATION?";
		case "programs":
			return "pop_prog_1|pop_prog_2|pop_prog_3|pop_prog_4|pop_prog_5";
		case "level":
			return "level";
		case "region":
			return "region_name";
		case "name":
			return "name|alias";
		case "cost":
			return "avg_cost";
		case "sat":
			return "SAT_avg";
		case "act":
			return "ACT_avg";
		case "earnings":
			return "avg_earnings_6_years_after_matriculation";
		case "size":
			return "std_bdy_sz";
		case "admrate":
			return "adm_rate";
		case "avginc":
			return "avg_fam_inc";
		case "medinc":
			return "med_fam_inc";
		case "tuitionin":
			return "tuition_and_fees_in";
		case "tuitionout":
			return "tuition_and_fees_out";
		case "age":
			return "avg_entry_age";
		case "firstgen":
			return "1_gen_std_share";
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
			return "dist_learning";
	}
		return "";
	}
}
