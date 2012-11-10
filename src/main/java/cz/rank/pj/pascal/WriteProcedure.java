package cz.rank.pj.pascal;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;

import java.util.Enumeration;
import java.util.Vector;

/**
 * User: karl
 * Date: Feb 23, 2006
 * Time: 10:45:39 PM
 */
public class WriteProcedure extends Procedure {
	public Object clone() throws CloneNotSupportedException {
		return new WriteProcedure();
	}

	public void setParameters(Vector<Expression> parameters) throws NotEnoughtParametersException {
		if (parameters == null) {
			throw new NotEnoughtParametersException("Calling 'write' without parameter has no sence!");
		}

		super.setParameters(parameters);	//To change body of overridden methods use File | Settings | File Templates.
	}

	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		Enumeration parametersEnumeration = parameters.elements();

		while (parametersEnumeration.hasMoreElements()) {
			System.out.print(parametersEnumeration.nextElement());
		}
	}
}
