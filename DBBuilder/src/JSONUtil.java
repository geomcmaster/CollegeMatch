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
//TODO once the attributes we need are determined, create constants for that part of the string
	
	private int currentPage;	//page of results
	private int per_page;		//number of results per page
	private int numPages;		//number of total pages with data
	
	/**
	 * Set current page of results to 0
	 */
	public JSONUtil() {
		currentPage = 0;
	}
	
	/**
	 * Process everything in the dataset.
	 * 
	 * @param apiKey API key for the dataset
	 */
	public void processAllRecords(String apiKey) {
		//limiting to schools that predominately award bachelors (for now at least)
		String baseUrl = BASE_DATA_URL + "?school.degrees_awarded.predominant=3&_fields=id,school.name&api_key=" + apiKey + "&_page=";
		
		String json = readAsString(baseUrl + currentPage);
	    
	    Gson gson = new Gson();        
	    Root root = gson.fromJson(json, Root.class);
	    
	    per_page = root.metadata.per_page;
	    numPages = (root.metadata.total / per_page) + (root.metadata.total % per_page == 0 ? 0 : 1);
	    
	    for (Result result : root.results) {
	    	System.out.println(result.schoolName);
	    }
	    currentPage++;
	    
	    for (;currentPage < numPages; currentPage++) {
	    	
	    	json = readAsString(baseUrl + currentPage);
		    
		    gson = new Gson();        
		    root = gson.fromJson(json, Root.class);
		    
		    for (Result result : root.results) {
		    	//for now we just print the school name
		    	//when database stuff is ready, we'll call a method in DBUtil class
		    	//		to populate db from a result
		    	System.out.println(result.schoolName);
		    }
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
