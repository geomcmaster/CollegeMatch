import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

/**
 * Utility class for json processing (using gson).
 * 
 * @author Geoff
 *
 */
public class JSONUtil {
	private static final String BASE_DATA_URL = 
			"https://api.data.gov/ed/collegescorecard/v1/schools.json";
	//limiting results by predominant degree for now, we can change this
	private static final String CONDITIONS = "school.degrees_awarded.predominant=3";
	
	//all test score attributes
	private static final String SAT_ACT_SCORES = "2014.admissions.sat_scores.average.overall," 
		    + "2014.admissions.sat_scores.25th_percentile.critical_reading," 
			+ "2014.admissions.sat_scores.75th_percentile.critical_reading,"
			+ "2014.admissions.sat_scores.25th_percentile.math," 
			+ "2014.admissions.sat_scores.75th_percentile.math,"
			+ "2014.admissions.sat_scores.25th_percentile.writing," 
			+ "2014.admissions.sat_scores.75th_percentile.writing,"
			+ "2014.admissions.sat_scores.midpoint.critical_reading," 
			+ "2014.admissions.sat_scores.midpoint.math,"
			+ "2014.admissions.sat_scores.midpoint.writing," 
			+ "2014.admissions.act_scores.25th_percentile.cumulative,"
			+ "2014.admissions.act_scores.75th_percentile.cumulative," 
			+ "2014.admissions.act_scores.25th_percentile.english,"
			+ "2014.admissions.act_scores.75th_percentile.english," 
			+ "2014.admissions.act_scores.25th_percentile.math,"
			+ "2014.admissions.act_scores.75th_percentile.math," 
			+ "2014.admissions.act_scores.25th_percentile.writing,"
			+ "2014.admissions.act_scores.75th_percentile.writing," 
			+ "2014.admissions.act_scores.midpoint.cumulative,"
			+ "2014.admissions.act_scores.midpoint.english," 
			+ "2014.admissions.act_scores.midpoint.math,"
			+ "2014.admissions.act_scores.midpoint.writing,";
	
	private static final String SCHOOL = "id,school.name,school.ownership,"
			+ "school.school_url,school.alias,school.institutional_characteristics.level,"
			+ "2014.admissions.admission_rate.overall," 
			+ "2014.student.size,";
	
	private static final String DEMOGRPHICS = "2014.student.demographics.avg_family_income,"
			+ "2014.student.demographics.median_family_income," 
			+ "2014.cost.tuition.out_of_state,"
			+ "2014.cost.tuition.in_state," 
			+ "2014.student.demographics.age_entry,"
			+ "2014.student.demographics.first_generation," 
			+ "school.online_only,"
			+ "2014.student.demographics.men," 
			+ "2014.student.demographics.women,"
			+ "2014.student.demographics.race_ethnicity.white," 
			+ "2014.student.demographics.race_ethnicity.black,"
			+ "2014.student.demographics.race_ethnicity.hispanic," 
			+ "2014.student.demographics.race_ethnicity.asian,"
			+ "2014.student.demographics.race_ethnicity.aian," 
			+ "2014.student.demographics.race_ethnicity.nhpi,"
			+ "2014.student.demographics.race_ethnicity.two_or_more," 
			+ "2014.student.demographics.race_ethnicity.non_resident_alien,"
			+ "2014.student.demographics.race_ethnicity.unknown,";
	
	private static final String MAJORS = "2014.academics.program_percentage.agriculture,"
			+ "2014.academics.program_percentage.resources," 
			+ "2014.academics.program_percentage.architecture,"
			+ "2014.academics.program_percentage.ethnic_cultural_gender," 
			+ "2014.academics.program_percentage.communication,"
			+ "2014.academics.program_percentage.communications_technology," 
			+ "2014.academics.program_percentage.computer,"
			+ "2014.academics.program_percentage.personal_culinary," 
			+ "2014.academics.program_percentage.education,"
			+ "2014.academics.program_percentage.engineering," 
			+ "2014.academics.program_percentage.engineering_technology,"
			+ "2014.academics.program_percentage.language," 
			+ "2014.academics.program_percentage.family_consumer_science,"
			+ "2014.academics.program_percentage.legal," 
			+ "2014.academics.program_percentage.english,"
			+ "2014.academics.program_percentage.humanities," 
			+ "2014.academics.program_percentage.library,"
			+ "2014.academics.program_percentage.biological," 
			+ "2014.academics.program_percentage.mathematics,"
			+ "2014.academics.program_percentage.military," 
			+ "2014.academics.program_percentage.multidiscipline,"
			+ "2014.academics.program_percentage.parks_recreation_fitness," 
			+ "2014.academics.program_percentage.philosophy_religious,"
			+ "2014.academics.program_percentage.theology_religious_vocation," 
			+ "2014.academics.program_percentage.physical_science,"
			+ "2014.academics.program_percentage.science_technology," 
			+ "2014.academics.program_percentage.psychology,"
			+ "2014.academics.program_percentage.security_law_enforcement," 
			+ "2014.academics.program_percentage.public_administration_social_service,"
			+ "2014.academics.program_percentage.social_science," 
			+ "2014.academics.program_percentage.construction,"
			+ "2014.academics.program_percentage.mechanic_repair_technology," 
			+ "2014.academics.program_percentage.precision_production,"
			+ "2014.academics.program_percentage.transportation," 
			+ "2014.academics.program_percentage.visual_performing,"
			+ "2014.academics.program_percentage.health," 
			+ "2014.academics.program_percentage.business_marketing,"
			+ "2014.academics.program_percentage.history,";
	
