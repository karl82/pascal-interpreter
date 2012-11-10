package cz.rank.pj.pascal.statement;

import org.apache.log4j.Logger;

import java.util.Enumeration;
import java.util.Vector;

import cz.rank.pj.pascal.operator.NotUsableOperatorException;
import cz.rank.pj.pascal.UnknowExpressionTypeException;

/**
 * User: karl
 * Date: Feb 22, 2006
 * Time: 4:09:13 PM
 */
public class Block implements Statement {
	Vector<Statement> statements;

	private static Logger logger;

	public Block() {
		statements = new Vector<Statement>();
	}
	public void execute() throws UnknowExpressionTypeException, NotUsableOperatorException {
		Enumeration<Statement> statementsEnumeration = statements.elements();

		while (statementsEnumeration.hasMoreElements()) {
			Statement st = statementsEnumeration.nextElement();
			logger.debug(st);
			st.execute();
			logger.debug(st);

		}
	}

	public String toString() {
		StringBuilder info = new StringBuilder("Block[");

		Enumeration<Statement> st = statements.elements();

		while (st.hasMoreElements()) {
			info.append(st.nextElement());
		}

		return info.append("]").toString();
	}

	public void add(Statement st) {
		statements.addElement(st);
	}

	static {
		logger = Logger.getLogger(Block.class);
	}
}
