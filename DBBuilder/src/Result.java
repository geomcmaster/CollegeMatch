import com.google.gson.annotations.SerializedName;

/**
 * Represents a result element, i.e. a school. Fields will be expanded to include
 * all desired attributes of a result.
 * 
 * @author Geoff
 *
 */
public class Result {
	int id;
	
	//must use annotation for attribute names with dots so gson can parse it
	@SerializedName("school.name")
    String schoolName;
	
	@SerializedName("school.ownership")
	int schoolOwnership;
	
	@SerializedName("school.school_url")
	String schoolURL;
	
	@SerializedName("school.alias")
	String schoolAlias;
	
	@SerializedName("school.institutional_characteristics.level")
	int schoolLevel;
	
	@SerializedName("admissions.admission_rate.overall")
	double admissionRate;
	
	
}
