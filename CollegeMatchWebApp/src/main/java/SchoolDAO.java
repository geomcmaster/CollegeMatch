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
	//FROM tables
	public static final byte RESIDENCE = 0x1;
	public static final byte FAV_FIELDS = 0x2;
	public static final byte USER = 0x4;
	//JOIN tables
	public static final byte GENDER = 0x1;
	public static final byte ETHNIC = 0x2;
	public static final byte REGION = 0x4;
	public static final byte OFFERS = 0x8;
	public static final byte FIELDS = 0x10;
	
	public SchoolDAO() {
		dbUtil = new DBUtil();
	}
	
	/**
	 * Performs query based on user search. Does not yet support order by. Does not yet support complex conditions 
	 * such as "OR" or subqueries. Currently schools only return name, url, in state/out of state tuition, 
	 * city, and state, but we can modify this to be whatever we want to return to the user.
	 * 
	 * @param conditions The list of conditions to use in search
	 * @param fromTables Bitmap of tables (in addition to school) to include in FROM list
	 * @param tablesToJoin Bitmap of tables to join on. Setting FIELDS requires that OFFERS is also set.
	 * It is assumed that school_loc and location tables will be joined to retrieve location info.
	 * @return A list of school objects returned by the query
	 */
	public List<School> getSchools(List<Condition> conditions, byte fromTables, byte tablesToJoin) {
		
		StringBuilder queryBuilder = selectFromJoin(fromTables, tablesToJoin);	//SELECT ... FROM ... JOIN ... ON etc.
		
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
		if (val.getType() == ValType.SUBQUERY) {
			return buildSubqueryConditionString(c, i);
		} else if (val.getType() == ValType.OR_GROUP) {
			//TODO handle this
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
	
	private String buildSubqueryConditionString(Condition c, Index i) {
		String condStr = c.getColumnName();
		CondVal val = c.getValue();
		switch (c.getConditionType()) {
			case RANGE:	throw new RuntimeException("Subquery condition does not support range");
			case EQ:	condStr += " = " + val.getSubQuery();
						break;
			case GT:	condStr += " > " + val.getSubQuery();
						break;
			case GE:	condStr += " >= " + val.getSubQuery();
						break;
			case LT:	condStr += " < " + val.getSubQuery();
						break;
			case LE:	condStr += " <= " + val.getSubQuery();
						break;
			case NE:	condStr += " <> " + val.getSubQuery();
						break;
			case LIKE:	condStr += " LIKE " + val.getSubQuery();
						break;
			case IN: condStr += " IN " + val.getSubQuery();
						break;
		}
		return condStr;
	}
	
	/**
	 * 
	 * @param fromTables Bitmap of tables to include in FROM
	 * @param tablesToJoin Bitmap of tables to JOIN
	 * @return A StringBuilder for SELECT, FROM and JOIN clauses
	 */
	private StringBuilder selectFromJoin(byte fromTables, byte tablesToJoin) {
		String baseQuery = 
				"SELECT school.name AS name, school.url AS url, school.tuition_out AS outOfState, "
				+ "school.tuition_in AS inState, location.city AS city, location.state AS state FROM school";
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		//FROM
		if ((fromTables & RESIDENCE) == RESIDENCE) {
			queryBuilder.append(", residence");
		}
		if ((fromTables & FAV_FIELDS) == FAV_FIELDS) {
			queryBuilder.append(", favoriteFieldsOfStudy");
		}
		if ((fromTables & USER) == USER) {
			queryBuilder.append(", user");
		}
		//JOINS
		queryBuilder.append(" JOIN school_loc ON school_loc.school_ID = school.ID "
				+ "JOIN location ON school_loc.loc_ID = location.ID");
		if ((tablesToJoin & GENDER) == GENDER) {
			queryBuilder.append(" JOIN GenderDemographics ON GenderDemographics_ID = school.ID");
		}
		if ((tablesToJoin & ETHNIC) == ETHNIC) {
			queryBuilder.append(" JOIN EthnicDemographics ON EthnicDemographics_ID = school.ID");
		}
		if ((tablesToJoin & REGION) == REGION) {
			queryBuilder.append(" JOIN region ON location.state = region.state");
		}
		if ((tablesToJoin & OFFERS) == OFFERS) {
			queryBuilder.append(" JOIN offers ON offers.school_ID = school.ID");
		}
		if ((tablesToJoin & FIELDS) == FIELDS) {
			queryBuilder.append(" JOIN fieldsOfStudy ON offers.field_ID = fieldsOfStudy.ID");
		}
		
		return queryBuilder;
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
			for (Condition c: conditions) {
				if (c.getConditionType() == CondType.NO_COND) {
					continue;
				} else {
					CondVal val = c.getValue();
					ValType vtype = val.getType();
					if (vtype == ValType.INT_RANGE) {
						pstmt.setInt(val.getIndexOfMin(), val.getMinInt());
						pstmt.setInt(val.getIndexOfMax(), val.getMaxInt());
					} else if (vtype == ValType.DOUBLE_RANGE) {
						pstmt.setDouble(val.getIndexOfMin(), val.getMinDouble());
						pstmt.setDouble(val.getIndexOfMax(), val.getMaxDouble());
					} else if (vtype == ValType.INT) {
						pstmt.setInt(val.getIndex(), val.getIntVal());
					} else if (vtype == ValType.DOUBLE) {
						pstmt.setDouble(val.getIndex(), val.getDoubleVal());
					} else if (vtype == ValType.STRING) {
						pstmt.setString(val.getIndex(), val.getStrVal());
					}
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				School s = new School();
				Location l = new Location();
				l.setCity(rs.getString("city"));
				l.setState(rs.getInt("state"));
				s.setLocation(l);
				s.setName(rs.getString("name"));
				s.setWebsite(rs.getString("url"));
				s.setTuitionIn(rs.getInt("inState"));
				s.setTuitionOut(rs.getInt("outOfState"));
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

}
