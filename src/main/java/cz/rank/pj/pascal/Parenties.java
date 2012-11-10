package cz.rank.pj.pascal;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;

/**
 * User: karl
 * Date: Feb 22, 2006
 * Time: 5:34:55 PM
 */
public class Parenties implements Expression {
	private Expression expression;

	public Parenties(Expression expression) {
		setExpression(expression);
	}

	public Expression evalute() {
		return getExpression().evalute();
	}

	public Object getValue() throws UnknowExpressionTypeException, NotUsableOperatorException {
		return evalute().getValue();
	}

	public Expression getExpression() {
		return expression;
	}

	public String toString() {
		StringBuilder info;
		info = new StringBuilder("(").append(expression).append(")");

		return info.toString();
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
