package cz.rank.pj.pascal.operator;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.operator.Operator;
import cz.rank.pj.pascal.BoolExpression;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 2:24:02 PM
 */
public class OrOperator extends Operator implements BoolExpression {
	public OrOperator(Expression left, Expression right) {
		super(left, right);
	}

	Object operate(Integer i1, Integer i2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator 'or' for integers!");
	}

	Object operate(Double i1, Double i2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator 'or' for reals!");
	}

	Object operate(String i1, String i2) throws NotUsableOperatorException {
		throw new NotUsableOperatorException("Can't use operator 'or' for strings!");
	}

	Object operate(Boolean b1, Boolean b2) throws NotUsableOperatorException {
		return b1 || b2;
	}


	public boolean isTrue() throws UnknowExpressionTypeException, NotUsableOperatorException {
		return (Boolean) getValue();
	}

	public boolean isFalse() throws UnknowExpressionTypeException, NotUsableOperatorException {
		return !((Boolean) getValue());
	}
}
