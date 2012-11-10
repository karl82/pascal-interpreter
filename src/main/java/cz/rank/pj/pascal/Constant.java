package cz.rank.pj.pascal;

/**
 * User: karl
 * Date: Feb 22, 2006
 * Time: 5:46:04 PM
 */
public class Constant implements Expression {
	private Object value;

	public Constant(Object value) {
		setValue(value);
	}

	public Expression evalute() {
		return this;
	}

	public String toString() {
		return getValue().toString();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
