package main.java;

/**
 * The type of condition being applied to a column
 * 
 * NO_COND
 * 		no condition for this column
 * RANGE
 * 		BETWEEN ? AND ?
 * EQ
 * 		= ?
 * GT
 * 		> ?
 * GE
 * 		>= ?
 * LT
 * 		< ?
 * LE
 * 		<= ?
 * NE
 * 		<> ?
 * LIKE
 * 		LIKE ?
 * IN
 * 		IN ?
 * 
 * OR_GROUP
 * 		(cond1 OR cond2 OR ...)
 * 
 * @author Geoff
 *
 */
public enum CondType {
	NO_COND, RANGE, EQ, GT, GE, LT, LE, NE, LIKE, IN, OR_GROUP
}
