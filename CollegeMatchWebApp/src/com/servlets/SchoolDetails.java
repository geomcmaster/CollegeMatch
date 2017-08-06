package com.servlets;

import java.io.IOException;
import main.java.SchoolDAO;
import main.java.School;
import main.java.Location;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SchoolDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/cookie").include(request,response);
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		SchoolDAO db = new SchoolDAO();
		School sch = db.getSingleSchoolViewInfo(id);
		
		// Initialize all variables in case something in the School object is NULL
		String name = new String();
		String url = new String();
		String city = new String();
		String stateAbbr = new String();
		String region = new String();
		int cost = 0;
		double sat25 = 0;
		double satAvg = 0;
		double sat75 = 0;
		double act25 = 0;
		double actAvg = 0;
		double act75 = 0;
		double satE25 = 0;
		double satEAvg = 0;
		double satE75 = 0;
		double satM25 = 0;
		double satMAvg = 0;
		double satM75 = 0;
		double satW25 = 0;
		double satWAvg = 0;
		double satW75 = 0;
		double actE25 = 0;
		double actEAvg = 0;
		double actE75 = 0;
		double actM25 = 0;
		double actMAvg = 0;
		double actM75 = 0;
		double actW25 = 0;
		double actWAvg = 0;
		double actW75 = 0;
		int earnings = 0;
		int size = 0;
		String prog_1 = new String();
		String prog_2 = new String();
		String prog_3 = new String();
		String prog_4 = new String();
		String prog_5 = new String();
		double admRate = 0;
		int avgInc = 0;
		int medInc = 0;
		int tuitionIn = 0;
		int tuitionOut = 0;
		double medDebt = 0;
		int age = 0;
		double firstGen = 0;
		String level = new String();
		boolean distOnly = false;
		double male = 0;
		double female = 0;
		double white = 0;
		double black = 0;
		double hispanic = 0;
		double asian = 0;
		double aian = 0;
		double nhpi = 0;
		double multi = 0;
		double nonres = 0;
		double unknown = 0;
		
		if (sch.isNameNotNull()) {
			name = sch.getName();
		}
		
		if (sch.isWebsiteNotNull()) {
			url = sch.getWebsite();
		}
		
		if (sch.isLocationNotNull()) {
			Location loc = sch.getLocation();
			if (loc.isCityNotNull()) {
				city = loc.getCity();
			}
			if (loc.isStateStrNotNull()) {
				stateAbbr = loc.getStateAbbreviation();
			}
			if (loc.isRegionNotNull()) {
				region = loc.getRegion();
			}
		}
		
		if (sch.isAvgCostNotNull()) {
			cost = sch.getAvgCost();
		}
		
		if (sch.isSat25NotNull()) {
			sat25 = sch.getSat25();
		}
		
		if (sch.isSatAvgNotNull()) {
			satAvg = sch.getSatAvg();
		}
		
		if (sch.isSat75NotNull()) {
			sat75 = sch.getSat75();
		}
		
		/*
		 * if (sch.isSatE25NotNull()) {
		 * 	satE25 = sch.getSatE25();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satEAvg = sch.getSatEAvg();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satE75 = sch.getSatE75();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satM25 = sch.getSatM25();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satMAvg = sch.getSatMAvg();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satM75 = sch.getSatM75();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satW25 = sch.getSatW25();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satWAvg = sch.getSatWAvg();
		 * }
		 * if (sch.isSatE25NotNull()) {
		 * 	satW75 = sch.getSatW75();
		 * }
		 */
		
		if (sch.isAct25NotNull()) {
			act25 = sch.getAct25();
		}
		
		if (sch.isActAvgNotNull()) {
			actAvg = sch.getActAvg();
		}
		
		if (sch.isAct75NotNull()) {
			act75 = sch.getAct75();
		}
		
		/*
		 * if (sch.isActE25NotNull()) {
		 * 	actE25 = sch.getActE25();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actEAvg = sch.getActEAvg();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actE75 = sch.getActE75();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actM25 = sch.getActM25();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actMAvg = sch.getActMAvg();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actM75 = sch.getActM75();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actW25 = sch.getActW25();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actWAvg = sch.getActWAvg();
		 * }
		 * if (sch.isActE25NotNull()) {
		 * 	actW75 = sch.getActW75();
		 * }
		 */
		
		if (sch.isAvgEarningsNotNull()) {
			earnings = sch.getAvgEarnings();
		}
		
		if (sch.isStdBodySzNotNull()) {
			size = sch.getStdBodySz();
		}
		
		if (sch.isPopProg1NotNull()) {
			prog_1 = sch.getPopProg1();
		}

		if (sch.isPopProg2NotNull()) {
			prog_2 = sch.getPopProg2();
		}
		
		if (sch.isPopProg3NotNull()) {
			prog_3 = sch.getPopProg3();
		}
		
		if (sch.isPopProg4NotNull()) {
			prog_4 = sch.getPopProg4();
		}
		
		if (sch.isPopProg5NotNull()) {
			prog_5 = sch.getPopProg5();
		}
		
		if (sch.isAdmissionRateNotNull()) {
			admRate = sch.getAdmissionRate();
		}
		
		if (sch.isAvgFamIncomeNotNull()) {
			avgInc = sch.getAvgFamilyIncome();
		}
		
		if (sch.isMedFamIncomeNotNull()) {
			medInc = sch.getMedFamIncome();
		}
		
		if (sch.isTuitionInNotNull()) {
			tuitionIn = sch.getTuitionIn();
		}
		
		if (sch.isTuitionOutNotNull()) {
			tuitionOut = sch.getTuitionOut();
		}
		
		if (sch.isMedDebtNotNull()) {
			medDebt = sch.getMedDebt();
		}
		
		if (sch.isAvgAgeNotNull()) {
			age = sch.getAvgAge();
		}
		
		if (sch.isFirstGenStudentShareNotNull()) {
			firstGen = sch.getFirstGenStudentShare();
		}
		
		if (sch.isLevelNull()) {
			level = sch.getLevel();
		}

		if (sch.isDistanceLearningNotNull()) {
			distOnly = sch.getDistanceLearning() > 0;
		}
		
		if (sch.isMaleShareNotNull()) {
			male = sch.getMaleShare();
		}
		
		if (sch.isFemaleShareNotNull()) {
			female = sch.getFemaleShare();
		}
		
		if (sch.isWhiteNull()) {
			white = sch.getWhite();
		}
		
		if (sch.isBlackNull()) {
			black = sch.getBlack();
		}
		
		if (sch.isHispanicNull()) {
			hispanic = sch.getHispanic();
		}
		
		if (sch.isAsianNull()) {
			asian = sch.getAsian();
		}
		
		if (sch.isAmericanIndianAlaskanNativeNull()) {
			aian = sch.getAmerican_indian_alaskan_native();
		}
		
		if (sch.isNativeHawaiianPacificIslanderNull()) {
			nhpi = sch.getNative_hawaiian_pacific_islander();
		}
		
		if (sch.isMultiethnicNull()) {
			multi = sch.getMultiethnic();
		}
		
		if (sch.isNonresidentNull()) {
			nonres = sch.getNonresident();
		}
		
		if (sch.isUnknownEthnicityNull()) {
			unknown = sch.getUnknown_ethnicity();
		}
		
		request.setAttribute("name", name);
		request.setAttribute("url", url);
		request.setAttribute("city", city);
		request.setAttribute("state", stateAbbr);
		request.setAttribute("region", region);
		request.setAttribute("cost", cost);
		request.setAttribute("sat25", sat25);
		request.setAttribute("satAvg", satAvg);
		request.setAttribute("sat75", sat75);
		request.setAttribute("satE25", satE25);
		request.setAttribute("satEAvg", satEAvg);
		request.setAttribute("satE75", satE75);
		request.setAttribute("satM25", satM25);
		request.setAttribute("satMAvg", satMAvg);
		request.setAttribute("satM75", satM75);
		request.setAttribute("satW25", satW25);
		request.setAttribute("satWAvg", satWAvg);
		request.setAttribute("satW75", satW75);
		request.setAttribute("act25", act25);
		request.setAttribute("actAvg", actAvg);
		request.setAttribute("act75", act75);
		request.setAttribute("actE25", actE25);
		request.setAttribute("actEAvg", actEAvg);
		request.setAttribute("actE75", actE75);
		request.setAttribute("actM25", actM25);
		request.setAttribute("actMAvg", actMAvg);
		request.setAttribute("actM75", actM75);
		request.setAttribute("actW25", actW25);
		request.setAttribute("actWAvg", actWAvg);
		request.setAttribute("actW75", actW75);
		request.setAttribute("earnings", earnings);
		request.setAttribute("size", size);
		request.setAttribute("prog_1", prog_1);
		request.setAttribute("prog_2", prog_2);
		request.setAttribute("prog_3", prog_3);
		request.setAttribute("prog_4", prog_4);
		request.setAttribute("prog_5", prog_5);
		request.setAttribute("admRate", admRate);
		request.setAttribute("avgInc", avgInc);
		request.setAttribute("medInc", medInc);
		request.setAttribute("tuitionIn", tuitionIn);
		request.setAttribute("tuitionOut", tuitionOut);
		request.setAttribute("medDebt", medDebt);
		request.setAttribute("age", age);
		request.setAttribute("firstGen", firstGen);
		request.setAttribute("level", level);
		if (distOnly) {
			request.setAttribute("distOnly", "Yes");
		} else {
			request.setAttribute("distOnly", "No");
		}
		request.setAttribute("male", male);
		request.setAttribute("female", female);
		request.setAttribute("white", white);
		request.setAttribute("black", black);
		request.setAttribute("hispanic", hispanic);
		request.setAttribute("asian", asian);
		request.setAttribute("aian", aian);
		request.setAttribute("nhpi", nhpi);
		request.setAttribute("multi", multi);
		request.setAttribute("nonres", nonres);
		request.setAttribute("unknown", unknown);
		
		getServletContext().getRequestDispatcher("/schooldetails.jsp").forward(request,response);
	}
}
