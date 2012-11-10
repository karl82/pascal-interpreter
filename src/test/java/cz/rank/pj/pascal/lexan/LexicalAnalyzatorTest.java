package cz.rank.pj.pascal.lexan;

import junit.framework.TestCase;
import cz.rank.pj.pascal.Token;
import cz.rank.pj.pascal.TokenType;

import java.io.StringReader;
import java.io.IOException;
import java.io.BufferedReader;

import org.apache.log4j.PropertyConfigurator;

/**
 * User: karl
 * Date: Jan 24, 2006
 * Time: 11:19:35 AM
 */

public class LexicalAnalyzatorTest extends TestCase {
	private LexicalAnalyzator lexicalAnalyzator;

	public LexicalAnalyzatorTest(String string) {
		super(string);
	}

	protected void setUp() throws Exception {
		super.setUp();
		lexicalAnalyzator = new LexicalAnalyzator();
	}

	public void testProgram() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("program TEST1;\t")));

			Token token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.PROGRAM, token.getType());

			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.ID, token.getType());
			assertEquals("test1", token.getName());

			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.SEMICOLON, token.getType());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testFor() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("\tfor to do")));

			assertEquals(TokenType.FOR, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.TO, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.DO, lexicalAnalyzator.getNextToken().getType());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testId() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("VALUE1VALUE;VALUE_12345_VALUE;")));

			assertEquals("value1value", lexicalAnalyzator.getNextToken().getName());
			assertEquals(TokenType.SEMICOLON, lexicalAnalyzator.getNextToken().getType());
			assertEquals("value_12345_value", lexicalAnalyzator.getNextToken().getName());
			assertEquals(TokenType.SEMICOLON, lexicalAnalyzator.getNextToken().getType());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testWrongId() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("1VALUE")));
			lexicalAnalyzator.getNextToken();

			fail();
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			// It's ok here
		}
	}

	public void testComment() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("VALUE{ for test for\n\n\t\n}NONVALUE")));
			Token token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.ID, token.getType());
			assertEquals("value", token.getName());
			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.ID, token.getType());
			assertEquals("nonvalue", token.getName());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testWrongComment() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("}")));

			lexicalAnalyzator.getNextToken();

			fail();
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			assertTrue(true);
		}
	}

	public void testInteger() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("123456")));

			Token token = lexicalAnalyzator.getNextToken();

			assertEquals(123456, (int) token.getIntegerValue());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testDouble() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("123456.01")));

			Token token = lexicalAnalyzator.getNextToken();

			assertEquals(123456.01, token.getDoubleValue());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testAssigment() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("a := 1;")));

			Token token = lexicalAnalyzator.getNextToken();

			assertEquals(TokenType.ID, token.getType());
			assertEquals("a", token.getName());

			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.ASSIGMENT, token.getType());

			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.VAL_INTEGER, token.getType());
			assertEquals(1, (int) token.getIntegerValue());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testBrackets() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("( ) [[ ]] ){) (}(")));

			assertEquals(TokenType.LPAREN, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.RPAREN, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.LBRACKET, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.LBRACKET, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.RBRACKET, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.RBRACKET, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.RPAREN, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.LPAREN, lexicalAnalyzator.getNextToken().getType());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testComparisions() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("< > <=  >= = <>")));

			assertEquals(TokenType.LESS, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.MORE, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.LESS_EQUAL, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.MORE_EQUAL, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.EQUAL, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.NOTEQUAL, lexicalAnalyzator.getNextToken().getType());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testString() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("\"test\"\"\"\"\"\"test\"")));

			Token token = lexicalAnalyzator.getNextToken();

			assertEquals(TokenType.VAL_STRING, token.getType());
			assertEquals("test\"\"test", token.getStringValue());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public void testFailString() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("\"test\"\"test\"")));

			assertEquals(TokenType.STRING, lexicalAnalyzator.getNextToken().getType());

			fail();
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	public void testPlusMinus() {
		try {
			lexicalAnalyzator.setReader(new BufferedReader(new StringReader("+ - +12 -11 +.1 -0000.1")));
			Token token;

			assertEquals(TokenType.PLUS, lexicalAnalyzator.getNextToken().getType());
			assertEquals(TokenType.MINUS, lexicalAnalyzator.getNextToken().getType());

			assertEquals(TokenType.PLUS, lexicalAnalyzator.getNextToken().getType());
			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.VAL_INTEGER, token.getType());
			assertEquals(12, (int) token.getIntegerValue());
			assertEquals(TokenType.MINUS, lexicalAnalyzator.getNextToken().getType());
			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.VAL_INTEGER, token.getType());
			assertEquals(11, (int) token.getIntegerValue());

			assertEquals(TokenType.PLUS, lexicalAnalyzator.getNextToken().getType());
			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.VAL_DOUBLE, token.getType());
			assertEquals(0.1, token.getDoubleValue());
			assertEquals(TokenType.MINUS, lexicalAnalyzator.getNextToken().getType());
			token = lexicalAnalyzator.getNextToken();
			assertEquals(TokenType.VAL_DOUBLE, token.getType());
			assertEquals(0.1, token.getDoubleValue());


		} catch (IOException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		}
	}

	public static void main(String[] args) {
		PropertyConfigurator.configureAndWatch("log4j.properties");
		junit.textui.TestRunner.run(LexicalAnalyzatorTest.class);
	}

	static {
		PropertyConfigurator.configureAndWatch("log4j.properties");
	}
}
