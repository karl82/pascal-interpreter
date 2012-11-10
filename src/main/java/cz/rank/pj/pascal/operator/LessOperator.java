package cz.rank.pj.pascal.operator;

import org.apache.log4j.Logger;
import cz.rank.pj.pascal.BoolExpression;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 2:24:02 PM
 */
public class LessOperator extends Operator implements BoolExpression {
	private static Logger logger;

	public LessOperator(Expression left, Expression right) {
		super(left, right);
	}

	Object operate(Integer i1, Integer i2) {
		logger.debug(left + "<" + right);
		return i1 < i2;
	}

	Object operate(Double i1, Double i2) {
		return i1 < i2;
	}

	Object operate(String i1, String i2) throws NotUsableOperatorException {
		return i1.compareTo(i2) == -1;
	}

	Object operate(Boolean b1, Boolean b2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator '<' for booleans!");
	}


	public boolean isTrue() throws UnknowExpressionTypeException, NotUsableOperatorException {
		return (Boolean) getValue();
	}

	public boolean isFalse() throws UnknowExpressionTypeException, NotUsableOperatorException {
		return !((Boolean) getValue());
	}

	public String toString() {
		StringBuilder info;
		info = new StringBuilder().append(left).append("<").append(right);

		return info.toString();
	}

	static {
		logger = Logger.getLogger(LessOperator.class);
	}
}
