package cz.rank.pj.pascal;

/**
 * User: karl
 * Date: Feb 1, 2006
 * Time: 2:13:33 AM
 */
public class StringVariable extends Variable {
	String value;
	String name;

	public StringVariable() {
		setString("");
		setName(null);
	}

	public StringVariable(String name) {
		setString("");
		setName(name);
	}

	public StringVariable(String name, String value) {
		setString(value);
		setName(name);
	}

	public String getString() {
		return value;
	}

	public Integer getInteger() {
		return null;
	}

	public Double getReal() {
		return null;
	}

	public void setString(String value) {
		this.value = value;
	}

	public void setInteger(Integer value) {
	}

	public void setReal(Double value) {
	}

	public Expression evalute() {
		return null;
	}

	public Object getValue() {
		return null;
	}
}
