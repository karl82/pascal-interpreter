package cz.rank.pj.pascal.operator;

import cz.rank.pj.pascal.Expression;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 12:56:39 AM
 */
public class MultipleOperator extends Operator {
	public MultipleOperator(Expression left, Expression right) {
		super(left, right);
	}

	Object operate(Integer i1, Integer i2) {
		return i1 * i2;
	}

	Object operate(Double i1, Double i2) {
		return i1 * i2;
	}

	Object operate(String i1, String i2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator '*' for strings!");
	}

	Object operate(Boolean b1, Boolean b2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator '*' for booleans!");
	}

	public String toString() {
		StringBuilder info;
		info = new StringBuilder().append(left).append(" * ").append(right);

		return info.toString();
	}

}
