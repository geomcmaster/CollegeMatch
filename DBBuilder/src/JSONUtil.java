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
	private static final String ATTRIBUTES = "id,school.name,school.ownership,"
			+ "school.school_url,school.alias,school.institutional_characteristics.level,"
			+ "admissions.admission_rate.overall";
	
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
	    //TODO open a database connection
	    processPage(root);
	    currentPage++;
	    
	    for (;currentPage < numPages; currentPage++) {     
		    processPage(getRoot(baseUrl));
	    }
	    //TODO close connection

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
	    	//for now we just print the school name
	    	//when database stuff is ready, we'll call a method in DBUtil class
	    	//		to populate db from a result
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
