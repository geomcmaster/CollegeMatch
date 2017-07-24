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
	double moneyEarnings_7yrs_avg;
	@SerializedName("2014.cost.attendance.academic_year")
	double moneyAvgCost;
	@SerializedName("2014.aid.loan_principal")
	double moneyMedianLoan;
	//# of undergrads seeking certification/degree
	@SerializedName("2014.student.size")
	int schoolStudentSize;
	@SerializedName("2014.student.demographics.avg_family_income")
	double moneyFamilyIncome_avg;
	@SerializedName("2014.student.demographics.median_family_income")
	double moneyFamilyIncome_median;
	@SerializedName("2014.cost.tuition.out_of_state")
	double moneyTuition_out_of_state;
	@SerializedName("2014.cost.tuition.in_state")
	double moneyTuition_in_state;
	//average age of student entry
	@SerializedName("2014.student.demographics.age_entry")
	int studentEntryAge;
	//percentage share of 1st-generation students
	@SerializedName("2014.student.demographics.first_generation")
	double student_first_generation_students;
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
	double SAT_reading_avg;
	@SerializedName("2014.admissions.sat_scores.midpoint.math")
	double SAT_math_avg;
	@SerializedName("2014.admissions.sat_scores.midpoint.writing")
	double SAT_writing_avg;
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
	
}
