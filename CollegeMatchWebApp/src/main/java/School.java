package main.java;

/**
 * Represents a school record
 * 
 * @author Geoff
 *
 */
public class School {
	public static final String ID = "school.id";
	public static final String NAME = "school.name";
	public static final String URL = "school.url";
	public static final String ALIAS = "school.alias";
	public static final String SAT_AVG = "school.SAT_avg";
	public static final String SAT_25 = "school.SAT_pct_25_cumulative";
	public static final String SAT_75 = "school.SAT_pct_75_cumulative";
	public static final String SAT_25_RD = "school.SAT_pct_25_reading";
	public static final String SAT_75_RD = "school.SAT_pct_75_reading";
	public static final String SAT_25_MATH = "school.SAT_pct_25_math";
	public static final String SAT_75_MATH = "school.SAT_pct_75_math";
	public static final String SAT_25_WR = "school.SAT_pct_25_writing";
	public static final String SAT_75_WR = "school.SAT_pct_75_writing";
	public static final String SAT_MED_RD = "school.SAT_midpoint_reading";
	public static final String SAT_MED_MATH = "school.SAT_midpoint_math";
	public static final String SAT_MED_WR = "school.SAT_midpoint_writing";
	public static final String ACT_25 = "school.ACT_pct_25_cumulative";
	public static final String ACT_75 = "school.ACT_pct_75_cumulative";
	public static final String ACT_25_ENG = "school.ACT_pct_25_english";
	public static final String ACT_75_ENG = "school.ACT_pct_75_english";
	public static final String ACT_25_MATH = "school.ACT_pct_25_math";
	public static final String ACT_75_MATH = "school.ACT_pct_75_math";
	public static final String ACT_25_WR = "school.ACT_pct_25_writing";
	public static final String ACT_75_WR = "school.ACT_pct_75_writing";
	public static final String ACT_AVG = "school.ACT_avg";
	public static final String ACT_MED_ENG = "school.ACT_midpoint_english";
	public static final String ACT_MED_MATH = "school.ACT_midpoint_math";
	public static final String ACT_MED_WR = "school.ACT_midpoint_writing";
	public static final String AVG_EARN = "school.avg_earnings_6_years_after_matriculation";
	public static final String AVG_COST = "school.avg_cost";
	public static final String CONTROL = "school.control";
	public static final String MED_DEBT = "school.med_debt";
	public static final String STD_BODY_SIZE = "school.std_bdy_sz";
	public static final String PROG_1 = "school.pop_prog_1";
	public static final String PROG_2 = "school.pop_prog_2";
	public static final String PROG_3 = "school.pop_prog_3";
	public static final String PROG_4 = "school.pop_prog_4";
	public static final String PROG_5 = "school.pop_prog_5";
	public static final String ADM_RATE = "school.adm_rate";
	public static final String AVG_FAM_INC = "school.avg_fam_inc";
	public static final String MED_FAM_INC = "school.med_fam_inc";
	public static final String TUITION_OUT = "school.tuition_and_fees_out";
	public static final String TUITION_IN = "school.tuition_and_fees_in";
	public static final String AVG_AGE_ENTRY = "school.avg_entry_age";
	public static final String FIRST_GEN = "school.1_gen_std_share";
	public static final String LEVEL = "school.level";
	public static final String DIST_LEARNING = "school.dist_learning";
	public static final String GENDER_DEM_ID = "school.GenderDemographics_ID";
	public static final String ETHNIC_DEM_ID = "school.EthnicDemographics_ID";
	//school_loc
	public static final String SL_SCHOOL_ID = "school_loc.school_ID";
	public static final String SL_LOC_ID = "school_loc.loc_ID";
	//location
	public static final String L_ID = "location.ID";
	public static final String L_CITY = "location.city";
	public static final String L_STATE_STRING = "location.state_string";
	public static final String L_STATE = "location.state";
	public static final String L_ZIP = "location.ZIP";
	//region
	public static final String R_STATE = "region.state";
	public static final String R_REGION = "region.region_name";
	//gender demographics
	public static final String GD_ID = "genderDemographics.ID";
	public static final String GD_FEMALE = "genderDemographics.female";
	public static final String GD_MALE = "genderDemographics.male";
	//ethnic demographics
	public static final String ED_ID = "ethnicDemographics.ID";
	public static final String ED_WHITE = "ethnicDemographics.white";
	public static final String ED_BLACK = "ethnicDemographics.black";
	public static final String ED_HISPANIC = "ethnicDemographics.hispanic";
	public static final String ED_ASIAN = "ethnicDemographics.asian";
	public static final String ED_AM_IND = "ethnicDemographics.american_indian_alaskan_native";
	public static final String ED_HAW_PAC_ISL = "ethnicDemographics.native_hawaiian_pacific_islander";
	public static final String ED_TWO_OR_MORE = "ethnicDemographics.two_or_more";
	public static final String ED_UNKNOWN = "ethnicDemographics.unknown";
	public static final String ED_NONRES = "ethnicDemographics.nonresident";
	//offers
	public static final String O_SCHOOL = "offers.school_ID";
	public static final String O_FIELD = "offers.field_ID";
	//fields of study
	public static final String FOS_ID = "fieldsOfStudy.ID";
	public static final String FOS_NAME = "fieldsOfStudy.name";
	
