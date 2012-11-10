package cz.rank.pj.pascal;

/**
 * User: karl
 * Date: Feb 1, 2006
 * Time: 2:13:55 AM
 */
public class IntegerVariable extends Variable {

	public IntegerVariable() {
		setInteger(0);
		setName(null);
	}

	public IntegerVariable(String name) {
		setInteger(0);
		setName(name);
	}

	public IntegerVariable(String name, Integer value) {
		setInteger(value);
		setName(name);
	}

	public String toString() {
/*
		StringBuilder info;
		info = new StringBuilder("[").append(getName()).append("(").append(getInteger()).append(")]");
*/
		return getInteger().toString();
	}

	public String getString() {
		return null;
	}

	public Integer getInteger() {
		return (Integer) value;
	}

	public Double getReal() {
		return null;
	}

	public void setString(String value) {
	}

	public void setInteger(Integer value) {
		this.value = value;
	}

	public void setReal(Double value) {
	}

	public Expression evalute() {
		return this;
	}

	public Object getValue() {
		return value;
	}
}
