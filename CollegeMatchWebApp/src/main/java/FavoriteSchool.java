package main.java;

/**
 * Represents a favorite school record
 * 
 * @author Geoff
 *
 */
public class FavoriteSchool {
	private School school;
	private int rank;
	private String status;
	private int financialAid;
	private int loan;
	private int merit;
	
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
