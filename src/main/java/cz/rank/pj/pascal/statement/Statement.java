package cz.rank.pj.pascal.statement;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Jan 18, 2006
 * Time: 11:13:48 PM
 */
public interface Statement {
	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException;
}
