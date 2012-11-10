package cz.rank.pj.pascal.parser;

import cz.rank.pj.pascal.*;
import cz.rank.pj.pascal.statement.*;
import cz.rank.pj.pascal.operator.*;
import cz.rank.pj.pascal.lexan.LexicalAnalyzator;
import cz.rank.pj.pascal.lexan.LexicalException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * User: karl
 * Date: Jan 31, 2006
 * Time: 2:18:09 PM
 */
public class Parser {
	LexicalAnalyzator lexan;
	Token currentToken;

	LinkedHashMap<String, Variable> globalVariables;
	LinkedHashMap<String, Procedure> globalProcedures;

	private boolean tokenPushed;

	private static Logger logger;

	protected Statement entryPoint;

	public Parser(Reader reader) {
		this.lexan = new LexicalAnalyzator(reader);

		initGlobals();
	}

	public Parser(InputStream in) {
		this.lexan = new LexicalAnalyzator(in);

		initGlobals();
	}

	private void initGlobals() {
		globalVariables = new LinkedHashMap<String, Variable>();
		globalProcedures = new LinkedHashMap<String, Procedure>();
		setTokenPushed(false);

		initStaticMethods();
	}

	private void initStaticMethods() {
		globalProcedures.put("writeln", new WriteLnProcedure());
		globalProcedures.put("write", new WriteProcedure());
	}

	void parseProgram() throws ParseException, IOException, LexicalException {
		if (currentToken.isProgram()) {
			if (readToken().isId()) {
				if (!readToken().isSemicolon()) { // isSemicolon
					throw new ParseException("; expected", lexan.getLineNumber());
				}
			} else { // isId
				throw new ParseException("identificator expected", lexan.getLineNumber());
			}
		}
	}

	public Variable getGlobalVariable(String name) {
		return globalVariables.get(name);
	}

	public Procedure getGlobalProcedure(String name) {
		return globalProcedures.get(name);
	}

	LinkedHashMap<String, Variable> parseVar() throws ParseException, IOException, LexicalException {
		Vector<Token> variablesNames;
		LinkedHashMap<String, Variable> variables;

		variablesNames = new Vector<Token>();
		variables = new LinkedHashMap<String, Variable>();

		boolean variablesParsed = false;

		logger.debug("testing '" + currentToken.getType() + "' if is VAR");
		if (currentToken.isVar()) {
			logger.debug("in VAR");
			while (!variablesParsed) {
				do {
					readToken();
					switch (currentToken.getType()) {
						case ID:
							variablesNames.add(currentToken);
							switch (readToken().getType()) {
								case COLON:
									break;
								case COMMA:
									break;
								default:
									throw new ParseException("',' or ':' expected", lexan.getLineNumber());
							}

							break;
						default:
							throw new ParseException("identificator expected", lexan.getLineNumber());
					}
				} while (!currentToken.isColon());

				Iterator variablesNamesIterator = variablesNames.iterator();

				switch (readToken().getType()) {
					case INTEGER:

						logger.debug("INTEGER variable");

						while (variablesNamesIterator.hasNext()) {
							Token variable = (Token) variablesNamesIterator.next();

							logger.debug(variable);

							if (!variables.containsKey(variable.getName())) {
								variables.put(variable.getName(), VariableFactory.createIntegerVariable(variable.getName()));
							} else {
								throw new ParseException("variable '" + variable.getName() + "'is defined 2times!", lexan.getLineNumber());
							}
						}

						variablesNames.clear();
						break;
					case STRING:

						logger.debug("STRING variable");

						while (variablesNamesIterator.hasNext()) {
							Token variable = (Token) variablesNamesIterator.next();

							logger.debug(variable);

							if (!variables.containsKey(variable.getName())) {
								variables.put(variable.getName(), VariableFactory.createStringVariable(variable.getName()));
							} else {
								throw new ParseException("variable '" + variable.getName() + "'is defined 2times!", lexan.getLineNumber());
							}
						}

						variablesNames.clear();
						break;
					case REAL:

						logger.debug("REAL variable");

						while (variablesNamesIterator.hasNext()) {
							Token variable = (Token) variablesNamesIterator.next();

							logger.debug(variable);

							if (!variables.containsKey(variable.getName())) {
								variables.put(variable.getName(), VariableFactory.createRealVariable(variable.getName()));
							} else {
								throw new ParseException("variable '" + variable.getName() + "'is defined 2times!", lexan.getLineNumber());
							}
						}

						variablesNames.clear();
						break;
					default:
						throw new ParseException("type expected", lexan.getLineNumber());
				}

				if (!readToken().isSemicolon()) {
					throw new ParseException("';' expected", lexan.getLineNumber());
				}

				switch (readToken().getType()) {
					case BEGIN:
						variablesParsed = true;
					case ID:
						setTokenPushed(true);
					case VAR:
						break;
					default:
						logger.debug("unexpected token " + currentToken);
						throw new ParseException("unexpected currentToken '" + currentToken.getType() + "'", lexan.getLineNumber());
				}
			}
		}

		return variables;
	}

