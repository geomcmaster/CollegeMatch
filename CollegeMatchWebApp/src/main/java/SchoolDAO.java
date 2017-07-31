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
	 * Performs query based on user search. Does not yet support order by.
	 * 
	 * @param conditions The list of conditions to use in search
	 * @param fromTables Bitmap of tables (in addition to school) to include in FROM list
	 * @param tablesToJoin Bitmap of tables to join on. Setting FIELDS requires that OFFERS is also set.
	 * It is assumed that school_loc and location tables will be joined to retrieve location info.
	 * @return A list of school objects returned by the query
	 */
	public List<School> getSchools(List<Condition> conditions, byte fromTables, byte tablesToJoin) {
		List<School> schools = new LinkedList<School>();
		
		//TODO columns
		String baseQuery = "SELECT ____ FROM school";
//				+ "FROM school "
//				+ "JOIN GenderDemographics ON school.GenderDemographics_ID = GenderDemographics.ID "
//				+ "JOIN EthnicDemographics ON school.EthnicDemographics_ID = EthnicDemographics.ID "
//				+ "JOIN school_loc ON school_loc.school_ID = school.ID "
//				+ "JOIN location ON school_loc.loc_ID = location.ID "
//				+ "JOIN region ON location.state = region.state "
//				+ "JOIN offers ON ";
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
		//BUILD WHERE CLAUSE
		queryBuilder.append(" WHERE");
		ListIterator<Condition> itr = conditions.listIterator();
		int index = 1;	//index in PreparedStatement
		//first condition not prefaced with AND
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" ");
				//TODO will have to handle more complex conditions
				queryBuilder.append(c.getColumnName());
				String operator = "";
				CondVal val = c.getValue();
				switch (ctype) {
					case RANGE:	operator = " BETWEEN ? AND ?";
								val.setIndexOfMin(index++);
								val.setIndexOfMax(index++);
								break;
					case EQ:	operator = " = ?";
								val.setIndex(index++);
								break;
					case GT:	operator = " > ?";
								val.setIndex(index++);
								break;
					case GE:	operator = " >= ?";
								val.setIndex(index++);
								break;
					case LT:	operator = " < ?";
								val.setIndex(index++);
								break;
					case LE:	operator = " <= ?";
								val.setIndex(index++);
								break;
					case NE:	operator = " <> ?";
								val.setIndex(index++);
								break;
					case LIKE:	operator = " LIKE ?";
								val.setIndex(index++);
								break;
				}
				queryBuilder.append(operator);
				break;
			}
		}
		while (itr.hasNext()) {
			Condition c = itr.next();
			CondType ctype = c.getConditionType();
			if (ctype != CondType.NO_COND) {
				queryBuilder.append(" AND ");
				//TODO will have to handle more complex conditions
				queryBuilder.append(c.getColumnName());
				String operator = "";
				CondVal val = c.getValue();
				switch (ctype) {
					case RANGE:	operator = " BETWEEN ? AND ?";
								val.setIndexOfMin(index++);
								val.setIndexOfMax(index++);
								break;
					case EQ:	operator = " = ?";
								val.setIndex(index++);
								break;
					case GT:	operator = " > ?";
								val.setIndex(index++);
								break;
					case GE:	operator = " >= ?";
								val.setIndex(index++);
								break;
					case LT:	operator = " < ?";
								val.setIndex(index++);
								break;
					case LE:	operator = " <= ?";
								val.setIndex(index++);
								break;
					case NE:	operator = " <> ?";
								val.setIndex(index++);
								break;
					case LIKE:	operator = " LIKE ?";
								val.setIndex(index++);
								break;
				}
				queryBuilder.append(operator);
			}
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = dbUtil.getConnection().prepareStatement(queryBuilder.toString());
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
				//call setString/Int/Double using index o
			}
			rs = pstmt.executeQuery();
			
			//TODO loop over rs
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(pstmt);
		}

		
		return schools;
	}

}
