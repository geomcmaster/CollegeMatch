
public class JSONUtil {
	private static final String BASE_DATA_URL = 
			"https://api.data.gov/ed/collegescorecard/v1/schools.json";
	private static final String OPTION_PARAM_INDICATOR = "_fields=";
	private static final String OPTION_PARAMS = "";	//attributes to return
	private static final String FIELD_PARAMS = "";	//return only results matching on these values
	public String query;
	public int currentPage;
	
	public JSONUtil() {
		query = BASE_DATA_URL + "?" + FIELD_PARAMS + "&" + OPTION_PARAM_INDICATOR + OPTION_PARAMS;
		currentPage = 0;
	}
	
	//not sure how I'm going to implement rest of JSON reading API yet, but remember to add 
	//"_page=" as we loop. This should be the only thing that changes?
}
