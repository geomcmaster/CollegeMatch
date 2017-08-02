package test.java;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.CondType;
import main.java.CondVal;
import main.java.Condition;
import main.java.DBUtil;
import main.java.School;
import main.java.SchoolDAO;

public class SchoolDAOTest {
	private DBUtil dbUtil;
	private SchoolDAO schoolDAO;
	
	@Before
	public void setUp() {
		dbUtil = new DBUtil();
		schoolDAO = new SchoolDAO();
	}
	
	@Test
	public void testGetSchools() {
		testSimpleConditions();
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
		List<School> schools = schoolDAO.getSchools(conditions, schoolDAO.NONE);
		for (School school : schools) {
			assertEquals("School not in CA", school.getLocation().getStateStr(), "CA");
			assertTrue("In state tuition not less than 20000", school.getTuitionIn() < 20000);
		}
	}
	
}
