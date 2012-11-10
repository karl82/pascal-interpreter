package cz.rank.pj.pascal.statement;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.Variable;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Feb 2, 2006
 * Time: 9:25:51 PM
 */
public class Assignment implements Statement {
	protected Variable variable;
	protected Expression expression;

	public Assignment(Variable variable, Expression expression) {
		super();

		setVariable(variable);
		setExpression(expression);
	}

	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		variable.setValue(expression.getValue());
	}

	protected Variable getVariable() {
		return variable;
	}

	public String toString() {
		StringBuilder info;
		info = new StringBuilder("[").append(variable).append(":=").append(expression).append("]");

		return info.toString();
	}

	protected void setVariable(Variable variable) {
		this.variable = variable;
	}

	protected Expression getExpression() {
		return expression;
	}

	protected void setExpression(Expression expression) {
		this.expression = expression;
	}
}