	private String name;
	private boolean nameIsNotNull;
	private String website;
	private boolean websiteIsNotNull;
	private Location location;
	private boolean locationIsNotNull;
	private double admissionRate;
	private boolean admissionRateIsNotNull;
	private int avgCost;
	private boolean avgCostIsNotNull;
	private double sat25;
	private boolean sat25IsNotNull;
	private double sat75;
	private boolean sat75IsNotNull;
	private double satAvg;
	private boolean satAvgIsNotNull;
	private double act25;
	private boolean act25IsNotNull;
	private double act75;
	private boolean act75IsNotNull;
	private double actAvg;
	private boolean actAvgIsNotNull;
	private int avgEarnings;
	private boolean avgEarningsIsNotNull;
	private int stdBodySz;
	private boolean stdBodySzIsNotNull;
	private String popProg1;
	private boolean popProg1IsNotNull;
	private String popProg2;
	private boolean popProg2IsNotNull;
	private String popProg3;
	private boolean popProg3IsNotNull;
	private String popProg4;
	private boolean popProg4IsNotNull;
	private String popProg5;
	private boolean popProg5IsNotNull;
	private int tuitionIn;
	private boolean tuitionInIsNotNull;
	private int tuitionOut;
	private boolean tuitionOutIsNotNull;
	private double medDebt;
	private boolean medDebtIsNotNull;
	private int avgFamIncome;
	private boolean avgFamIncomeIsNotNull;
	private int medFamIncome;
	private boolean medFamIncomeIsNotNull;
	private int avgAge;
	private boolean avgAgeIsNotNull;
	private double firstGenStudentShare;
	private boolean firstGenStudentShareIsNotNull;
	//Maybe include distance learning as boolean variable?
	private double maleShare;
	private boolean maleShareIsNotNull;
	private double femaleShare;
	private boolean femaleShareIsNotNull;
	
	public School() {
		setNulls();
	}
	
	private void setNulls() {
		setNameIsNotNull(false);
		setWebsiteIsNotNull(false);
		setLocationIsNotNull(false);
		setAdmissionRateIsNotNull(false);
		setAvgCostIsNotNull(false);
		setSat25IsNotNull(false);
		setSat75IsNotNull(false);
		setSatAvgIsNotNull(false);
		setAct25IsNotNull(false);
		setAct75IsNotNull(false);
		setActAvgIsNotNull(false);
		setAvgEarningsIsNotNull(false);
		setStdBodySzIsNotNull(false);
		setPopProg1IsNotNull(false);
		setPopProg2IsNotNull(false);
		setPopProg3IsNotNull(false);
		setPopProg4IsNotNull(false);
		setPopProg5IsNotNull(false);
		setTuitionInIsNotNull(false);
		setTuitionOutIsNotNull(false);
		setMedDebtIsNotNull(false);
		setAvgFamIncomeIsNotNull(false);
		setMedFamIncomeIsNotNull(false);
		setAvgAgeIsNotNull(false);
		setFirstGenStudentShareIsNotNull(false);
		setMaleShareIsNotNull(false);
		setFemaleShareIsNotNull(false);
	}
	
