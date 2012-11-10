package cz.rank.pj.pascal;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;

/**
 * User: karl
 * Date: Feb 2, 2006
 * Time: 9:05:33 PM
 */
abstract public interface Expression {
	abstract Expression evalute();

	abstract Object getValue() throws UnknowExpressionTypeException, NotUsableOperatorException;
}
