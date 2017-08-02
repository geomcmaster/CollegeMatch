package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.CondType;
import main.java.CondVal;
import main.java.Condition;
import main.java.DBUtil;
import main.java.School;
import main.java.SchoolDAO;
import main.java.UserDAO;

public class SchoolDAOTest {
	private DBUtil dbUtil;
	private SchoolDAO schoolDAO;
	private UserDAO userDAO;
	
	@Before
	public void setUp() {
		dbUtil = new DBUtil();
		schoolDAO = new SchoolDAO();
		userDAO = new UserDAO();
	}
	
	@Test
	public void testGetSchools() {
		testOneCondition();
		testSimpleConditions();

		//favs in top 5
		//create user
		String user1 = "favtestuser";
		String pw = "favtestpw";
		userDAO.createUser(user1, pw);
		userDAO.modifyFavField(user1, 47, 1);
		userDAO.modifyFavField(user1, 3, 2);
		//create conditions
		List<Condition> conditions = new LinkedList<Condition>();
		Condition suny = new Condition("school.name", CondType.LIKE, CondVal.createStrVal("SUNY%"));
		Condition top5 = schoolDAO.favsInTopFive(user1);
		conditions.add(suny);
		conditions.add(top5);
		//execute
		List<School> schools = schoolDAO.getSchools(conditions, SchoolDAO.NONE);
		assertEquals("More than three schools returned", 3, schools.size());
		for (School school : schools) {
			assertTrue("Wrong school returned", school.getName().equals("SUNY College of Technology at Alfred") 
					|| school.getName().equals("SUNY College of Environmental Science and Forestry") 
					|| school.getName().equals("SUNY College of Agriculture and Technology at Cobleskill"));
		}
		
		//offers favs
		//TODO
		
	}
	
	private void testOneCondition() {
		Condition c = 
				new Condition( "school.tuition_and_fees_out", 					//out of state tuition < 20000
						CondType.GT, CondVal.createIntVal(40000));
		List<Condition> conditions = new LinkedList<Condition>();
		conditions.add(c);
		List<School> schools = schoolDAO.getSchools(conditions, SchoolDAO.NONE);
		for (School school : schools) {
			assertTrue("Out of state tuition not greater than 40000", school.getTuitionOut() > 40000);
		}
	}

	private void testSimpleConditions() {
		Condition c1 = 
				new Condition(
						"location.state", CondType.EQ, CondVal.createIntVal(6));		//state = 6 [CA]
		Condition c2 = 
				new Condition(
						"school.tuition_and_fees_in", 								//in state tuition < 20000
						CondType.LT, CondVal.createIntVal(20000));
		List<Condition> conditions = new LinkedList<Condition>();
		conditions.add(c1);
		conditions.add(c2);
		List<School> schools = schoolDAO.getSchools(conditions, SchoolDAO.NONE);
		for (School school : schools) {
			assertEquals("School not in CA", school.getLocation().getStateStr(), "CA");
			assertTrue("In state tuition not less than 20000", school.getTuitionIn() < 20000);
		}
	}
	
	@After
	public void cleanUp() {
		cleanUpFavoriteFields();
		cleanUpUser();
		
		//ALWAYS LAST
		dbUtil.closeConnection();
	}
	
	private void cleanUpUser() {
		Statement stmt = null;
		try {
			stmt = dbUtil.getConnection().createStatement();
			stmt.executeUpdate("DELETE FROM user");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
	
	private void cleanUpFavoriteFields() {
		Statement stmt = null;
		try {
			stmt = dbUtil.getConnection().createStatement();
			stmt.executeUpdate("DELETE FROM favoriteFieldsOfStudy");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStatement(stmt);
		}
	}
}
