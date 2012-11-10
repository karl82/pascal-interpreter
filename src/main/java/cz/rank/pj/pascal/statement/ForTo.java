package cz.rank.pj.pascal.statement;

import cz.rank.pj.pascal.operator.LessEqualOperator;
import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.operator.PlusOperator;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.UnknowExpressionTypeException;
import cz.rank.pj.pascal.Variable;
import cz.rank.pj.pascal.Constant;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 9:30:34 PM
 */
public class ForTo implements Statement {
	protected Assignment assigmentStatement;
	protected Expression expression;
	protected Statement afterCycleStatement;
	private Statement executeStatement;
	protected Expression finalExpression;

	public ForTo(Assignment assigmentStatement, Expression finalExpression, Statement executeStatament) {
		setAssigmentStatement(assigmentStatement);
		setFinalExpression(finalExpression);
		setExecuteStatement(executeStatament);

		initExpression();
	}

	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		getAssigmentStatement().execute();

		while ((Boolean) getExpression().getValue()) {
			getExecuteStatement().execute();
			getAfterCycleStatement().execute();
		}
	}

	protected void initExpression() {
		if (assigmentStatement != null) {
			Variable variable = assigmentStatement.getVariable();

			expression = new LessEqualOperator(variable, finalExpression);

			afterCycleStatement = new Assignment(variable, new PlusOperator(variable, new Constant(1)));
		}
	}

	public Assignment getAssigmentStatement() {
		return assigmentStatement;
	}

	public void setAssigmentStatement(Assignment assigmentStatement) {
		this.assigmentStatement = assigmentStatement;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Statement getAfterCycleStatement() {
		return afterCycleStatement;
	}

	public void setAfterCycleStatement(Statement afterCycleStatement) {
		this.afterCycleStatement = afterCycleStatement;
	}

	public Statement getExecuteStatement() {
		return executeStatement;
	}

	public void setExecuteStatement(Statement executeStatement) {
		this.executeStatement = executeStatement;
	}

	public Object getFinalExpression() {
		return finalExpression;
	}

	public void setFinalExpression(Expression finalExpression) {
		this.finalExpression = finalExpression;
	}
}
