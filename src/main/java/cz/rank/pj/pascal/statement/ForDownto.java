package cz.rank.pj.pascal.statement;

import cz.rank.pj.pascal.operator.MinusOperator;
import cz.rank.pj.pascal.operator.MoreEqualOperator;
import cz.rank.pj.pascal.Expression;
import cz.rank.pj.pascal.Variable;
import cz.rank.pj.pascal.Constant;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 10:17:14 PM
 */
public class ForDownto extends ForTo {
	public ForDownto(Assignment assigmentStatement, Expression finalExpression, Statement executeStatament) {
		super(assigmentStatement, finalExpression, executeStatament);
	}

	protected void initExpression() {
		if (assigmentStatement != null) {
			Variable variable = assigmentStatement.getVariable();

			expression = new MoreEqualOperator(variable, finalExpression);

			afterCycleStatement = new Assignment(variable, new MinusOperator(variable, new Constant(1)));
		}
	}

}
