package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import main.java.SortColumn;
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
		testContainsUserFieldsOfStudy();
		testOneCondition();
		testSimpleConditions();
		testWithFavs();
		testNullValues();
		testMyScores();
		testJoinConditions();
		testOrderBy();
		testDistance();
		testGetSingleSchoolViewInfo();
	}
	
	private void testGetSingleSchoolViewInfo() {
		boolean testWorks = true;
		int YaleUniversityID = 130794;
		School Yale = new School();
		Yale = schoolDAO.getSingleSchoolViewInfo(YaleUniversityID);
		//A massive boolean check on all returned school fields
		assertEquals("Name", "Yale University", Yale.getName());
		assertEquals("URL", "www.yale.edu", Yale.getWebsite());
		assertEquals("SATAvg", 1493, Yale.getSatAvg(), .01);
		//TODO add these again after updating DB
		//assertEquals("SAT25", 1410, Yale.getSat25(), .01);
		//assertEquals("SAT75", 1600, Yale.getSat75(), .01);
		assertEquals("ACTAvg", 33, Yale.getActAvg(), .01);
		assertEquals("ACT25", 31, Yale.getAct25(), .01);
		assertEquals("ACT75", 35, Yale.getAct75(), 01);
		assertEquals("Earnings", 75500, Yale.getAvgEarnings());
		assertEquals("Cost", 61620, Yale.getAvgCost());
		assertEquals("Debt", 13206, Yale.getMedDebt(), .01);
		assertEquals("StudentBody", 5473, Yale.getStdBodySz());
		assertEquals("PopProg1", "Social Sciences", Yale.getPopProg1());
		assertEquals("PopProg2", "Biological and Biomedical Sciences", Yale.getPopProg2());
		assertEquals("PopProg3", "Multi/Interdisciplinary Studies", Yale.getPopProg3());
		//TODO fix the rest of these
		if (!Yale.getPopProg4().equals("History")) {
			System.out.println("PopProg4");
			testWorks = false;
		}
		if (!Yale.getPopProg5().equals("English Language and Literature/Letters")) {
			System.out.println("PopProg5");
			testWorks = false;
		}
		if (Yale.getAdmissionRate()!=0.063) {
			System.out.println("admRate");
			testWorks = false;
		}
		if (Yale.getAvgFamilyIncome()!=90290) {
			System.out.println("AvgFamInc");
			testWorks = false;
		}
		if (Yale.getMedFamIncome()!=49166) {
			System.out.println("MedFamInc");
			testWorks = false;
		}
		if (Yale.getTuitionIn()!=45800) {
			System.out.println("TuitionIn");
			testWorks = false;
		}
		if (Yale.getTuitionOut()!=45800) {
			System.out.println("TuitionOut");
			testWorks = false;
		}
		if (Yale.getAvgAge()!=19) {
			System.out.println("Age");
			testWorks = false;
		}
		if (Yale.getFirstGenStudentShare()!=0.224625624) {
			System.out.println("1stGen");
			testWorks = false;
		}
		if (!Yale.getLevel().equals("4-year")) {
			System.out.println("level");
			testWorks = false;
		}
		if (Yale.isDistanceLearningNotNull()==false) {
			System.out.println("distLearningBoolean");
			testWorks = false;
		}
		if (Yale.getDistanceLearning()!=0) {
			System.out.println("distLearning");
			testWorks = false;
		}
		if (!(Yale.getLocation().getCity().equals(("New Haven")))) {
			System.out.println("city");
			testWorks = false;
		}
		if (!Yale.getLocation().getStateStr().equals("CT")) {
			System.out.println("state");
			testWorks = false;
		}
		if (Yale.getFemaleShare()!=0.4902) {
			System.out.println("female");
			testWorks = false;
		}
		if (Yale.getMaleShare()!=0.5098) {
			System.out.println("male");
			testWorks = false;
		}
		if (Yale.getWhite()!=0.4705) {
			System.out.println("white");
			testWorks = false;
		}
		if (Yale.getBlack()!=0.0678) {
			System.out.println("black");
			testWorks = false;
		}
		if (Yale.getHispanic()!=0.11) {
			System.out.println("hispanic");
			testWorks = false;
		}
		if (Yale.getAsian()!=0.1655) {
			System.out.println("asian");
			testWorks = false;
		}
		if (Yale.getAmerican_indian_alaskan_native()!=0.0064) {
			System.out.println("americanIndian");
			testWorks = false;
		}
		if (Yale.getNative_hawaiian_pacific_islander()!=0) {
			System.out.println("nativeHawaiian");
			testWorks = false;
		}
		if (Yale.getMultiethnic()!=0.0596) {
			System.out.println("multiethnic");
			testWorks = false;
		}
		if (Yale.getUnknown_ethnicity()!=0.0163) {
			System.out.println("unknown ethnicity");
			testWorks = false;
		}
		if (Yale.getNonresident()!=0.104) {
			System.out.println("nonresident");
			testWorks = false;
		}
		assertTrue("getSingleSchoolViewInfo works", testWorks);
	}
	
	private void testContainsUserFieldsOfStudy() {
		ArrayList<Integer> fieldIDList = new ArrayList<Integer>();
		fieldIDList.add(13); //for University of Arkansas-Fort Smith
		fieldIDList.add(47); //for University of Arkansas at Monticello
		fieldIDList.add(43); //for University of Arkansas at Little Rock & University of Arkansas at Pine Bluff
		fieldIDList.add(14); //for University of Arkansas
		Condition hasFields = schoolDAO.containsSelectedFieldOfStudy(fieldIDList);
		Condition ArkansasSchools = new Condition("school.name", CondType.LIKE, CondVal.createStrVal("University of Arkansas%"));
		Condition NotCollegeInName = new Condition("school.name NOT", CondType.LIKE, CondVal.createStrVal("%College%"));
		ArrayList<Condition> fieldsArrayList = new ArrayList<Condition>();
		fieldsArrayList.add(hasFields);
		fieldsArrayList.add(ArkansasSchools);
		fieldsArrayList.add(NotCollegeInName);
		byte allTables = 0x7;
		List<School> schools = schoolDAO.getSchools(fieldsArrayList, allTables);
		ArrayList<String> schoolNames = new ArrayList<String>();
		for (School school : schools) {
			schoolNames.add(school.getName());
		}
		ArrayList<String> compareNames = new ArrayList<String>();
		compareNames.add("University of Arkansas at Little Rock");
		compareNames.add("University of Arkansas");
		compareNames.add("University of Arkansas at Pine Bluff");
		compareNames.add("University of Arkansas at Monticello");
		compareNames.add("University of Arkansas-Fort Smith");
		assertTrue("Schools with user-selected fields of study appear", schoolNames.equals(compareNames));
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
			assertEquals("School not in CA", "CA", school.getLocation().getStateStr());
			assertTrue("In state tuition not less than 20000", school.getTuitionIn() < 20000);
		}
		
		Condition between = new Condition(School.TUITION_IN, CondType.RANGE, CondVal.createIntRangeVal(5000, 7000));
		Condition suny = new Condition("school.name", CondType.LIKE, CondVal.createStrVal("SUNY%"));
		conditions = new LinkedList<Condition>();
		conditions.add(between);
		conditions.add(suny);
		schools = schoolDAO.getSchools(conditions, SchoolDAO.NONE);
		assertEquals("School not correct", "SUNY Empire State College", schools.get(0).getName());
	}
	
	private void testWithFavs() {
		String SUNYAlfred = "SUNY College of Technology at Alfred";
		String SUNYEnvSci = "SUNY College of Environmental Science and Forestry";
		String SUNYCobleskill = "SUNY College of Agriculture and Technology at Cobleskill";
		
		////favs in top 5//
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
			assertTrue("Wrong school returned", school.getName().equals(SUNYAlfred) 
					|| school.getName().equals(SUNYEnvSci) 
					|| school.getName().equals(SUNYCobleskill));
		}
		
		////offers favs////
		//update favs
		userDAO.deleteFavField(user1, 47);
		userDAO.deleteFavField(user1, 3);
		userDAO.modifyFavField(user1, 1, 2);
		userDAO.modifyFavField(user1, 46, 1);
		//create conditions
		List<Condition> conditionsOffers = new LinkedList<Condition>();
		Condition offers = schoolDAO.favsInOffers(user1);
		conditionsOffers.add(suny);	//reuse from above
		conditionsOffers.add(offers);
		//execute
		List<School> schoolsOffers = schoolDAO.getSchools(conditionsOffers, SchoolDAO.NONE);
		assertEquals("More than three schools returned", 3, schoolsOffers.size());
		for (School school : schoolsOffers) {
			assertTrue("Wrong school returned", school.getName().equals(SUNYAlfred) 
					|| school.getName().equals(SUNYEnvSci) 
					|| school.getName().equals(SUNYCobleskill));
		}
	}
	
	private void testNullValues() {
		List<Condition> conditions = new LinkedList<Condition>();
		conditions.add(new Condition(School.ID, CondType.EQ, CondVal.createIntVal(110398)));
		List<School> schools = schoolDAO.getSchools(conditions, SchoolDAO.NONE);
		School UCHastingsLaw = schools.get(0);
		//non-null value
		assertEquals("School url not correct", "www.uchastings.edu", UCHastingsLaw.getWebsite());
		//null value
		assertFalse("Alias is not null", UCHastingsLaw.isPopProg1NotNull());
	}
	
	private void testMyScores() {
		String user = "checkmyscores";
		userDAO.createUser(user, "abcdefgh");
		userDAO.updateUser(user, 1400, 31);
		List<Condition> conditions = new LinkedList<Condition>();
		Condition sat = schoolDAO.compareMySAT(CondType.LT, user);
		Condition act = schoolDAO.compareMyACT(CondType.GT, user);
		Condition UC = new Condition("school.name", CondType.LIKE, CondVal.createStrVal("University of California%"));
		conditions.add(sat);
		conditions.add(act);
		conditions.add(UC);
		List<School> schools = schoolDAO.getSchools(conditions, SchoolDAO.NONE);
		//uncomment when ID implemented
		assertEquals("Correct ID not found", 110635, schools.get(0).getId());
		assertEquals("Correct admission rate not found", .1602, schools.get(0).getAdmissionRate(), .001);
		assertEquals("Correct SAT not found", 1382, schools.get(0).getSatAvg(), .1);
		assertEquals("Correct ACT not found", 32, schools.get(0).getActAvg(), .1);
		assertEquals("Correct school not found", "University of California-Berkeley", schools.get(0).getName());
	}
	
	private void testJoinConditions() {
		List<Condition> conditions = new LinkedList<Condition>();
		Condition SUNY = new Condition(School.NAME, CondType.LIKE, CondVal.createStrVal("SUNY%"));
		Condition gender = new Condition(School.GD_FEMALE, CondType.GT, CondVal.createDoubleVal(.59));
		Condition ethnicity = new Condition(School.ED_WHITE, CondType.GT, CondVal.createDoubleVal(.63));
		conditions.add(SUNY);
		conditions.add(gender);
		conditions.add(ethnicity);
		byte tablesToJoin = SchoolDAO.GENDER|SchoolDAO.ETHNIC;
		List<School> schools = schoolDAO.getSchools(conditions, tablesToJoin);
		assertEquals("Correct school not found", 196185, schools.get(0).getId());
		assertEquals("Correct school not found", "SUNY Oneonta", schools.get(0).getName());
		
		//region
		tablesToJoin = SchoolDAO.REGION;
		Condition region = new Condition(School.R_REGION, CondType.EQ, CondVal.createStrVal("Outlying Areas"));
		Condition name = new Condition(School.NAME, CondType.LIKE, CondVal.createStrVal("Atlantic%"));
		List<Condition> regionConditions = new LinkedList<Condition>();
		regionConditions.add(region);
		regionConditions.add(name);
		List<School> regionSchools = schoolDAO.getSchools(regionConditions, tablesToJoin);
		assertEquals("Correct school not found", "Atlantic University College", regionSchools.get(0).getName());
	}
	
	private void testOrderBy() {
		//one column ASC
		List<Condition> conditions = new LinkedList<Condition>();
		conditions.add(
				new Condition(School.NAME, CondType.LIKE, CondVal.createStrVal("University of California-Santa%")));
		List<SortColumn> cols = new LinkedList<SortColumn>();
		cols.add(new SortColumn(School.SAT_AVG, true));
		List<School> schoolsAsc = 
				schoolDAO.getSchools(conditions, SchoolDAO.NONE, cols);
		assertEquals("Order incorrect", 110714, schoolsAsc.get(0).getId());
		assertEquals("Order incorrect", 110705, schoolsAsc.get(1).getId());
		//one column DESC
		cols = new LinkedList<SortColumn>();
		cols.add(new SortColumn(School.SAT_AVG, false));
		List<School> schoolsDesc = 
				schoolDAO.getSchools(conditions, SchoolDAO.NONE, cols);
		assertEquals("Order incorrect", 110705, schoolsDesc.get(0).getId());
		assertEquals("Order incorrect", 110714, schoolsDesc.get(1).getId());
		//two columns
		//TODO SQL string looks right but can't verify with workbench cause that's not showing everything
	}
	
	private void testDistance() {
		String userName = "distTest";
		String pw = "distPW";
		userDAO.createUser(userName, pw);
		userDAO.modifyResidence(userName, "Rochester", 36, 14624);
		Condition c = schoolDAO.distanceRange(75, userName);
		List<Condition> conditions = new LinkedList<Condition>();
		conditions.add(c);
		List<School> schools = schoolDAO.getSchools(conditions, SchoolDAO.COORDINATES);
		for (School school : schools) {
			System.out.println(school.getName());
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
