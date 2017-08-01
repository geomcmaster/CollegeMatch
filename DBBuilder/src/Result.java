import com.google.gson.annotations.SerializedName;
import java.util.*;

/**
 * Represents a result element, i.e. a school. Fields will be expanded to include
 * all desired attributes of a result.
 * 
 * @author Geoff
 *
 */
public class Result {
	int id;
	
	public static final int AGRICULTURE = 1;
	public static final int NATURALRESOURCES = 3;
	public static final int ARCHITECTURE = 4;
	public static final int CULTUREGENDER = 5;
	public static final int COMMUNICATION = 9;
	public static final int COMMUNICATIONSTECHNOLOGY = 10;
	public static final int COMPUTER_AND_INFORMATION_SCIENCE = 11;
	public static final int PERSONALCULINARY = 12;
	public static final int EDUCATION = 13;
	public static final int ENGINEERING = 14;
	public static final int ENGINEERING_TECHNOLOGY = 15;
	public static final int FOREIGNLANGUAGE_AND_LINGUISTICS = 16;
	public static final int FAMILYCONSUMERSCIENCE = 19;
	public static final int LEGAL = 22;
	public static final int ENGLISH = 23;
	public static final int LIBERALARTSHUMANITIES = 24;
	public static final int LIBRARYSCIENCE = 25;
	public static final int BIOLOGICALSCIENCES = 26;
	public static final int MATHEMATICS_AND_STATISTICS = 27;
	public static final int MILITARYTECHNOLOGIES = 29;
	public static final int MULTIDISCIPLINARYSTUDIES = 30;
	public static final int PARKSRECREATIONLEISUREFITNESSSTUDIES = 31;
	public static final int PHILOSOPHY_AND_RELIGION = 38;
	public static final int THEOLOGYVOCATION = 39;
	public static final int PHYSICALSCIENCE = 40;
	public static final int SCIENCETECHNOLOGY = 41;
	public static final int PSYCHOLOGY = 42;
	public static final int SECURITYLAWENFORCEMENT = 43;
	public static final int PUBLICADMINSOCIALSERVICE = 44;
	public static final int SOCIALSCIENCE = 45;
	public static final int CONSTRUCTION = 46;
	public static final int MECHANICREPAIRTECHNOLOGY = 47;
	public static final int PRECISIONPRODUCTION = 48;
	public static final int TRANSPORTATION = 49;
	public static final int VISUALPERFORMING = 50;
	public static final int HEALTH = 51;
	public static final int BUSINESSMARKETING = 52;
	public static final int HISTORY = 54;
	
	//must use annotation for attribute names with dots so gson can parse it
	
	/*School entity attributes
	 */
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
	@SerializedName("2014.admissions.admission_rate.overall")
	double admissionRate;
	@SerializedName("2014.earnings.6_yrs_after_entry.working_not_enrolled.mean_earnings")
	int moneyEarnings_6yrs_avg;
	@SerializedName("2014.cost.attendance.academic_year")
	int moneyAvgCost;
	@SerializedName("2014.aid.loan_principal")
	double moneyMedianLoan;
	//# of undergrads seeking certification/degree
	@SerializedName("2014.student.size")
	int schoolStudentSize;
	@SerializedName("2014.student.demographics.avg_family_income")
	int moneyFamilyIncome_avg;
	@SerializedName("2014.student.demographics.median_family_income")
	int moneyFamilyIncome_median;
	@SerializedName("2014.cost.tuition.out_of_state")
	int moneyTuition_and_fees_out_of_state;
	@SerializedName("2014.cost.tuition.in_state")
	int moneyTuition_and_fees_in_state;
	//average age of student entry
	@SerializedName("2014.student.demographics.age_entry")
	int studentEntryAge;
	//percentage share of 1st-generation students
	@SerializedName("2014.student.demographics.first_generation")
	double student_first_generation_students_share;
	@SerializedName("school.online_only")
	int schoolDistanceLearning;
	@SerializedName("2014.student.demographics.men")
	double students_male;
	@SerializedName("2014.student.demographics.women")
	double students_female;
	@SerializedName("2014.student.demographics.race_ethnicity.white")
	double students_white;
	@SerializedName("2014.student.demographics.race_ethnicity.black")
	double students_black;
	@SerializedName("2014.student.demographics.race_ethnicity.hispanic")
	double students_hispanic;
	@SerializedName("2014.student.demographics.race_ethnicity.asian")
	double students_asian;
	//Amerindian/Alaskan Native
	@SerializedName("2014.student.demographics.race_ethnicity.aian")
	double students_aian;
	//Native Hawaiian/Pacific Islander
	@SerializedName("2014.student.demographics.race_ethnicity.nhpi")
	double students_nhpi;
	@SerializedName("2014.student.demographics.race_ethnicity.two_or_more")
	double students_two_or_more;
	@SerializedName("2014.student.demographics.race_ethnicity.non_resident_alien")
	double students_non_resident_alien;
	@SerializedName("2014.student.demographics.race_ethnicity.unknown")
	double students_unknown;
	
