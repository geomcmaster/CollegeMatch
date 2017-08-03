package main.java;

/**
 * Represents a favorite school record
 * 
 * @author Geoff
 *
 */
public class FavoriteSchool {
	private School school;
	private boolean schoolIsNotNull;
	private int rank;
	private boolean rankIsNotNull;
	private String status;
	private boolean statusIsNotNull;
	private int financialAid;
	private boolean financialAidIsNotNull;
	private int loan;
	private boolean loanIsNotNull;
	private int merit;
	private boolean meritIsNotNull;
	
	public FavoriteSchool() {
		setNulls();
	}
	
	private void setNulls() {
		setSchoolIsNotNull(false);
		setRankIsNotNull(false);
		setStatusIsNotNull(false);
		setFinancialAidIsNotNull(false);
		setLoanIsNotNull(false);
		setMeritIsNotNull(false);
	}
	
	/**
	 * @return the schoolIsNotNull
	 */
	public boolean isSchoolNotNull() {
		return schoolIsNotNull;
	}

	/**
	 * @param schoolIsNotNull the schoolIsNotNull to set
	 */
	public void setSchoolIsNotNull(boolean schoolIsNotNull) {
		this.schoolIsNotNull = schoolIsNotNull;
	}

	/**
	 * @return the rankIsNotNull
	 */
	public boolean isRankNotNull() {
		return rankIsNotNull;
	}

	/**
	 * @param rankIsNotNull the rankIsNotNull to set
	 */
	public void setRankIsNotNull(boolean rankIsNotNull) {
		this.rankIsNotNull = rankIsNotNull;
	}

	/**
	 * @return the statusIsNotNull
	 */
	public boolean isStatusNotNull() {
		return statusIsNotNull;
	}

	/**
	 * @param statusIsNotNull the statusIsNotNull to set
	 */
	public void setStatusIsNotNull(boolean statusIsNotNull) {
		this.statusIsNotNull = statusIsNotNull;
	}

	/**
	 * @return the financialAidIsNotNull
	 */
	public boolean isFinancialAidNotNull() {
		return financialAidIsNotNull;
	}

	/**
	 * @param financialAidIsNotNull the financialAidIsNotNull to set
	 */
	public void setFinancialAidIsNotNull(boolean financialAidIsNotNull) {
		this.financialAidIsNotNull = financialAidIsNotNull;
	}

	/**
	 * @return the loanIsNotNull
	 */
	public boolean isLoanNotNull() {
		return loanIsNotNull;
	}

	/**
	 * @param loanIsNotNull the loanIsNotNull to set
	 */
	public void setLoanIsNotNull(boolean loanIsNotNull) {
		this.loanIsNotNull = loanIsNotNull;
	}

	/**
	 * @return the meritIsNotNull
	 */
	public boolean isMeritNotNull() {
		return meritIsNotNull;
	}

	/**
	 * @param meritIsNotNull the meritIsNotNull to set
	 */
	public void setMeritIsNotNull(boolean meritIsNotNull) {
		this.meritIsNotNull = meritIsNotNull;
	}
	
	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}
	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the financialAid
	 */
	public int getFinancialAid() {
		return financialAid;
	}
	/**
	 * @param financialAid the financialAid to set
	 */
	public void setFinancialAid(int financialAid) {
		this.financialAid = financialAid;
	}
	/**
	 * @return the loan
	 */
	public int getLoan() {
		return loan;
	}
	/**
	 * @param loan the loan to set
	 */
	public void setLoan(int loan) {
		this.loan = loan;
	}
	/**
	 * @return the merit
	 */
	public int getMerit() {
		return merit;
	}
	/**
	 * @param merit the merit to set
	 */
	public void setMerit(int merit) {
		this.merit = merit;
	}
}
