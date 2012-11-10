package cz.rank.pj.pascal.statement;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.statement.Statement;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 1:37:43 AM
 */
public class While implements Statement {
	private Statement statement;
	private Expression expression;

	public While(Expression expression, Statement statement) {
		setExpression(expression);
		setStatement(statement);
	}

	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		while ((Boolean) expression.getValue()) {
			statement.execute();
		}
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
