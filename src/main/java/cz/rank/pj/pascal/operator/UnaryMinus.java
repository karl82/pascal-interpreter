package cz.rank.pj.pascal.operator;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.operator.Operator;
import cz.rank.pj.pascal.Expression;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 1:24:50 AM
 */
public class UnaryMinus extends Operator {

	public UnaryMinus(Expression ex) {
		super(ex, ex);
	}

	Object operate(Integer i1, Integer i2) {
		return - i1;
	}

	public String toString() {
		return "-" + left.toString();
	}

	Object operate(Double i1, Double i2) {
		return -i2;
	}

	Object operate(String i1, String i2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator unary '-' for strings!");
	}

	Object operate(Boolean b1, Boolean b2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator unary '-' for boolean!");
	}
}
