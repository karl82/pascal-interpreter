package cz.rank.pj.pascal;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;

import java.util.Enumeration;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 10:49:48 PM
 */
public class WriteLnProcedure extends Procedure {
	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		if (parameters != null) {
			Enumeration parametersEnumeration = parameters.elements();

			while (parametersEnumeration.hasMoreElements()) {
				System.out.print(parametersEnumeration.nextElement());
			}
		}

		System.out.println();
	}

	public Object clone() throws CloneNotSupportedException {
		return new WriteLnProcedure();
	}
}