	/**
	 * @return the nameIsNotNull
	 */
	public boolean isNameNotNull() {
		return nameIsNotNull;
	}
	/**
	 * @param nameIsNotNull the nameIsNotNull to set
	 */
	public void setNameIsNotNull(boolean nameIsNotNull) {
		this.nameIsNotNull = nameIsNotNull;
	}
	/**
	 * @return the websiteIsNotNull
	 */
	public boolean isWebsiteNotNull() {
		return websiteIsNotNull;
	}
	/**
	 * @param websiteIsNotNull the websiteIsNotNull to set
	 */
	public void setWebsiteIsNotNull(boolean websiteIsNotNull) {
		this.websiteIsNotNull = websiteIsNotNull;
	}
	/**
	 * @return the locationIsNotNull
	 */
	public boolean isLocationNotNull() {
		return locationIsNotNull;
	}
	/**
	 * @param locationIsNotNull the locationIsNotNull to set
	 */
	public void setLocationIsNotNull(boolean locationIsNotNull) {
		this.locationIsNotNull = locationIsNotNull;
	}
	/**
	 * @return the admissionRateIsNotNull
	 */
	public boolean isAdmissionRateNotNull() {
		return admissionRateIsNotNull;
	}
	/**
	 * @param admissionRateIsNotNull the admissionRateIsNotNull to set
	 */
	public void setAdmissionRateIsNotNull(boolean admissionRateIsNotNull) {
		this.admissionRateIsNotNull = admissionRateIsNotNull;
	}
	/**
	 * @return the avgCostIsNotNull
	 */
	public boolean isAvgCostNotNull() {
		return avgCostIsNotNull;
	}
	/**
	 * @param avgCostIsNotNull the avgCostIsNotNull to set
	 */
	public void setAvgCostIsNotNull(boolean avgCostIsNotNull) {
		this.avgCostIsNotNull = avgCostIsNotNull;
	}
	/**
	 * @return the sat25IsNotNull
	 */
	public boolean isSat25NotNull() {
		return sat25IsNotNull;
	}
	/**
	 * @param sat25IsNotNull the sat25IsNotNull to set
	 */
	public void setSat25IsNotNull(boolean sat25IsNotNull) {
		this.sat25IsNotNull = sat25IsNotNull;
	}
	/**
	 * @return the sat75IsNotNull
	 */
	public boolean isSat75NotNull() {
		return sat75IsNotNull;
	}
	/**
	 * @param sat75IsNotNull the sat75IsNotNull to set
	 */
	public void setSat75IsNotNull(boolean sat75IsNotNull) {
		this.sat75IsNotNull = sat75IsNotNull;
	}
	/**
	 * @return the satAvgIsNotNull
	 */
	public boolean isSatAvgNotNull() {
		return satAvgIsNotNull;
	}
	/**
	 * @param satAvgIsNotNull the satAvgIsNotNull to set
	 */
	public void setSatAvgIsNotNull(boolean satAvgIsNotNull) {
		this.satAvgIsNotNull = satAvgIsNotNull;
	}
	/**
	 * @return the act25IsNotNull
	 */
	public boolean isAct25NotNull() {
		return act25IsNotNull;
	}
	/**
	 * @param act25IsNotNull the act25IsNotNull to set
	 */
	public void setAct25IsNotNull(boolean act25IsNotNull) {
		this.act25IsNotNull = act25IsNotNull;
	}
	/**
	 * @return the act75IsNotNull
	 */
	public boolean isAct75NotNull() {
		return act75IsNotNull;
	}
	/**
	 * @param act75IsNotNull the act75IsNotNull to set
	 */
	public void setAct75IsNotNull(boolean act75IsNotNull) {
		this.act75IsNotNull = act75IsNotNull;
	}
	/**
	 * @return the actAvgIsNotNull
	 */
	public boolean isActAvgNotNull() {
		return actAvgIsNotNull;
	}
	/**
	 * @param actAvgIsNotNull the actAvgIsNotNull to set
	 */
	public void setActAvgIsNotNull(boolean actAvgIsNotNull) {
		this.actAvgIsNotNull = actAvgIsNotNull;
	}
	/**
	 * @return the avgEarningsIsNotNull
	 */
	public boolean isAvgEarningsNotNull() {
		return avgEarningsIsNotNull;
	}
	/**
	 * @param avgEarningsIsNotNull the avgEarningsIsNotNull to set
	 */
	public void setAvgEarningsIsNotNull(boolean avgEarningsIsNotNull) {
		this.avgEarningsIsNotNull = avgEarningsIsNotNull;
	}
	/**
	 * @return the stdBodySzIsNotNull
	 */
	public boolean isStdBodySzNotNull() {
		return stdBodySzIsNotNull;
	}
	/**
	 * @param stdBodySzIsNotNull the stBodySzIsNotNull to set
	 */
	public void setStdBodySzIsNotNull(boolean stBodySzIsNotNull) {
		this.stdBodySzIsNotNull = stBodySzIsNotNull;
	}
	/**
	 * @return the popProg1IsNotNull
	 */
	public boolean isPopProg1NotNull() {
		return popProg1IsNotNull;
	}
	/**
	 * @param popProg1IsNotNull the popProg1IsNotNull to set
	 */
	public void setPopProg1IsNotNull(boolean popProg1IsNotNull) {
		this.popProg1IsNotNull = popProg1IsNotNull;
	}
	/**
	 * @return the popProg2IsNotNull
	 */
	public boolean isPopProg2NotNull() {
		return popProg2IsNotNull;
	}
	/**
	 * @param popProg2IsNotNull the popProg2IsNotNull to set
	 */
	public void setPopProg2IsNotNull(boolean popProg2IsNotNull) {
		this.popProg2IsNotNull = popProg2IsNotNull;
	}
	/**
	 * @return the popProg3IsNotNull
	 */
	public boolean isPopProg3NotNull() {
		return popProg3IsNotNull;
	}
	/**
	 * @param popProg3IsNotNull the popProg3IsNotNull to set
	 */
	public void setPopProg3IsNotNull(boolean popProg3IsNotNull) {
		this.popProg3IsNotNull = popProg3IsNotNull;
	}
	/**
	 * @return the popProg4IsNotNull
	 */
	public boolean isPopProg4NotNull() {
		return popProg4IsNotNull;
	}
	/**
	 * @param popProg4IsNotNull the popProg4IsNotNull to set
	 */
	public void setPopProg4IsNotNull(boolean popProg4IsNotNull) {
		this.popProg4IsNotNull = popProg4IsNotNull;
	}
	/**
	 * @return the popProg5IsNotNull
	 */
	public boolean isPopProg5NotNull() {
		return popProg5IsNotNull;
	}
	/**
	 * @param popProg5IsNotNull the popProg5IsNotNull to set
	 */
	public void setPopProg5IsNotNull(boolean popProg5IsNotNull) {
		this.popProg5IsNotNull = popProg5IsNotNull;
	}
	/**
	 * @return the tuitionInIsNotNull
	 */
	public boolean isTuitionInNotNull() {
		return tuitionInIsNotNull;
	}
	/**
	 * @param tuitionInIsNotNull the tuitionInIsNotNull to set
	 */
	public void setTuitionInIsNotNull(boolean tuitionInIsNotNull) {
		this.tuitionInIsNotNull = tuitionInIsNotNull;
	}
	/**
	 * @return the tuitionOutIsNotNull
	 */
	public boolean isTuitionOutNotNull() {
		return tuitionOutIsNotNull;
	}
	/**
	 * @param tuitionOutIsNotNull the tuitionOutIsNotNull to set
	 */
	public void setTuitionOutIsNotNull(boolean tuitionOutIsNotNull) {
		this.tuitionOutIsNotNull = tuitionOutIsNotNull;
	}
	/**
	 * @return the medDebt
	 */
	public double getMedDebt() {
		return medDebt;
	}
	/**
	 * @param medDebt the medDebt to set
	 */
	public void setMedDebt(double medDebt) {
		this.medDebt = medDebt;
	}
	/**
	 * @return the medDebtIsNotNull
	 */
	public boolean isMedDebtNotNull() {
		return medDebtIsNotNull;
	}
	/**
	 * @param medDebtIsNotNull the medDebtIsNotNull to set
	 */
	public void setMedDebtIsNotNull(boolean medDebtIsNotNull) {
		this.medDebtIsNotNull = medDebtIsNotNull;
	}
	/**
	 * @return the avgFamIncome
	 */
	public int getAvgFamIncome() {
		return avgFamIncome;
	}
	/**
	 * @param avgFamIncome the avgFamIncome to set
	 */
	public void setAvgFamIncome(int avgFamIncome) {
		this.avgFamIncome = avgFamIncome;
	}
	/**
	 * @return the avgFamIncomeIsNotNull
	 */
	public boolean isAvgFamIncomeNotNull() {
		return avgFamIncomeIsNotNull;
	}
	/**
	 * @param avgFamIncomeIsNotNull the avgFamIncomeIsNotNull to set
	 */
	public void setAvgFamIncomeIsNotNull(boolean avgFamIncomeIsNotNull) {
		this.avgFamIncomeIsNotNull = avgFamIncomeIsNotNull;
	}
	/**
	 * @return the medFamIncome
	 */
	public int getMedFamIncome() {
		return medFamIncome;
	}
	/**
	 * @param medFamIncome the medFamIncome to set
	 */
	public void setMedFamIncome(int medFamIncome) {
		this.medFamIncome = medFamIncome;
	}
	/**
	 * @return the medFamIncomeIsNotNull
	 */
	public boolean isMedFamIncomeNotNull() {
		return medFamIncomeIsNotNull;
	}
	/**
	 * @param medFamIncomeIsNotNull the medFamIncomeIsNotNull to set
	 */
	public void setMedFamIncomeIsNotNull(boolean medFamIncomeIsNotNull) {
		this.medFamIncomeIsNotNull = medFamIncomeIsNotNull;
	}
	/**
	 * @return the avgAge
	 */
	public int getAvgAge() {
		return avgAge;
	}
	/**
	 * @param avgAge the avgAge to set
	 */
	public void setAvgAge(int avgAge) {
		this.avgAge = avgAge;
	}
	/**
	 * @return the avgAgeIsNotNull
	 */
	public boolean isAvgAgeNotNull() {
		return avgAgeIsNotNull;
	}
	/**
	 * @param avgAgeIsNotNull the avgAgeIsNotNull to set
	 */
	public void setAvgAgeIsNotNull(boolean avgAgeIsNotNull) {
		this.avgAgeIsNotNull = avgAgeIsNotNull;
	}
	/**
	 * @return the firstGenStudentShareIsNotNull
	 */
	public boolean isFirstGenStudentShareNotNull() {
		return firstGenStudentShareIsNotNull;
	}
	/**
	 * @param firstGenStudentShareIsNotNull the firstGenStudentShareIsNotNull to set
	 */
	public void setFirstGenStudentShareIsNotNull(boolean firstGenStudentShareIsNotNull) {
		this.firstGenStudentShareIsNotNull = firstGenStudentShareIsNotNull;
	}
	/**
	 * @return the maleShareIsNotNull
	 */
	public boolean isMaleShareNotNull() {
		return maleShareIsNotNull;
	}
	/**
	 * @param maleShareIsNotNull the maleShareIsNotNull to set
	 */
	public void setMaleShareIsNotNull(boolean maleShareIsNotNull) {
		this.maleShareIsNotNull = maleShareIsNotNull;
	}
	/**
	 * @return the femaleShareIsNotNull
	 */
	public boolean isFemaleShareNotNull() {
		return femaleShareIsNotNull;
	}
	/**
	 * @param femaleShareIsNotNull the femaleShareIsNotNull to set
	 */
	public void setFemaleShareIsNotNull(boolean femaleShareIsNotNull) {
		this.femaleShareIsNotNull = femaleShareIsNotNull;
	}
	/**
	 * @param maleShare the maleShare to set
	 */
	public void setMaleShare(double maleShare) {
		this.maleShare = maleShare;
	}
	/**
	 * @param femaleShare the femaleShare to set
	 */
	public void setFemaleShare(double femaleShare) {
		this.femaleShare = femaleShare;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @return the admissionRate
	 */
	public double getAdmissionRate() {
		return admissionRate;
	}
	/**
	 * @param admissionRate the admissionRate to set
	 */
	public void setAdmissionRate(double admissionRate) {
		this.admissionRate = admissionRate;
	}
	/**
	 * @return the avgCost
	 */
	public int getAvgCost() {
		return avgCost;
	}
	/**
	 * @param avgCost the avgCost to set
	 */
	public void setAvgCost(int avgCost) {
		this.avgCost = avgCost;
	}
	/**
	 * @return the sat25
	 */
	public double getSat25() {
		return sat25;
	}
	/**
	 * @param sat25 the sat25 to set
	 */
	public void setSat25(double sat25) {
		this.sat25 = sat25;
	}
	/**
	 * @return the sat75
	 */
	public double getSat75() {
		return sat75;
	}
	/**
	 * @param sat75 the sat75 to set
	 */
	public void setSat75(double sat75) {
		this.sat75 = sat75;
	}
	/**
	 * @return the satAvg
	 */
	public double getSatAvg() {
		return satAvg;
	}
	/**
	 * @param satAvg the satAvg to set
	 */
	public void setSatAvg(double satAvg) {
		this.satAvg = satAvg;
	}
	/**
	 * @return the act25
	 */
	public double getAct25() {
		return act25;
	}
	/**
	 * @param act25 the act25 to set
	 */
	public void setAct25(double act25) {
		this.act25 = act25;
	}
	/**
	 * @return the act75
	 */
	public double getAct75() {
		return act75;
	}
	/**
	 * @param act75 the act75 to set
	 */
	public void setAct75(double act75) {
		this.act75 = act75;
	}
	/**
	 * @return the actAvg
	 */
	public double getActAvg() {
		return actAvg;
	}
	/**
	 * @param actAvg the actAvg to set
	 */
	public void setActAvg(double actAvg) {
		this.actAvg = actAvg;
	}
	/**
	 * @return the avgEarnings
	 */
	public int getAvgEarnings() {
		return avgEarnings;
	}
	/**
	 * @param avgEarnings the avgEarnings to set
	 */
	public void setAvgEarnings(int avgEarnings) {
		this.avgEarnings = avgEarnings;
	}
	/**
	 * @return the stdBodySz
	 */
	public int getStdBodySz() {
		return stdBodySz;
	}
	/**
	 * @param stdBodySz the stdBodySz to set
	 */
	public void setStdBodySz(int stdBodySz) {
		this.stdBodySz = stdBodySz;
	}
	/**
	 * @return the popProg1
	 */
	public String getPopProg1() {
		return popProg1;
	}
	/**
	 * @param popProg1 the popProg1 to set
	 */
	public void setPopProg1(String popProg1) {
		this.popProg1 = popProg1;
	}
	/**
	 * @return the popProg2
	 */
	public String getPopProg2() {
		return popProg2;
	}
	/**
	 * @param popProg2 the popProg2 to set
	 */
	public void setPopProg2(String popProg2) {
		this.popProg2 = popProg2;
	}
	/**
	 * @return the popProg3
	 */
	public String getPopProg3() {
		return popProg3;
	}
	/**
	 * @param popProg3 the popProg3 to set
	 */
	public void setPopProg3(String popProg3) {
		this.popProg3 = popProg3;
	}
	/**
	 * @return the popProg4
	 */
	public String getPopProg4() {
		return popProg4;
	}
	/**
	 * @param popProg4 the popProg4 to set
	 */
	public void setPopProg4(String popProg4) {
		this.popProg4 = popProg4;
	}
	/**
	 * @return the popProg5
	 */
	public String getPopProg5() {
		return popProg5;
	}
	/**
	 * @param popProg5 the popProg5 to set
	 */
	public void setPopProg5(String popProg5) {
		this.popProg5 = popProg5;
	}
	/**
	 * @return the tuitionIn
	 */
	public int getTuitionIn() {
		return tuitionIn;
	}
	/**
	 * @param tuitionIn the tuitionIn to set
	 */
	public void setTuitionIn(int tuitionIn) {
		this.tuitionIn = tuitionIn;
	}
	/**
	 * @return the tuitionOut
	 */
	public int getTuitionOut() {
		return tuitionOut;
	}
	/**
	 * @param tuitionOut the tuitionOut to set
	 */
	public void setTuitionOut(int tuitionOut) {
		this.tuitionOut = tuitionOut;
	}
	/*
	 * @param debt
	 */
	public void setMedianDebt(double debt) {
		this.medDebt = debt;
	}
	/*
	 * @return medDebt
	 */
	public double getMedianDebt() {
		return this.medDebt;
	}
	/*
	 * @param avgIncome
	 */
	public void setAvgFamilyIncome(int avgIncome) {
		this.avgFamIncome = avgIncome;
	}
	/*
	 * @return avgFamIncome
	 */
	public int getAvgFamilyIncome() {
		return this.avgFamIncome;
	}
	/*
	 * @param medIncome
	 */
	public void setMedianFamIncome(int medIncome) {
		this.medFamIncome = medIncome;
	}
	/*
	 * @return medFamIncome
	 */
	public int getMedianFamIncome(int medIncome) {
		return this.medFamIncome;
	}
	/*
	 * @param avg entry age
	 */
	public void setEntryAge(int age) {
		this.avgAge = age;
	}
	/*
	 * @return avg entry age
	 */
	public int getEntryAge() {
		return this.avgAge;
	}
	/*
	 * @param 1st generation student share of student body
	 */
	public void setFirstGenStudentShare(double firstGen) {
		this.firstGenStudentShare = firstGen;
	}
	/*
	 * @return 1st generation student double
	 */
	public double getFirstGenStudentShare() {
		return this.firstGenStudentShare;
	}
	/*
	 * @param male student share
	 */
	public void setGenderShare(double maleStudentShare, double femaleStudentShare) {
		this.maleShare = maleStudentShare;
		this.femaleShare = femaleStudentShare;
	}
	/*
	 * @return male student share
	 */
	public double getMaleShare() {
		return this.maleShare;
	}
	public double getFemaleShare() {
		return this.femaleShare;
	}
}
