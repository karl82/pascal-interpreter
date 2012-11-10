// $Id$

package cz.rank.pj.pascal.lexan;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;

import cz.rank.pj.pascal.Token;
import cz.rank.pj.pascal.TokenType;

/**
 * User: karl
 * Date: Jan 19, 2006
 * Time: 12:39:49 PM
 */
public class LexicalAnalyzator {
	static final Logger logger;

	public LineNumberReader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = new LineNumberReader(reader);
	}

	public HashMap<String, TokenType> keywords;

	LexicalAnalyzatorState state;
	TokenType currentTokenType;

	private LineNumberReader reader;
	ByteArrayOutputStream tokenBuffer;

	public LexicalAnalyzator() {
		reader = null;
		tokenBuffer = new ByteArrayOutputStream();

		initKeywords();
	}

	public LexicalAnalyzator(Reader reader) {
		this.setReader(new LineNumberReader(reader));
		tokenBuffer = new ByteArrayOutputStream();

		initKeywords();
	}

	public LexicalAnalyzator(InputStream in) {
		this.setReader(new LineNumberReader(new InputStreamReader(in)));
		tokenBuffer = new ByteArrayOutputStream();

		initKeywords();
	}

	protected void initKeywords() {
		keywords = new HashMap<String, TokenType>();

		keywords.put("if", TokenType.IF);
		keywords.put("then", TokenType.THEN);
		keywords.put("else", TokenType.ELSE);
		keywords.put("program", TokenType.PROGRAM);
		keywords.put("do", TokenType.DO);
		keywords.put("while", TokenType.WHILE);
		keywords.put("for", TokenType.FOR);
		keywords.put("to", TokenType.TO);
		keywords.put("downto", TokenType.DOWNTO);
		keywords.put("var", TokenType.VAR);
		keywords.put("type", TokenType.TYPE);
		keywords.put("function", TokenType.FUNCTION);
		keywords.put("procedure", TokenType.PROCEDURE);
		keywords.put("begin", TokenType.BEGIN);
		keywords.put("end", TokenType.END);
		keywords.put("integer", TokenType.INTEGER);
		keywords.put("string", TokenType.STRING);
		keywords.put("real", TokenType.REAL);
		keywords.put("and", TokenType.AND);
		keywords.put("or", TokenType.OR);
		keywords.put("not", TokenType.NOT);

		logger.debug(keywords);
	}

	/* Maybe be better use static methods from Character
	 */

	static public boolean isIdBeginChar(int c) {
		return (c >= 'a' && c <= 'z')
				|| (c >= 'A' && c <= 'Z');
	}

	static public boolean isIdChar(int c) {
		return (c >= 'a' && c <= 'z')
				|| (c >= 'A' && c <= 'Z')
				|| (c >= '0' && c <= '9')
				|| c == '_';
	}

	static public boolean isNumberChar(int c) {
		return c >= '0' && c <= '9';
	}

	static public boolean isPlusChar(int c) {
		return c == '+';
	}

	static public boolean isMinusChar(int c) {
		return c == '-';
	}

	static public boolean isDotChar(int c) {
		return c == '.';
	}

	static public boolean isCommentBeginChar(int c) {
		return c == '{';
	}

	static public boolean isCommentEndChar(int c) {
		return c == '}';
	}

	static public boolean isWhiteSpaceChar(int c) {
		return c == ' ' || c == '\t' || c == '\n';
	}

	static public boolean isCommaChar(int c) {
		return c == ',';
	}

	static public boolean isColonChar(int c) {
		return c == ':';
	}

	static public boolean isSemicolonChar(int c) {
		return c == ';';
	}

	static public boolean isEqualChar(int c) {
		return c == '=';
	}

	static public boolean isLeftParenChar(int c) {
		return c == '(';
	}

	static public boolean isRightParenChar(int c) {
		return c == ')';
	}

	static public boolean isLeftBracketChar(int c) {
		return c == '[';
	}

	static public boolean isRightBracketChar(int c) {
		return c == ']';
	}

	static public boolean isLessChar(int c) {
		return c == '<';
	}

	static public boolean isMoreChar(int c) {
		return c == '>';
	}

	static public boolean isQuoteChar(int c) {
		return c == '"';
	}

	static public boolean isStarChar(int c) {
		return c == '*';
	}

	static public boolean isSlashChar(int c) {
		return c == '/';
	}

	final void readToken() throws java.io.IOException, LexicalException {
		int character;

		tokenBuffer.reset();
		currentTokenType = TokenType.EMPTY;

		while ((state != LexicalAnalyzatorState.EOF) && (state != LexicalAnalyzatorState.FINISH)) {
			reader.mark(5);
			character = reader.read();

			logger.debug("state:" + state + ";character:" + character + ";tokenBuffer:" + tokenBuffer.toString());

			switch (state) {
				case START: {
					if (isIdBeginChar(character)) {
						state = LexicalAnalyzatorState.ID;
						currentTokenType = TokenType.ID;

						tokenBuffer.write(character);
						break;
					}

					if (isNumberChar(character)) {
						state = LexicalAnalyzatorState.INTEGER;
						currentTokenType = TokenType.VAL_INTEGER;

						tokenBuffer.write(character);
						break;
					}

					if (isDotChar(character)) {
						state = LexicalAnalyzatorState.DOT;
						currentTokenType = TokenType.DOT;

						tokenBuffer.write(character);
						break;
					}

					if (isCommentBeginChar(character)) {
						state = LexicalAnalyzatorState.COMMENT;
						break;
					}

					if (isCommentEndChar(character)) {
						state = LexicalAnalyzatorState.ERROR;

						throw new LexicalException("Unexpected end of comment!", reader.getLineNumber());
					}

					if (isWhiteSpaceChar(character)) {
						break;
					}

					if (isColonChar(character)) {
						state = LexicalAnalyzatorState.COLON;
						currentTokenType = TokenType.COLON;
						break;
					}

					if (isSemicolonChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.SEMICOLON;
						continue;
					}

					if (isCommaChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.COMMA;
						continue;
					}

					if (isEqualChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.EQUAL;
						continue;
					}

					if (isLeftParenChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.LPAREN;
						continue;
					}

					if (isRightParenChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.RPAREN;
						continue;
					}

					if (isLeftBracketChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.LBRACKET;
						continue;
					}

					if (isRightBracketChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.RBRACKET;
						continue;
					}

					if (isStarChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.MULT;
						continue;
					}

					if (isSlashChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.REAL_DIV;
						continue;
					}

					if (isPlusChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.PLUS;
						continue;
					}

					if (isMinusChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.MINUS;
						continue;
					}

					if (isLessChar(character)) {
						state = LexicalAnalyzatorState.LESS;
						currentTokenType = TokenType.LESS;
						continue;
					}

					if (isMoreChar(character)) {
						state = LexicalAnalyzatorState.MORE;
						currentTokenType = TokenType.MORE;
						continue;
					}

					if (isQuoteChar(character)) {
						state = LexicalAnalyzatorState.STRING;
						currentTokenType = TokenType.VAL_STRING;
						continue;
					}

					if (character == -1) {
						state = LexicalAnalyzatorState.EOF;
						currentTokenType = TokenType.EOF;
						continue;
					}

					throw new LexicalException("Not expected character '" + (char) character + "'", getLineNumber());
				}
				case COMMENT: {
					if (isCommentEndChar(character)) {
						state = LexicalAnalyzatorState.START;
						break;
					}
				}
				case EOF:
					break;
				case ID: {
					if (isIdChar(character)) {
						tokenBuffer.write(character);
						break;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();
					continue;
				}
				case DOT: {
					if (isNumberChar(character)) {
						state = LexicalAnalyzatorState.REAL;
						currentTokenType = TokenType.VAL_DOUBLE;

						tokenBuffer.write(character);
						break;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();

					break;
				}
				case INTEGER: {
					if (isNumberChar(character)) {
						tokenBuffer.write(character);
						break;
					}

					if (isDotChar(character)) {
						state = LexicalAnalyzatorState.REAL;
						currentTokenType = TokenType.VAL_DOUBLE;

						tokenBuffer.write(character);
						break;
					}

					if (isIdBeginChar(character)) {
						state = LexicalAnalyzatorState.ERROR;
//						currentTokenType = Token.Type.EMPTY;

						reader.reset();

						throw new LexicalException("Identificator can't begin with number!", reader.getLineNumber());
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();

					continue;
				}
				case REAL: {
					if (isNumberChar(character)) {
						tokenBuffer.write(character);
						break;
					}

					if (isDotChar(character)) {
						state = LexicalAnalyzatorState.ERROR;
						reader.reset();
						break;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();
					continue;
				}
				case LESS: {
					if (isEqualChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.LESS_EQUAL;

						continue;
					}

					if (isMoreChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.NOTEQUAL;

						continue;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();
					continue;
				}
				case MORE: {
					if (isEqualChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.MORE_EQUAL;

						continue;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();
					continue;
				}
				case SLASH:
					break;
				case STAR:
					break;
				case STRING: {
					if (isQuoteChar(character)) {
						state = LexicalAnalyzatorState.STRING_QUOTE;
						continue;
					}

					tokenBuffer.write(character);
					break;
				}
				case STRING_QUOTE: {
					if (isQuoteChar(character)) {
						state = LexicalAnalyzatorState.STRING_QUOTE2;
						continue;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();
					continue;
				}
				case STRING_QUOTE2: {
					if (isQuoteChar(character)) {
						state = LexicalAnalyzatorState.STRING;
						tokenBuffer.write(character);
						continue;
					}

					state = LexicalAnalyzatorState.ERROR;
					currentTokenType = TokenType.EMPTY;
					throw new LexicalException("Wrong sequence of \" in string", reader.getLineNumber());
				}
				case COLON: {
					if (isEqualChar(character)) {
						state = LexicalAnalyzatorState.FINISH;
						currentTokenType = TokenType.ASSIGMENT;

						continue;
					}

					state = LexicalAnalyzatorState.FINISH;
					reader.reset();
					continue;
				}
				case ERROR:
					break;
			}
		}
	}

	TokenType getTokenType() {

		if (currentTokenType == TokenType.ID) {
			TokenType tokenType;
			tokenType = keywords.get(tokenBuffer.toString().toLowerCase());

			// If we didn't find any reserved keyword, then it is probably
			// identificator of variable
			if (tokenType != null) {
				return tokenType;
			}
		}

		return currentTokenType;
	}

	public Token getNextToken() throws java.io.IOException, LexicalException {
		state = LexicalAnalyzatorState.START;

		readToken();

		TokenType tokenType;
		tokenType = getTokenType();

		switch (tokenType) {
			case ID: {
				return Token.createIdLexicalToken(tokenBuffer.toString().toLowerCase());
			}
			case VAL_STRING: {
				return Token.createStringLexicalToken(tokenBuffer.toString());
			}
			case VAL_INTEGER: {
				return new Token(Integer.parseInt(tokenBuffer.toString()));
			}
			case VAL_DOUBLE: {
				return new Token(Double.parseDouble(tokenBuffer.toString()));
			}
			default: {
				return new Token(tokenType);
			}
		}
	}

	public int getLineNumber() {
		return reader.getLineNumber() + 1;
	}


	static {
		logger = Logger.getLogger(LexicalAnalyzator.class);
	}
}
