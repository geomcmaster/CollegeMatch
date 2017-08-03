package main.java;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * Performs query based on user search. Does not yet support order by. Currently schools 
	 * only return name, url, in state/out of state tuition, city, and state, but we can 
	 * modify this to be whatever we want to return to the user.
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
				"SELECT school.name AS name, school.url AS url, school.tuition_and_fees_out AS outOfState, "
				+ "school.tuition_and_fees_in AS inState, location.city AS city, location.state_string AS stateStr FROM school";
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
					l.setCityIsNotNull(true);
					l.setCity(city);
				}
				String state = rs.getString("stateStr");
				if (!rs.wasNull()) {
					l.setStateStrIsNotNull(true);
					l.setStateStr(state);
				}
				s.setLocation(l);
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
