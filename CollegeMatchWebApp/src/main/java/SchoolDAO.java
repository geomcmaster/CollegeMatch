package main.java;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Data Access Object for school-related queries
 * 
 * @author Geoff
 *
 */
public class SchoolDAO {
	private DBUtil dbUtil;
	//JOIN tables
	public static final byte NONE = 0x0;
	public static final byte GENDER = 0x1;
	public static final byte ETHNIC = 0x2;
	public static final byte REGION = 0x4;
	
	public SchoolDAO() {
		dbUtil = new DBUtil();
	}
	
	/**
	 * Performs query based on user search.
	 * 
	 * @param conditions The list of conditions to use in search
	 * @param tablesToJoin Bitmap of tables to join on.
	 * It is currently assumed that school_loc and location tables will always be joined to retrieve location info.
	 * @return A list of school objects returned by the query
	 */
	public List<School> getSchools(List<Condition> conditions, byte tablesToJoin) {
		
		StringBuilder queryBuilder = selectAndJoin(tablesToJoin);	//SELECT ... FROM ... JOIN ... ON etc.
		
		//BUILD WHERE CLAUSE
		queryBuilder.append(" WHERE");
		ListIterator<Condition> itr = conditions.listIterator();
		Index index = new Index();	//index in PreparedStatement
		//first condition not prefaced with AND
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" ");
				queryBuilder.append(buildConditionString(c, index));
				break;
			}
		}
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" AND ");
				queryBuilder.append(buildConditionString(c, index));
			}
		}

		return processResults(queryBuilder, conditions);
	}
	
	/**
	 * Performs query based on user search and orders by given columns.
	 * 
	 * @param conditions The list of conditions to use in search
	 * @param tablesToJoin Bitmap of tables to join on.
	 * It is currently assumed that school_loc and location tables will always be joined to retrieve location info.
	 * @param columnsToSort List of columns to sort by -- includes column name and ASC/DESC
	 * @return A list of school objects returned by the query
	 */
	public List<School> getSchools(List<Condition> conditions, byte tablesToJoin, List<SortColumn> columnsToSort) {
		
		StringBuilder queryBuilder = selectAndJoin(tablesToJoin);	//SELECT ... FROM ... JOIN ... ON etc.
		
		//BUILD WHERE CLAUSE
		queryBuilder.append(" WHERE");
		ListIterator<Condition> itr = conditions.listIterator();
		Index index = new Index();	//index in PreparedStatement
		//first condition not prefaced with AND
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" ");
				queryBuilder.append(buildConditionString(c, index));
				break;
			}
		}
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" AND ");
				queryBuilder.append(buildConditionString(c, index));
			}
		}
		
		//add ORDER BY
		queryBuilder.append(" ORDER BY ");
		ListIterator<SortColumn> sortItr = columnsToSort.listIterator();
		//first condition not prefaced with ,
		if (sortItr.hasNext()) {
			SortColumn c = sortItr.next();
			queryBuilder.append(c.getColumnName());
			queryBuilder.append(" ");
			queryBuilder.append(c.isAscending() ? "ASC" : "DESC");
		}
		while (sortItr.hasNext()) {
			SortColumn c = sortItr.next();
			queryBuilder.append(", ");
			queryBuilder.append(c.getColumnName());
			queryBuilder.append(" ");
			queryBuilder.append(c.isAscending() ? "ASC" : "DESC");
		}
		
		return processResults(queryBuilder, conditions);
	}
	
	private String buildConditionString(Condition c, Index i) {
		
		CondVal val = c.getValue();
		if (val.getType() == ValType.SINGLE_STRING_SUBQUERY) {
			return buildSingleStringSubqueryConditionString(c, i);
		} else if (val.getType() == ValType.OR_GROUP) {
			return buildORGroupConditionString(c, i).toString();
		}
		String condStr = c.getColumnName();
		switch (c.getConditionType()) {
			case RANGE:	condStr += " BETWEEN ? AND ?";
						val.setIndexOfMin(i.getAndIncrement());
						val.setIndexOfMax(i.getAndIncrement());
						break;
			case EQ:	condStr += " = ?";
						val.setIndex(i.getAndIncrement());
						break;
			case GT:	condStr += " > ?";
						val.setIndex(i.getAndIncrement());
						break;
			case GE:	condStr += " >= ?";
						val.setIndex(i.getAndIncrement());
						break;
			case LT:	condStr += " < ?";
						val.setIndex(i.getAndIncrement());
						break;
			case LE:	condStr += " <= ?";
						val.setIndex(i.getAndIncrement());
						break;
			case NE:	condStr += " <> ?";
						val.setIndex(i.getAndIncrement());
						break;
			case LIKE:	condStr += " LIKE ?";
						val.setIndex(i.getAndIncrement());
						break;
			case IN: condStr += " IN ?";
						val.setIndex(i.getAndIncrement());
						break;
			case REVERSE_IN:
						condStr = "? IN " + condStr + "";
						val.setIndex(i.getAndIncrement());
						break;
		}
		return condStr;
	}
	
	private String buildSingleStringSubqueryConditionString(Condition c, Index i) {
		String condStr = c.getColumnName();
		CondVal val = c.getValue();
		switch (c.getConditionType()) {
			case RANGE:	throw new RuntimeException("Subquery condition does not support range");
			case EQ:	condStr += " = " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case GT:	condStr += " > " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case GE:	condStr += " >= " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case LT:	condStr += " < " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case LE:	condStr += " <= " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case NE:	condStr += " <> " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case LIKE:	condStr += " LIKE " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
			case IN: condStr += " IN " + val.getSubQuery();
						val.setIndex(i.getAndIncrement());
						break;
		}
		return condStr;
	}
	
	private StringBuilder buildORGroupConditionString(Condition cond, Index i) {
		StringBuilder queryBuilder = new StringBuilder(" (");
		List<Condition> conditions = cond.getValue().getOrConditions();
		ListIterator<Condition> itr = conditions.listIterator();
		//first condition not prefaced with OR
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" ");
				queryBuilder.append(buildConditionString(c, i));
				break;
			}
		}
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" OR ");
				queryBuilder.append(buildConditionString(c, i));
			}
		}
		queryBuilder.append(")");
		return queryBuilder;
	}
	
	/**
	 * 
	 * @param tablesToJoin Bitmap of tables to JOIN
	 * @return A StringBuilder for SELECT, FROM and JOIN clauses
	 */
	private StringBuilder selectAndJoin(byte tablesToJoin) {
		String baseQuery = 
				"SELECT school.ID AS ID, school.name AS name, school.url AS url, school.tuition_and_fees_out AS outOfState, "
				+ "school.tuition_and_fees_in AS inState, location.city AS city, location.state_string AS stateStr, school.SAT_avg AS SAT_avg,"
				+ "school.ACT_avg AS ACT_avg, school.adm_rate AS adm_rate FROM school";
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		//JOINS
		queryBuilder.append(" JOIN school_loc ON school_loc.school_ID = school.ID "
				+ "JOIN location ON school_loc.loc_ID = location.ID");
		if ((tablesToJoin & GENDER) == GENDER) {
			queryBuilder.append(" JOIN GenderDemographics ON school.GenderDemographics_ID = GenderDemographics.ID");
		}
		if ((tablesToJoin & ETHNIC) == ETHNIC) {
			queryBuilder.append(" JOIN EthnicDemographics ON school.EthnicDemographics_ID = EthnicDemographics.ID");
		}
		if ((tablesToJoin & REGION) == REGION) {
			queryBuilder.append(" JOIN region ON location.state = region.state");
		}
		
		return queryBuilder;
	}
	
	/**
	 * Creates a condition to select schools that have one of a user's favorite fields in their top 5 programs
	 * 
	 * @param userName
	 * @return A condition to select schools that have one of a user's favorite fields in their top 5 programs
	 */
	public Condition favsInTopFive(String userName) {
		List<Condition> conditions = new LinkedList<Condition>();
		String subQuery = "(SELECT field_ID FROM favoriteFieldsOfStudy WHERE std_ID=?)";
		
		//conditions for each of the top 5 fields
		//school.pop_prog_x IN SELECT field_ID FROM favoriteFieldsOfStudy WHERE std_ID=?
		CondVal cval1 = CondVal.createSingleStringSubQueryVal(subQuery, userName);
		Condition c1 = new Condition("school.pop_prog_1", CondType.IN, cval1);
		conditions.add(c1);
		
		CondVal cval2 = CondVal.createSingleStringSubQueryVal(subQuery, userName);
		Condition c2 = new Condition("school.pop_prog_2", CondType.IN, cval2);
		conditions.add(c2);
		
		CondVal cval3 = CondVal.createSingleStringSubQueryVal(subQuery, userName);
		Condition c3 = new Condition("school.pop_prog_3", CondType.IN, cval3);
		conditions.add(c3);
		
		CondVal cval4 = CondVal.createSingleStringSubQueryVal(subQuery, userName);
		Condition c4 = new Condition("school.pop_prog_4", CondType.IN, cval4);
		conditions.add(c4);
		
		CondVal cval5 = CondVal.createSingleStringSubQueryVal(subQuery, userName);
		Condition c5 = new Condition("school.pop_prog_5", CondType.IN, cval5);
		conditions.add(c5);
		
		//puts all the above conditions into OR group (c1 OR c2 OR ...)
		CondVal orVal = CondVal.createORGroupVal(conditions);
		Condition orCondition = new Condition("", CondType.OR_GROUP, orVal);
		return orCondition;
	}
	
	/**
	 * Returns condition for checking whether a school offers any of a user's favorites.
	 * 
	 * @param userName
	 * @return condition for checking whether a school offers any of a user's favorites
	 */
	public Condition favsInOffers(String userName) {
		String subQuery = 
				"(SELECT school_ID FROM offers "
				+ "WHERE field_ID IN "
				+ "(SELECT field_ID FROM favoriteFieldsOfStudy WHERE std_ID=?))";
		CondVal v = CondVal.createSingleStringSubQueryVal(subQuery, userName);
		Condition c = new Condition(School.ID, CondType.IN, v);
		return c;
	}
	
	/**
	 * Returns condition for checking whether school contains user-specified field(s) of study
	 * in its top five fields of study, using OR logic
	 * 
	 * @param list of fields
	 * @return condition for checking whether school contains user-specified field of study
	 * in its top five fields of study
	 */
	public Condition containsSelectedFieldOfStudy(ArrayList<Integer> fieldIDList) {
		 // WHERE fieldID IN (school.pop_prog_1, pop_prog_2...)
		List<Condition> userSelectedFieldsOfStudy = new LinkedList<Condition>();
		Iterator<Integer> fieldIDListIterator = fieldIDList.iterator();
		while (fieldIDListIterator.hasNext()) {
			CondVal ForFieldID = CondVal.createIntVal(fieldIDListIterator.next());
			Condition c = new Condition("(pop_prog_1, pop_prog_2, pop_prog_3, pop_prog_4,"
					+ " pop_prog_5)", CondType.REVERSE_IN, ForFieldID);
			userSelectedFieldsOfStudy.add(c);
		}
		CondVal orFields = CondVal.createORGroupVal(userSelectedFieldsOfStudy);
		Condition orFieldsCondition = new Condition("", CondType.OR_GROUP, orFields);
		return orFieldsCondition;
	}
	
	/**
	 * Returns a condition for comparing a user's SAT scores to a school's average. 
	 * Order is school average [comparison operator] user score
	 * 
	 * @param type Type where 
	 * @param userName
	 * @return A condition for comparing a user's SAT scores to a school's average
	 */
	public Condition compareMySAT(CondType type, String userName) {
		String subquery = "(SELECT SAT_SCORE FROM user WHERE ID=?)";
		return new Condition(School.SAT_AVG, type, CondVal.createSingleStringSubQueryVal(subquery, userName));
	}
	
	/**
	 * Returns a condition for comparing a user's ACT scores to a school's average
	 * Order is school average [comparison operator] user score
	 * 
	 * @param type
	 * @param userName
	 * @return A condition for comparing a user's ACT scores to a school's average
	 */
	public Condition compareMyACT(CondType type, String userName) {
		String subquery = "(SELECT ACT_SCORE FROM user WHERE ID=?)";
		return new Condition(School.ACT_AVG, type, CondVal.createSingleStringSubQueryVal(subquery, userName));
	}
	
	/**
	 * This method returns a school object with fields matching the fields selected in the query.
	 * 
	 * @param schoolID
	 * @return School object with fields populated matching the query SELECT <attributes>
	 * 
	 */
	
	public School getSingleSchoolViewInfo(int schoolID) {
		School school = new School();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet PopProgRS = null;
		try {
			String fullSchoolQuery = 
					"SELECT school.id AS id, school.name AS name, school.url AS url, school.avg_cost, school.SAT_pct_25_cumulative,"
					+ "school.SAT_pct_75_cumulative, school.ACT_pct_25_cumulative, school.ACT_pct_75_cumulative,"
					+ "school.tuition_and_fees_out AS outOfState, school.std_bdy_sz as student_body_size, school.pop_prog_1, school.pop_prog_2,"
					+ "school.pop_prog_3, school.pop_prog_4, school.pop_prog_5, school.avg_fam_inc, school.med_fam_inc, school.avg_entry_age,"
					+ "school.1_gen_std_share AS firstGenStudentShare, school.level, school.dist_learning AS distanceLearning,"
					+ "school.tuition_and_fees_in AS inState, school.tuition_and_fees_out AS outOfState,"
					+ " location.city AS city, school.SAT_avg, school.ACT_avg, school.adm_rate, genderdemographics.female, genderdemographics.male,"
					+ "ethnicdemographics.white, ethnicdemographics.black, ethnicdemographics.hispanic, ethnicdemographics.asian,"
					+ "ethnicdemographics.american_indian_alaskan_native, ethnicdemographics.native_hawaiian_pacific_islander, "
					+ "ethnicdemographics.two_or_more AS multiethnic, ethnicdemographics.unknown AS unknownEthnicity, "
					+ "ethnicdemographics.nonresident AS nonresidentAlien, location.city, location.state_string, school.avg_earnings_6_years_after_matriculation,"
					+ "school.med_debt";
			String FROM = 
					" FROM school JOIN school_loc ON school.ID = school_loc.school_id"
					+ " JOIN location ON school_loc.loc_ID = location.ID"
					+ " JOIN GenderDemographics ON school.GenderDemographics_ID = GenderDemographics.ID"
					+ " JOIN EthnicDemographics ON school.EthnicDemographics_ID = EthnicDemographics.ID"
					+ " JOIN region ON location.state = region.state";
			String WHERE =
					" WHERE school.id = ?";
			FROM += WHERE;
			fullSchoolQuery += FROM;
			
			pstmt = dbUtil.getConnection().prepareStatement(fullSchoolQuery);
			pstmt.setInt(1, schoolID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Location loc = new Location();
				String city = rs.getString("city");
				if (!rs.wasNull()) {
					school.setLocationIsNotNull(true);
					loc.setCity(city);
					loc.setCityIsNotNull(true);
				}
				String state = rs.getString("state_string");
				if (!rs.wasNull()) {
					loc.setStateStr(state);
					loc.setStateStrIsNotNull(true);
				}
				school.setLocation(loc);
				int ID = rs.getInt("id");
				if (!rs.wasNull()) {
					school.setId(ID);
					school.setIdIsNotNull(true);
				}
				String name = rs.getString("name");
				if (!rs.wasNull()) {
					school.setName(name);
					school.setNameIsNotNull(true);
				}
				String website = rs.getString("url");
				if (!rs.wasNull()) {
					school.setWebsite(website);
					school.setWebsiteIsNotNull(true);
				}
				int cost = rs.getInt("avg_cost");
				if (!rs.wasNull()) {
					school.setAvgCost(cost);
					school.setAvgCostIsNotNull(true);
				}
				double SAT25 = rs.getDouble("SAT_pct_25_cumulative");
				if (!rs.wasNull()) {
					school.setSat25(SAT25);
					school.setSat25IsNotNull(true);
				}
				double SATavg = rs.getDouble("SAT_avg");
				if (!rs.wasNull()) {
					school.setSatAvg(SATavg);
					school.setSatAvgIsNotNull(true);
				}
				double SAT75 = rs.getDouble("SAT_pct_75_cumulative");
				if (!rs.wasNull()) {
					school.setSat75(SAT75);
					school.setSat75IsNotNull(true);
				}
				double ACTavg = rs.getDouble("ACT_avg");
				if (!rs.wasNull()) {
					school.setActAvg(ACTavg);
					school.setActAvgIsNotNull(true);
				}
				double ACT25 = rs.getDouble("ACT_pct_25_cumulative");
				if (!rs.wasNull()) {
					school.setAct25(ACT25);
					school.setAct25IsNotNull(true);
				}
				double ACT75 = rs.getDouble("ACT_pct_75_cumulative");
				if (!rs.wasNull()) {
					school.setAct75(ACT75);
					school.setAct75IsNotNull(true);
				}
				int tuitionIn = rs.getInt("inState");
				if (!rs.wasNull()) {
					school.setTuitionIn(tuitionIn);
					school.setTuitionInIsNotNull(true);
				}
				int tuitionOut = rs.getInt("outOfState");
				if (!rs.wasNull()) {
					school.setTuitionOut(tuitionOut);
					school.setTuitionOutIsNotNull(true);
				}
				double admRate = rs.getDouble("adm_rate");
				if (!rs.wasNull()) {
					school.setAdmissionRate(admRate);
					school.setAdmissionRateIsNotNull(true);
				}
				int studentBody = rs.getInt("student_body_size");
				if (!rs.wasNull()) {
					school.setStdBodySz(studentBody);
					school.setStdBodySzIsNotNull(true);
				}
				int popProg1 = rs.getInt("pop_prog_1");
				if (!rs.wasNull()) {
					school.setPopProg1ID(popProg1);
					school.setPopProg1IDIsNotNull(true);
				}
				int popProg2 = rs.getInt("pop_prog_2");
				if (!rs.wasNull()) {
					school.setPopProg2ID(popProg2);
					school.setPopProg2IDIsNotNull(true);
				}
				int popProg3 = rs.getInt("pop_prog_3");
				if (!rs.wasNull()) {
					school.setPopProg3ID(popProg3);
					school.setPopProg3IDIsNotNull(true);
				}
				int popProg4 = rs.getInt("pop_prog_4");
				if (!rs.wasNull()) {
					school.setPopProg4ID(popProg4);
					school.setPopProg4IDIsNotNull(true);
				}
				int popProg5 = rs.getInt("pop_prog_5");
				if (!rs.wasNull() ) {
					school.setPopProg5ID(popProg5);
					school.setPopProg5IDIsNotNull(true);
				}
				int avgFamInc = rs.getInt("avg_fam_inc");
				if (!rs.wasNull() ) {
					school.setAvgFamilyIncome(avgFamInc);
					school.setAvgFamIncomeIsNotNull(true);
				}
				int medFamInc = rs.getInt("med_fam_inc");
				if (!rs.wasNull() ) {
					school.setMedFamIncome(medFamInc);
					school.setMedFamIncomeIsNotNull(true);
				}
				int age = rs.getInt("avg_entry_age");
				if (!rs.wasNull()) {
					school.setAvgAge(age);
					school.setAvgAgeIsNotNull(true);
				}
				double firstGen = rs.getDouble("firstGenStudentShare");
				if (!rs.wasNull()) {
					school.setFirstGenStudentShare(firstGen);
					school.setFirstGenStudentShareIsNotNull(true);
				}
				int earnings = rs.getInt("avg_earnings_6_years_after_matriculation");
				if (!rs.wasNull()) {
					school.setAvgEarnings(earnings);
					school.setAvgEarningsIsNotNull(true);
				}
				double debt = rs.getDouble("med_debt");
				if (!rs.wasNull()) {
					school.setMedDebt(debt);
					school.setMedDebtIsNotNull(true);
				}
				if (rs.getInt("level") == 1) {
				school.setLevel("4-year");
				}
				else if (rs.getInt("level") == 2) {
					school.setLevel("2-year");
				}
				int dist = rs.getInt("distanceLearning");
				if (!rs.wasNull()) {
					school.setDistanceLearning(dist);
					school.setDistanceLearningIsNotNull(true);
				}
				double male = rs.getDouble("male");
				if (!rs.wasNull()) {
					school.setMaleShare(male);
					school.setMaleShareIsNotNull(true);
				}
				double female = rs.getDouble("female");
				if (!rs.wasNull()) {
					school.setFemaleShare(female);
					school.setFemaleShareIsNotNull(true);
				}
				double white = rs.getDouble("white");
				if (!rs.wasNull()) {
					school.setWhite(white);
					school.setWhiteIsNotNull(true);
				}
				double black = rs.getDouble("black");
				if (!rs.wasNull()) {
					school.setBlack(black);
					school.setBlackIsNotNull(true);
				}
				double hispanic = rs.getDouble("hispanic");
				if (!rs.wasNull()) {
					school.setHispanic(hispanic);
					school.setHispanicIsNotNull(true);
				}
				double asian = rs.getDouble("asian");
				if (!rs.wasNull()) {
					school.setAsian(asian);
					school.setAsianIsNotNull(true);
				}
				double americanIndian = rs.getDouble("american_indian_alaskan_native");
				if (!rs.wasNull()) {
					school.setAmerican_indian_alaskan_native(americanIndian);
					school.setAmericanIndianAlaskanNativeIsNotNull(true);
				}
				double nativeHawaiian = rs.getDouble("native_hawaiian_pacific_islander");
				if (!rs.wasNull()) {
					school.setNative_hawaiian_pacific_islander(nativeHawaiian);
					school.setNativeHawaiianPacificIslanderIsNotNull(true);
				}
				double multi = rs.getDouble("multiethnic");
				if (!rs.wasNull()) {
					school.setMultiethnic(multi);
					school.setMultiethnicIsNotNull(true);
				}
				double unknown = rs.getDouble("unknownEthnicity");
				if (!rs.wasNull()) {
					school.setUnknown_ethnicity(unknown);
					school.setUnknownEthnicityIsNotNull(true);
				}
				double nonresident = rs.getDouble("nonresidentAlien");
				if (!rs.wasNull()) {
					school.setNonresident(nonresident);
					school.setNonresidentIsNotNull(true);
				}
				DBUtil.closeResultSet(rs);
				DBUtil.closeStatement(pstmt);
				
				//Top 5 fields queries
				String PopProgStringQuery = "SELECT fieldsOfStudy.name FROM fieldsOfStudy "
						+ "WHERE fieldsOfStudy.ID = ?";
				pstmt = dbUtil.getConnection().prepareStatement(PopProgStringQuery);
				pstmt.setInt(1, school.getPopProg1ID());
				PopProgRS = pstmt.executeQuery();
				if (PopProgRS.next()) {
				school.setPopProg1(PopProgRS.getString(1));
				school.setPopProg1IsNotNull(true);
				}
				DBUtil.closeResultSet(PopProgRS);
				DBUtil.closeStatement(pstmt);

				//pop prog 2
				PopProgStringQuery = "SELECT fieldsOfStudy.name FROM fieldsOfStudy "
						+ "WHERE fieldsOfStudy.ID = ?";
				pstmt = dbUtil.getConnection().prepareStatement(PopProgStringQuery);
				pstmt.setInt(1, school.getPopProg2ID());
				PopProgRS = pstmt.executeQuery();
				if (PopProgRS.next()) {
				school.setPopProg2(PopProgRS.getString(1));
				school.setPopProg2IsNotNull(true);
				}
				DBUtil.closeResultSet(PopProgRS);
				DBUtil.closeStatement(pstmt);

				//pop prog 3
				PopProgStringQuery = "SELECT fieldsOfStudy.name FROM fieldsOfStudy "
						+ "WHERE fieldsOfStudy.ID = ?";
				pstmt = dbUtil.getConnection().prepareStatement(PopProgStringQuery);
				pstmt.setInt(1, school.getPopProg3ID());
				PopProgRS = pstmt.executeQuery();
				if (PopProgRS.next()) {
				school.setPopProg3(PopProgRS.getString(1));
				school.setPopProg3IsNotNull(true);
				}
				DBUtil.closeResultSet(PopProgRS);
				DBUtil.closeStatement(pstmt);
				
				//pop prog 4 
				PopProgStringQuery = "SELECT fieldsOfStudy.name FROM fieldsOfStudy "
						+ "WHERE fieldsOfStudy.ID = ?";
				pstmt = dbUtil.getConnection().prepareStatement(PopProgStringQuery);
				pstmt.setInt(1, school.getPopProg4ID());
				PopProgRS = pstmt.executeQuery();
				if (PopProgRS.next()) {
				school.setPopProg4(PopProgRS.getString(1));
				school.setPopProg4IsNotNull(true);
				}
				DBUtil.closeResultSet(PopProgRS);
				DBUtil.closeStatement(pstmt);
			
				//pop prog 5
				PopProgStringQuery = "SELECT fieldsOfStudy.name FROM fieldsOfStudy "
						+ "WHERE fieldsOfStudy.ID = ?";
				pstmt = dbUtil.getConnection().prepareStatement(PopProgStringQuery);
				pstmt.setInt(1, school.getPopProg5ID());
				PopProgRS = pstmt.executeQuery();
				if (PopProgRS.next()) {
				school.setPopProg5(PopProgRS.getString(1));
				school.setPopProg5IsNotNull(true);
				}
			}
		}
			catch (SQLException e) {
				System.out.println(e.toString());
			}
		finally {
			DBUtil.closeResultSet(PopProgRS);
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}
		return school;
	}
	
	/**
	 * 
	 * @param sb StringBuilder for the PreparedStatement
	 * @param conditions The list of search criteria
	 * @return A list of school objects resulting from the query
	 */
	private List<School> processResults(StringBuilder sb, List<Condition> conditions) {
		LinkedList<School> schools = new LinkedList<School>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement(sb.toString());
			insertIntoPrepStmt(conditions, pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				School s = new School();
				Location l = new Location();
				String city = rs.getString("city");
				if (!rs.wasNull()) {
					s.setLocationIsNotNull(true);
					l.setCityIsNotNull(true);
					l.setCity(city);
				}
				String state = rs.getString("stateStr");
				if (!rs.wasNull()) {
					l.setStateStrIsNotNull(true);
					l.setStateStr(state);
				}
				s.setLocation(l);
				int schoolID = rs.getInt("ID");
				if (!rs.wasNull()) {
					s.setIdIsNotNull(true);
					s.setId(schoolID);
				}
				String name = rs.getString("name");
				if (!rs.wasNull()) {
					s.setNameIsNotNull(true);
					s.setName(name);
				}
				String url = rs.getString("url");
				if (!rs.wasNull()) {
					s.setWebsiteIsNotNull(true);
					s.setWebsite(url);
				}
				int inState = rs.getInt("inState");
				if (!rs.wasNull()) {
					s.setTuitionInIsNotNull(true);
					s.setTuitionIn(inState);
				}
				int outState = rs.getInt("outOfState");
				if (!rs.wasNull()) {
					s.setTuitionOutIsNotNull(true);
					s.setTuitionOut(outState);
				}
				double satAvg = rs.getDouble("SAT_avg");
				if (!rs.wasNull()) {
					s.setSatAvgIsNotNull(true);
					s.setSatAvg(satAvg);
				}
				double actAvg = rs.getDouble("ACT_avg");
				if (!rs.wasNull()) {
					s.setActAvgIsNotNull(true);
					s.setActAvg(actAvg);
				}
				double admissionRate = rs.getDouble("adm_rate");
				if (!rs.wasNull()) {
					s.setAdmissionRateIsNotNull(true);
					s.setAdmissionRate(admissionRate);
				}
				
				schools.addLast(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}

		
		return schools;
	}
	
	/**
	 * Takes a list of conditions and inserts them into a PreparedStatement. Assumes that the indices into the 
	 * PreparedStatement for the values have been set
	 * 
	 * @param conditions A list of conditions
	 * @param pstmt The prepared statement to insert values into
	 * @throws SQLException
	 */
	private void insertIntoPrepStmt(List<Condition> conditions, PreparedStatement pstmt) throws SQLException {
		for (Condition c: conditions) {
			if (c.getConditionType() == CondType.NO_COND) {
				continue;
			} else {
				CondVal val = c.getValue();
				ValType vtype = val.getType();
				switch (vtype) {
					case INT_RANGE: 	pstmt.setInt(val.getIndexOfMin(), val.getMinInt());
										pstmt.setInt(val.getIndexOfMax(), val.getMaxInt());
										break;
					case DOUBLE_RANGE:	pstmt.setInt(val.getIndexOfMin(), val.getMinInt());
										pstmt.setInt(val.getIndexOfMax(), val.getMaxInt());
										break;
					case INT:			pstmt.setInt(val.getIndex(), val.getIntVal());
										break;
					case DOUBLE:		pstmt.setDouble(val.getIndex(), val.getDoubleVal());
										break;
					case STRING:		pstmt.setString(val.getIndex(), val.getStrVal());
										break;
					case SINGLE_STRING_SUBQUERY:	pstmt.setString(val.getIndex(), val.getSubQueryStrVal());
													break;
					case OR_GROUP:		insertIntoPrepStmt(val.getOrConditions(), pstmt);	//recursive
										break;
				}
			}
		}
	}

}