	/*
	 * SAT_pct_25, SAT_pct_75, ACT_pct_25, ACT_pct_75
	 */
	@SerializedName("2014.admissions.sat_scores.average.overall")
	double SAT_avg;
	@SerializedName("2014.admissions.sat_scores.25th_percentile.critical_reading")
	double SAT_25_reading;
	@SerializedName("2014.admissions.sat_scores.75th_percentile.critical_reading")
	double SAT_75_reading;
	@SerializedName("2014.admissions.sat_scores.25th_percentile.math")
	double SAT_25_math;
	@SerializedName("2014.admissions.sat_scores.75th_percentile.math")
	double SAT_75_math;
	@SerializedName("2014.admissions.sat_scores.25th_percentile.writing")
	double SAT_25_writing;
	@SerializedName("2014.admissions.sat_scores.75th_percentile.writing")
	double SAT_75_writing;
	@SerializedName("2014.admissions.sat_scores.midpoint.critical_reading")
	double SAT_midpoint_reading;
	@SerializedName("2014.admissions.sat_scores.midpoint.math")
	double SAT_midpoint_math;
	@SerializedName("2014.admissions.sat_scores.midpoint.writing")
	double SAT_midpoint_writing;
	@SerializedName("2014.admissions.act_scores.25th_percentile.cumulative")
	double ACT_25_cumulative;
	@SerializedName("2014.admissions.act_scores.75th_percentile.cumulative")
	double ACT_75_cumulative;
	@SerializedName("2014.admissions.act_scores.25th_percentile.english")
	double ACT_25_english;
	@SerializedName("2014.admissions.act_scores.75th_percentile.english")
	double ACT_75_english;
	@SerializedName("2014.admissions.act_scores.25th_percentile.math")
	double ACT_25_math;
	@SerializedName("2014.admissions.act_scores.75th_percentile.math")
	double ACT_75_math;
	@SerializedName("2014.admissions.act_scores.25th_percentile.writing")
	double ACT_25_writing;
	@SerializedName("2014.admissions.act_scores.75th_percentile.writing")
	double ACT_75_writing;
	@SerializedName("2014.admissions.act_scores.midpoint.cumulative")
	double ACT_avg;
	@SerializedName("2014.admissions.act_scores.midpoint.english")
	double ACT_midpoint_english;
	@SerializedName("2014.admissions.act_scores.midpoint.math")
	double ACT_midpoint_math;
	@SerializedName("2014.admissions.act_scores.midpoint.writing")
	double ACT_midpoint_writing;
	
