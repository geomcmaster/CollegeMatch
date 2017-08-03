package main.java;

/**
 * Represents a favorite field of study record
 * 
 * @author Geoff
 *
 */
public class FavoriteFieldOfStudy {
	private String fieldOfStudy;
	private boolean fieldOfStudyIsNotNull;
	private int rank;
	private boolean rankIsNotNull;
	
	public FavoriteFieldOfStudy() {
		setNulls();
	}
	
	private void setNulls() {
		setFieldOfStudyIsNotNull(false);
		setRankIsNotNull(false);
	}
	
	/**
	 * @return the fieldOfStudyIsNotNull
	 */
	public boolean isFieldOfStudyNotNull() {
		return fieldOfStudyIsNotNull;
	}

	/**
	 * @param fieldOfStudyIsNotNull the fieldOfStudyIsNotNull to set
	 */
	public void setFieldOfStudyIsNotNull(boolean fieldOfStudyIsNotNull) {
		this.fieldOfStudyIsNotNull = fieldOfStudyIsNotNull;
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
	 * @return the fieldOfStudy
	 */
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}
	/**
	 * @param fieldOfStudy the fieldOfStudy to set
	 */
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
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
}
