package cz.rank.pj.pascal;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 1:40:51 AM
 */
public interface BoolExpression {
	boolean isTrue() throws UnknowExpressionTypeException, NotUsableOperatorException;
	boolean isFalse() throws UnknowExpressionTypeException, NotUsableOperatorException;
}