	//Popular Programs: pop_prog_1, pop_prog_2, pop_prog_3, pop_prog_4, pop_prog_5
	@SerializedName("2014.academics.program_percentage.agriculture")
	double studyAgriculture;
	//natural resources
	@SerializedName("2014.academics.program_percentage.resources")
	double studyNaturalResources;
	@SerializedName("2014.academics.program_percentage.architecture")
	double studyArchitecture;
	@SerializedName("2014.academics.program_percentage.ethnic_cultural_gender")
	double studyCultureGender;
	@SerializedName("2014.academics.program_percentage.communication")
	double studyCommunication;
	@SerializedName("2014.academics.program_percentage.communications_technology")
	double studyCommunicationsTechnology;
	@SerializedName("2014.academics.program_percentage.computer")
	double studyComputer_and_Information_Science;
	@SerializedName("2014.academics.program_percentage.personal_culinary")
	double studyPersonalCulinary;
	@SerializedName("2014.academics.program_percentage.education")
	double studyEducation;
	@SerializedName("2014.academics.program_percentage.engineering")
	double studyEngineering;
	@SerializedName("2014.academics.program_percentage.engineering_technology")
	double studyEngineering_technology;
	@SerializedName("2014.academics.program_percentage.language")
	double studyForeignLanguage_and_Linguistics;
	@SerializedName("2014.academics.program_percentage.family_consumer_science")
	double studyFamilyConsumerScience;
	@SerializedName("2014.academics.program_percentage.legal")
	double studyLegal;
	@SerializedName("2014.academics.program_percentage.english")
	double studyEnglish;
	@SerializedName("2014.academics.program_percentage.humanities")
	double studyLiberalArtsHumanities;
	@SerializedName("2014.academics.program_percentage.library")
	double studyLibraryScience;
	@SerializedName("2014.academics.program_percentage.biological")
	double studyBiologicalSciences;
	@SerializedName("2014.academics.program_percentage.mathematics")
	double studyMathematics_and_Statistics;
	@SerializedName("2014.academics.program_percentage.military")
	double studyMilitaryTechnologies;
	@SerializedName("2014.academics.program_percentage.multidiscipline")
	double studyMultidisciplinaryStudies;
	@SerializedName("2014.academics.program_percentage.parks_recreation_fitness")
	double studyParksRecreationLeisureFitnessStudies;
	@SerializedName("2014.academics.program_percentage.philosophy_religious")
	double studyPhilosophy_and_Religion;
	@SerializedName("2014.academics.program_percentage.theology_religious_vocation")
	double studyTheologyVocation;
	@SerializedName("2014.academics.program_percentage.physical_science")
	double studyPhysicalScience;
	@SerializedName("2014.academics.program_percentage.science_technology")
	double studyScienceTechnology;
	@SerializedName("2014.academics.program_percentage.psychology")
	double studyPsychology;
	@SerializedName("2014.academics.program_percentage.security_law_enforcement")
	double studySecurityLawEnforcement;
	@SerializedName("2014.academics.program_percentage.public_administration_social_service")
	double studyPublicAdminSocialService;
	@SerializedName("2014.academics.program_percentage.social_science")
	double studySocialScience;
	@SerializedName("2014.academics.program_percentage.construction")
	double studyConstruction;
	@SerializedName("2014.academics.program_percentage.mechanic_repair_technology")
	double studyMechanicRepairTechnology;
	@SerializedName("2014.academics.program_percentage.precision_production")
	double studyPrecisionProduction;
	@SerializedName("2014.academics.program_percentage.transportation")
	double studyTransportation;
	@SerializedName("2014.academics.program_percentage.visual_performing")
	double studyVisualPerforming;
	@SerializedName("2014.academics.program_percentage.health")
	double studyHealth;
	@SerializedName("2014.academics.program_percentage.business_marketing")
	double studyBusinessMarketing;
	@SerializedName("2014.academics.program_percentage.history")
	double studyHistory;
	
	//location entity attributes
	//state FIPS codes e.g. 1="Alabama"
	@SerializedName("school.state_fips")
	int locationState;
	//WARNING: many schools don't have this string value
	@SerializedName("school.state")
	String locationStateString;
	@SerializedName("school.city")
	String locationCity;
	@SerializedName("school.zip")
	int locationZIP;
	@SerializedName("school.region_id")
	int locationRegionID;
	
	//Offers fields of study attributes
	@SerializedName("2014.academics.program.bachelors.agriculture")
	int offersAgriculture;
	@SerializedName("2014.academics.program.bachelors.resources")
	int offersResources;
	@SerializedName("2014.academics.program.bachelors.architecture")
	int offersArchitecture;
	@SerializedName("2014.academics.program.bachelors.ethnic_cultural_gender")
	int offersCultureGender;
	@SerializedName("2014.academics.program.bachelors.communication")
	int offersCommunication;
	@SerializedName("2014.academics.program.bachelors.communication_technology")
	int offersCommTech;
	@SerializedName("2014.academics.program.bachelors.computer")
	int offersCS;
	@SerializedName("2014.academics.program.bachelors.persona_culinary")
	int offersCulinary;
	@SerializedName("2014.academics.program.bachelors.education")
	int offersEducation;
	@SerializedName("2014.academics.program.bachelors.engineering")
	int offersEngineering;
	@SerializedName("2014.academics.program.bachelors.engineering_technology")
	int offersEngineeringTech;
	@SerializedName("2014.academics.program.bachelors.language")
	int offersLanguage;
	@SerializedName("2014.academics.program.bachelors.family_consumer_science")
	int offersFamilyConsumerScience;
	@SerializedName("2014.academics.program.bachelors.legal")
	int offersLegal;
	@SerializedName("2014.academics.program.bachelors.english")
	int offersEnglish;
	@SerializedName("2014.academics.program.bachelors.humanities")
	int offersHumanities;
	@SerializedName("2014.academics.program.bachelors.library")
	int offersLibrary;
	@SerializedName("2014.academics.program.bachelors.biological")
	int offersBiologicalSciences;
	@SerializedName("2014.academics.program.bachelors.mathematics")
	int offersMath;
	@SerializedName("2014.academics.program.bachelors.military")
	int offersMilitary;
	@SerializedName("2014.academics.program.bachelors.multidiscipline")
	int offersMultidiscipline;
	@SerializedName("2014.academics.program.bachelors.parks_recreation_fitness")
	int offersParksAndRec;
	@SerializedName("2014.academics.program.bachelors.philosophy_religious")
	int offersPhilRel;
	@SerializedName("2014.academics.program.bachelors.theology_religious_vocation")
	int offersTheology;
	@SerializedName("2014.academics.program.bachelors.physical_science")
	int offersPhySci;
	@SerializedName("2014.academics.program.bachelors.science_technology")
	int offersSciTech;
	@SerializedName("2014.academics.program.bachelors.psychology")
	int offersPsych;
	@SerializedName("2014.academics.program.bachelors.security_law_enforcement")
	int offersLawEnforce;
	@SerializedName("2014.academics.program.bachelors.public_administration_social_service")
	int offersPublicAdmin;
	@SerializedName("2014.academics.program.bachelors.social_science")
	int offersSocialSci;
	@SerializedName("2014.academics.program.bachelors.construction")
	int offersConstruct;
	@SerializedName("2014.academics.program.bachelors.mechanic_repair_technology")
	int offersMechTech;
	@SerializedName("2014.academics.program.bachelors.precision_production")
	int offersPrecProd;
	@SerializedName("2014.academics.program.bachelors.transportation")
	int offersTransport;
	@SerializedName("2014.academics.program.bachelors.visual_performing")
	int offersPerforming;
	@SerializedName("2014.academics.program.bachelors.health")
	int offersHealth;
	@SerializedName("2014.academics.program.bachelors.business_marketing")
	int offersBusinessMarketing;
	@SerializedName("2014.academics.program.bachelors.history")
	int offersHistory;
	
