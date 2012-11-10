package cz.rank.pj.pascal.parser;

import cz.rank.pj.pascal.TokenType;

/**
 * User: karl
 * Date: Jan 31, 2006
 * Time: 2:41:19 PM
 */
public class ParseException extends Exception {
	protected int lineNumber;

	public ParseException() {
		super();

		lineNumber = 0;
	}

	public ParseException(String string) {
		super(string);

		lineNumber = 0;
	}

	public ParseException(String string, int line) {
		super(string);
		this.lineNumber = line;
	}

	public ParseException(char c) {
		super("Expected '" + c + "'");
	}

	public ParseException(char c, int line) {
		super("Expected '" + c + "'");
		this.lineNumber = line;
	}

	public ParseException(TokenType type, int line) {
		super("Expected '" + type + "'");
		this.lineNumber = line;
	}
	
	public String getMessage() {
		return new StringBuilder().append("lineNumber:").append(lineNumber).append(" ").append(super.getMessage()).toString();
	}
}