	static Variable getVariablesCollision(LinkedHashMap<String, Variable> vars1, LinkedHashMap<String, Variable> vars2) {
		Variable variable = null;

		for (String currentKey : vars1.keySet()) {
			if (vars2.containsKey(currentKey)) {
				variable = vars2.get(currentKey);
				break;
			}
		}

		return variable;
	}

	public void procedure() {

	}

	public void function() {

	}

	public Statement mainBegin() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
		Block block = new Block();

		while (!readToken().isEnd()) {

			Statement st = parseStatement();

			logger.debug(currentToken);

/*
			if (st == null) {
				throw new ParseException("Unexpected token '" + currentToken + "'");
			}
*/

			if (st != null) {
				block.add(st);
			}

			if (!readToken().isSemicolon()) {
				logger.error("expected semicolon");
				throw new ParseException("Expected ';'. Have '" + currentToken + "'", lexan.getLineNumber());
			}
		}

		if (!readToken().isDot()) {
			throw new ParseException('.', lexan.getLineNumber());
		}

		return block;
	}

	public Statement parseBegin() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
		Block block = new Block();
		while (!readToken().isEnd()) {

			Statement st = parseStatement();

			logger.debug(currentToken);
/*
			if (st == null) {
				throw new ParseException("Unexpected token '" + currentToken + "'");
			}
*/

			if (st != null) {
				block.add(st);
			}

			if (!readToken().isSemicolon()) {
				throw new ParseException("Expected ';'. Have '" + currentToken + "'", lexan.getLineNumber());
			}

		}

		return block;
	}

	private Vector<Expression> parseProcedureParameters() throws IOException, LexicalException, ParseException, UnknowVariableNameException {
		if (readToken().isRightParentie()) {
			return null;
		}

		Vector<Expression> parameters = new Vector<Expression>();


		boolean hasMoreParameters = true;

		setTokenPushed(true);
		do {
			parameters.add(parseExpression());
			if (!readToken().isComma()) {
				 hasMoreParameters = false;
			}
		} while (hasMoreParameters);

		return parameters;
	}

	private Statement parseStatement() throws IOException, LexicalException, UnknowVariableNameException, ParseException, UnknowProcedureNameException, NotEnoughtParametersException {
		logger.debug(currentToken);

		switch (currentToken.getType()) {
			case ID: {
				String name = currentToken.getName();

				switch (readToken().getType()) {
					case ASSIGMENT:
						return parseAssigment(checkAndReturnVariable(name));
						// procedure
					case LPAREN:
						Procedure procedure = checkAndReturnProcedure(name);

						procedure.setParameters(parseProcedureParameters());

						if (!currentToken.isRightParentie()) {
							logger.error(currentToken);
							throw new ParseException("Expected ')'", lexan.getLineNumber());
						}

//						readToken();
						return procedure;
					default:
						throw new ParseException("Unexpected token '" + currentToken + "'", lexan.getLineNumber());
				}

//				throw new ParseException("Unexpected token '" + currentToken + "'", lexan.getLineNumber());
//					break;
			}
			case BEGIN: {
				return parseBegin();
			}

			case WHILE: {
				return parseWhile();
			}
			case IF: {
				return parseIf();
			}

			case FOR: {
				return parseFor();
			}

			default:
				throw new ParseException("Unexpected token '" + currentToken + "'", lexan.getLineNumber());
		}
	}

	private Statement parseWhile() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
		Expression ex = parseExpression();

		if (!readToken().isDo()) {
			throw new ParseException("Expected 'do'", lexan.getLineNumber());
		}

		readToken();
		Statement st = parseStatement();


		return new While(ex, st);
	}

	private Statement parseIf() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
		Expression ex = parseExpression();

		if (!readToken().isThen()) {
			throw new ParseException("Expected 'then'", lexan.getLineNumber());
		}

		readToken();
		Statement st1 = parseStatement();
		Statement st2 = null;

		logger.debug(currentToken);

		if (!currentToken.isSemicolon()) {
			if (readToken().isElse()) {
				readToken();
				st2 = parseStatement();
			} else {
				setTokenPushed(true);
			}
		}

		return new If(ex,st1, st2);
	}

	private Statement parseFor() throws IOException, ParseException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
		readToken();

		Statement assignmentStatement = parseStatement();

		if (!(assignmentStatement instanceof Assignment)) {
			throw new ParseException("Expected assignment statement!", lexan.getLineNumber());
		}

		boolean downto = false;

		switch (currentToken.getType()) {
			case TO:
				break;
			case DOWNTO:
				downto = true;
				break;
			default: {
				throw new ParseException("Expected 'to' or 'downto'!", lexan.getLineNumber());
			}
		}

		readToken();

		Expression finalExpression = parseExpression();

		if (!currentToken.isDo()) {
			throw new ParseException("Expected 'do'", lexan.getLineNumber());
		}

		setTokenPushed(false);
		readToken();
		Statement executeStatement = parseStatement();

		logger.debug(currentToken);

		if (downto) {
			return new ForDownto((Assignment) assignmentStatement, finalExpression, executeStatement);
		} else {
			return new ForTo((Assignment) assignmentStatement, finalExpression, executeStatement);
		}
	}

	private Variable checkAndReturnVariable(String name) throws UnknowVariableNameException {
		Variable variable = getGlobalVariable(name);

		if (variable == null) {
			throw new UnknowVariableNameException(name + ":" + lexan.getLineNumber());
		}

		return variable;
	}

	private Procedure checkAndReturnProcedure(String name) throws UnknowProcedureNameException {
		Procedure procedure = getGlobalProcedure(name);

		if (procedure == null) {
			throw new UnknowProcedureNameException(name + ":" + lexan.getLineNumber());
		}

		try {
			procedure = (Procedure) procedure.clone();
		} catch (CloneNotSupportedException e) {
			/* empty, cloning is SUPPORTED ALWAYS */
		}
		return procedure;
	}

	private void acceptIt() throws IOException, LexicalException {
		currentToken = lexan.getNextToken();
	}

	private void accept(TokenType token) throws IOException, LexicalException, ParseException {
		if (currentToken.getType() == token) {
			currentToken = lexan.getNextToken();
		} else {
			throw new ParseException(token, lexan.getLineNumber());
		}
	}

	private Expression primaryExpression() throws IOException, LexicalException, UnknowVariableNameException, ParseException {
		switch (readToken().getType()) {
			case VAL_INTEGER:
				logger.debug("parseExpression:interger value " + currentToken.getIntegerValue());
				return new Constant(currentToken.getIntegerValue());

			case VAL_DOUBLE:
				logger.debug("parseExpression:double value " + currentToken.getDoubleValue());
				return new Constant(currentToken.getDoubleValue());

			case VAL_STRING:
				logger.debug("parseExpression:string value " + currentToken.getStringValue());
				return new Constant(currentToken.getStringValue());

			case ID:
				logger.debug("parseExpression:id name " + currentToken);
				return checkAndReturnVariable(currentToken.getName());

				// Unary minus
			case MINUS:
				logger.debug("parseExpression:token " + currentToken);
				return new UnaryMinus(primaryExpression());

			case LPAREN:
				logger.debug("parseExpression:lparen");
				Expression ex = new Parenties(parseExpression());

				if (!readToken().isRightParentie()) {
					throw new ParseException(')', lexan.getLineNumber());
				}

				return ex;
		}

		setTokenPushed(true);
		logger.debug(currentToken);

		throw new ParseException("value expected!", lexan.getLineNumber());
	}

	private Expression parseOperatorExpression() throws IOException, ParseException, LexicalException, UnknowVariableNameException {
		Expression ex = primaryExpression();

		logger.debug(ex);

		boolean operatorFound;

		do {
			readToken();

			logger.debug("token:" + currentToken);

			switch (currentToken.getType()) {
				case PLUS:
					ex = new PlusOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case MINUS:
					ex = new MinusOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case MULT:
					ex = new MultipleOperator(ex, primaryExpression());
					operatorFound = true;
					break;
				case REAL_DIV:
					ex = new DivideOperator(ex, primaryExpression());
					operatorFound = true;
					break;
				case DIV:
					ex = new DivideOperator(ex, primaryExpression());
					operatorFound = true;
					break;
				default:
					operatorFound = false;
			}
		} while (operatorFound);

		setTokenPushed(true);
		return ex;
	}

	private Expression parseCompareExpression() throws IOException, LexicalException, UnknowVariableNameException, ParseException {
		Expression ex = parseOperatorExpression();

		logger.debug(ex);

		boolean operatorFound;

		do {
			readToken();

			logger.debug("token:" + currentToken);

			switch (currentToken.getType()) {
				case LESS:
					ex = new LessOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case MORE:
					ex = new MoreOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case EQUAL:
					ex = new EqualOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case LESS_EQUAL:
					ex = new LessEqualOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case MORE_EQUAL:
					ex = new MoreEqualOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				case NOTEQUAL:
					ex = new NotEqualOperator(ex, parseOperatorExpression());
					operatorFound = true;
					break;
				default:
					operatorFound = false;
			}
		} while (operatorFound);

		setTokenPushed(true);
		return ex;
	}

	private Expression parseExpression() throws IOException, LexicalException, UnknowVariableNameException, ParseException {
		Expression ex;
		if (readToken().isNot()) {
			ex = new NotOperator(parseCompareExpression());
		} else {
			setTokenPushed(true);
			ex = parseCompareExpression();
		}


		logger.debug(ex);

		boolean operatorFound;

		do {
			readToken();

			logger.debug("token:" + currentToken);

			switch (currentToken.getType()) {
				case AND:
					ex = new AndOperator(ex, parseExpression());
//					ex = new LessOperator(ex, primaryExpression());
					operatorFound = true;
					break;
				case OR:
					ex = new OrOperator(ex, parseExpression());
					operatorFound = true;
					break;
/*
				case NOT:
					ex = new NotOperator(parseExpression());
					operatorFound = true;
					break;
*/
				default:
					operatorFound = false;
			}
		} while (operatorFound);

		setTokenPushed(true);
		return ex;
	}

	private Statement parseAssigment(Variable variable) throws IOException, LexicalException, ParseException, UnknowVariableNameException {
		Statement st = new Assignment(variable, parseExpression());

		logger.debug("parseAssigment token:" + currentToken);
/*
		if (!readToken().isSemicolon()) {
			throw new ParseException("Expected ';'", lexan.getLineNumber());
		}
*/
		return st;
	}

	public void parse() throws ParseException, IOException, LexicalException, UnknowVariableNameException, UnknowProcedureNameException, NotEnoughtParametersException {
		boolean parsedAll = false;

		while (!parsedAll) {
			switch (readToken().getType()) {
				case PROGRAM:
					parseProgram();
					break;
				case VAR:
					LinkedHashMap<String, Variable> localVariables;
					localVariables = parseVar();

					if (globalVariables.isEmpty()) {
						globalVariables = localVariables;
					} else {
						Variable collisionVariable = getVariablesCollision(globalVariables, localVariables);

						if (collisionVariable != null) {
							throw new ParseException("variable'" + collisionVariable.getName() + "' already defined");
						}
					}
					break;
				case PROCEDURE:
					procedure();
					break;
				case FUNCTION:
					function();
					break;
				case BEGIN:
					entryPoint = mainBegin();
					parsedAll = true;
					break;
				default:
//					parsedAll = true;
					logger.debug("unexpected token " + currentToken);
					throw new ParseException("unexcpected currentToken '" + currentToken + "'", lexan.getLineNumber());
//					break;
			}
		}
	}

	private Token readToken() throws IOException, LexicalException {
		if (isTokenPushed()) {
			setTokenPushed(false);
			return currentToken;
		} else {
			return currentToken = lexan.getNextToken();
		}
	}


	public boolean isTokenPushed() {
		return tokenPushed;
	}

	public void setTokenPushed(boolean tokenPushed) {
		this.tokenPushed = tokenPushed;
	}

	static {
		logger = Logger.getLogger(Parser.class);
	}

	public void run() throws UnknowExpressionTypeException, NotUsableOperatorException {
		logger.debug("Executing entrypoint...");
//		logger.debug(entryPoint);
		entryPoint.execute();
	}
}