	//Method for getting an ascending sorted ArrayList<Double> with
	//top 5 fields of study for school
	public ArrayList<Integer> topFiveFields() {
	ArrayList<Double> studyList = new ArrayList<Double>();
	ArrayList<Integer> studyListInt = new ArrayList<Integer>();
	ArrayList<Integer> topFiveFieldsInt = new ArrayList<Integer>();
	studyList.add(studyAgriculture);
	studyList.add(studyNaturalResources);
	studyList.add(studyArchitecture);
	studyList.add(studyCultureGender);
	studyList.add(studyCommunication);
	studyList.add(studyCommunicationsTechnology);
	studyList.add(studyComputer_and_Information_Science);
	studyList.add(studyPersonalCulinary);;
	studyList.add(studyEducation);
	studyList.add(studyEngineering);
	studyList.add(studyEngineering_technology);
	studyList.add(studyForeignLanguage_and_Linguistics);
	studyList.add(studyFamilyConsumerScience);
	studyList.add(studyLegal);
	studyList.add(studyEnglish);
	studyList.add(studyLiberalArtsHumanities);
	studyList.add(studyLibraryScience);
	studyList.add(studyBiologicalSciences);
	studyList.add(studyMathematics_and_Statistics);
	studyList.add(studyMilitaryTechnologies);
	studyList.add(studyMultidisciplinaryStudies);
	studyList.add(studyParksRecreationLeisureFitnessStudies);
	studyList.add(studyPhilosophy_and_Religion);
	studyList.add(studyTheologyVocation);
	studyList.add(studyPhysicalScience);
	studyList.add(studyScienceTechnology);
	studyList.add(studyPsychology);
	studyList.add(studySecurityLawEnforcement);
	studyList.add(studyPublicAdminSocialService);
	studyList.add(studySocialScience);
	studyList.add(studyConstruction);
	studyList.add(studyMechanicRepairTechnology);
	studyList.add(studyPrecisionProduction);
	studyList.add(studyTransportation);
	studyList.add(studyVisualPerforming);
	studyList.add(studyHealth);
	studyList.add(studyBusinessMarketing);
	studyList.add(studyHistory);
	for (int counter = 1; counter <= studyList.size(); counter++) {
		studyListInt.add(counter);
	}
	for (int j = 0; j < 5; j++) {
		double max = 0;
		int maxInt = 0;
		int removalInt = 0;
		for (int i = 0; i < studyList.size(); i++) {
			if (max < studyList.get(i)) {
				max = studyList.get(i);
				maxInt = studyListInt.get(i);
				removalInt = i;
			}
		}
		studyList.remove(removalInt);
		studyListInt.remove(removalInt);
		topFiveFieldsInt.add(maxInt);
	}
	return topFiveFieldsInt;
	}
}


