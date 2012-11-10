package cz.rank.pj.pascal.statement;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 5:30:42 PM
 */
public class If implements Statement {
	private Statement statementTrue;
	private Statement statementFalse;
	private Expression expression;

	public If(Expression expression, Statement statementTrue, Statement statementFalse) {
		setExpression(expression);
		setStatementTrue(statementTrue);
		setStatementFalse(statementFalse);
	}

	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		if ((Boolean) expression.getValue()) {
			statementTrue.execute();
		} else {
			if (statementFalse != null) {
				statementFalse.execute();
			}
		}
	}

	public Statement getStatementTrue() {
		return statementTrue;
	}

	public void setStatementTrue(Statement statementTrue) {
		this.statementTrue = statementTrue;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Statement getStatementFalse() {
		return statementFalse;
	}

	public void setStatementFalse(Statement statementFalse) {
		this.statementFalse = statementFalse;
	}
}