	private static final String FINANCES_and_LOCATION = 
			"2014.earnings.6_yrs_after_entry.working_not_enrolled.mean_earnings,"
			+ "2014.cost.attendance.academic_year," 
			+ "2014.aid.loan_principal,"
			+ "school.state_fips," 
			+ "school.city,"
			+ "school.zip," 
			+ "school.region_id,"
			+ "school.state,";
	
	private static final String OFFERS =
			"2014.academics.program.bachelors.agriculture,"
			+ "2014.academics.program.bachelors.resources,"
			+ "2014.academics.program.bachelors.architecture,"
			+ "2014.academics.program.bachelors.ethnic_cultural_gender,"
			+ "2014.academics.program.bachelors.communication,"
			+ "2014.academics.program.bachelors.communication_technology,"
			+ "2014.academics.program.bachelors.computer,"
			+ "2014.academics.program.bachelors.persona_culinary,"
			+ "2014.academics.program.bachelors.education," 
			+ "2014.academics.program.bachelors.engineering,"
			+ "2014.academics.program.bachelors.engineering_technology,"
			+ "2014.academics.program.bachelors.language,"
			+ "2014.academics.program.bachelors.family_consumer_science,"
			+ "2014.academics.program.bachelors.legal,"
			+ "2014.academics.program.bachelors.english,"
			+ "2014.academics.program.bachelors.humanities,"
			+ "2014.academics.program.bachelors.library,"
			+ "2014.academics.program.bachelors.biological,"
			+ "2014.academics.program.bachelors.mathematics,"
			+ "2014.academics.program.bachelors.military,"
			+ "2014.academics.program.bachelors.multidiscipline,"
			+ "2014.academics.program.bachelors.parks_recreation_fitness,"
			+ "2014.academics.program.bachelors.philosophy_religious,"
			+ "2014.academics.program.bachelors.theology_religious_vocation,"
			+ "2014.academics.program.bachelors.physical_science,"
			+ "2014.academics.program.bachelors.science_technology,"
			+ "2014.academics.program.bachelors.psychology,"
			+ "2014.academics.program.bachelors.security_law_enforcement,"
			+ "2014.academics.program.bachelors.public_administration_social_service,"
			+ "2014.academics.program.bachelors.social_science,"
			+ "2014.academics.program.bachelors.construction,"
			+ "2014.academics.program.bachelors.mechanic_repair_technology,"
			+ "2014.academics.program.bachelors.precision_production,"
			+ "2014.academics.program.bachelors.transportation,"
			+ "2014.academics.program.bachelors.visual_performing,"
			+ "2014.academics.program.bachelors.health,"
			+ "2014.academics.program.bachelors.business_marketing,"
			+ "2014.academics.program.bachelors.history";
	
	private static final String ATTRIBUTES = 
			SCHOOL + SAT_ACT_SCORES + DEMOGRPHICS + MAJORS + FINANCES_and_LOCATION;
	
	private final String API_KEY;
	private DBUtil dbUtil;
	private int currentPage;	//page of results
	private int perPage;		//number of results per page
	private int numPages;		//number of total pages with data
	
	/**
	 * 
	 * @param apiKey API key for the dataset
	 * @param dbUtil DBUtil to use for processing
	 */
	public JSONUtil(String apiKey, DBUtil dbUtil) {
		currentPage = 0;
		API_KEY = apiKey;
		this.dbUtil = dbUtil;
	}
	
	/**
	 * Process everything in the dataset.
	 * 
	 */
	public void processAllRecords() {
		//limiting to schools that predominately award bachelors (for now at least)
		String baseUrl = BASE_DATA_URL + "?" + CONDITIONS + 
				"&_fields=" + ATTRIBUTES + "&api_key=" + API_KEY + "&_page=";
		       
	    Root root = getRoot(baseUrl);
	    
	    setPageData(root);
	    processPage(root);
	    currentPage++;
	    
	    for (;currentPage < numPages; currentPage++) {     
		    processPage(getRoot(baseUrl));
	    }

	}
	
	/**
	 * Sets perPage and numPages based on metadata
	 * 
	 * @param root The root element
	 */
	private void setPageData(Root root) {
		perPage = root.metadata.per_page;
	    numPages = (root.metadata.total / perPage) + (root.metadata.total % perPage == 0 ? 0 : 1);
	}
	
	/**
	 * Populates objects from json and returns the root
	 * 
	 * @param url The url to use
	 * @return The root of the json at the url
	 */
	private Root getRoot(String url) {
		String json = readAsString(url + currentPage);
		Gson gson = new Gson();
		return gson.fromJson(json, Root.class);
	}
	
	/**
	 * Processes a single page of results
	 * 
	 * @param root Root element of this page
	 */
	private void processPage(Root root) {
		for (Result result : root.results) {
	    	//call dbUtil.processSchool(result) to load all tables
			//with each school i.e. result's information
			dbUtil.processSchool(result);
			System.out.println(result.schoolName);
		}
	}
	
	/**
	 * Returns the json at a given url
	 * 
	 * @param strUrl The json url
	 * @return The json returned from the url
	 */
	private String readAsString(String strUrl) {
		String json = "";
		
		try {
			URL url = new URL(strUrl);
			json = IOUtils.toString(((HttpsURLConnection)url.openConnection()).getInputStream());
		} catch (MalformedURLException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	    
	    return json;
	}
	
}
