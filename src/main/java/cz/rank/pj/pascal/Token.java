package cz.rank.pj.pascal;

/**
 * User: karl
 * Date: Jan 19, 2006
 * Time: 1:44:21 PM
 */
public class Token {
	public TokenType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	static Token createEmptyLexicalToken() {
		return new Token();
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public static Token createIdLexicalToken(String name) {
		try {
			return new Token(TokenType.ID, name);
		} catch (WrongTokenTypeError e) {
			/* empty */
		}

		return null;
	}

	public static Token createStringLexicalToken(String value) {
		try {
			return new Token(TokenType.VAL_STRING, value);
		} catch (WrongTokenTypeError e) {
			/* empty */
		}

		return null;
	}

	private final TokenType type;
	private String name;
	private Integer integerValue;
	private Double doubleValue;
	private String stringValue;

	private Token() {
		type = TokenType.EMPTY;
	}

	public Token(TokenType type) {
		this.type = type;
	}

	private Token(TokenType type, String value) throws WrongTokenTypeError {
		switch (type) {
			case ID: {
				this.type = type;
				setName(value);
				break;
			}
			case VAL_STRING: {
				this.type = type;
				setStringValue(value);
				break;
			}
			default: {
				throw new WrongTokenTypeError();
			}
		}
	}

	public Token(Integer integerValue) {
		type = TokenType.VAL_INTEGER;
		setIntegerValue(integerValue);
	}

	public Token(Double doubleValue) {
		type = TokenType.VAL_DOUBLE;
		setDoubleValue(doubleValue);
	}

	public boolean isProgram() {
		return type == TokenType.PROGRAM;
	}

	public boolean isId() {
		return type == TokenType.ID;
	}

	public boolean isSemicolon() {
		return type == TokenType.SEMICOLON;
	}

	public boolean isVar() {
		return type == TokenType.VAR;
	}

	public boolean isColon() {
		return type == TokenType.COLON;
	}

	public boolean isComma() {
		return type == TokenType.COMMA;
	}

	public boolean isBegin() {
		return type == TokenType.BEGIN;
	}

	public boolean isEnd() {
		return type == TokenType.END;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder("Token:").append(getType());

		switch (getType()) {
			case VAL_INTEGER:
				ret.append("<").append(getIntegerValue()).append(">");
				break;
			case VAL_DOUBLE:
				ret.append("<").append(getDoubleValue()).append(">");
				break;
			case VAL_STRING:
				ret.append("<").append(getStringValue()).append(">");
				break;
			case ID:
				ret.append("<").append(getName()).append(">");
				break;
		}

		return ret.toString();
	}

	public boolean isDot() {
		return type == TokenType.DOT;
	}

	public boolean isRightParentie() {
		return type == TokenType.RPAREN;
	}

	public boolean isMinus() {
		return type == TokenType.MINUS;
	}

	public boolean isDo() {
		return type == TokenType.DO;
	}

	public boolean isNot() {
		return type == TokenType.NOT;
	}

	public boolean isThen() {
		return type == TokenType.THEN;
	}

	public boolean isElse() {
		return type == TokenType.ELSE;
	}
}
