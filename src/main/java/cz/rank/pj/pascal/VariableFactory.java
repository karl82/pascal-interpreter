/*
 * VariableFactory.java
 *
 * Created on 1. nor 2006, 13:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.rank.pj.pascal;

/**
 * @author karel.rank
 */
public class VariableFactory {

	/**
	 * Creates a new instance of VariableFactory
	 */
	public VariableFactory() {
	}

	public static Variable createStringVariable(String id, String value) {
		return new StringVariable(id, value);
	}

	public static Variable createStringVariable(String id) {
		return new StringVariable(id);
	}

	public static Variable createIntegerVariable(String id, Integer value) {
		return new IntegerVariable(id, value);
	}

	public static Variable createIntegerVariable(String id) {
		return new IntegerVariable(id);
	}

	public static Variable createRealVariable(String id, Double value) {
		return new RealVariable(id, value);
	}

	public static Variable createRealVariable(String id) {
		return new RealVariable(id);
	}
}
