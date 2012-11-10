package cz.rank.pj.pascal.parser;

import cz.rank.pj.pascal.IntegerVariable;
import cz.rank.pj.pascal.RealVariable;
import cz.rank.pj.pascal.StringVariable;
import cz.rank.pj.pascal.Variable;
import junit.framework.TestCase;
import cz.rank.pj.pascal.lexan.LexicalException;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.IOException;
import java.io.FileReader;

import org.apache.log4j.PropertyConfigurator;

/**
 * User: karl
 * Date: Jan 31, 2006
 * Time: 2:33:08 PM
 */
public class ParserTest extends TestCase {
	Parser parser;

	public ParserTest(String string) {
		super(string);
	}

	public void testProgram() {
		parser = new Parser(new StringReader("\nprogram TEST; begin end."));

		try {
			parser.parse();
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testWrongProgram() {
		parser = new Parser(new StringReader("\nprogram ; begin end."));

		try {
			parser.parse();

			fail("Bad parse of program!");
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVar() {
		parser = new Parser(new StringReader("var a,b : integer; begin end."));

		try {
			parser.parse();
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVarMissingId() {
		parser = new Parser(new StringReader("var a, : integer; begin end."));

		try {
			parser.parse();
			fail("Should not be parsed!");
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVarDoubleColon() {
		parser = new Parser(new StringReader("var a, b:: integer;  begin end."));

		try {
			parser.parse();
			fail("Should not be parsed!");
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVarDoubleComma() {
		parser = new Parser(new StringReader("var a,, b: integer;  begin end."));

		try {
			parser.parse();
			fail("Should not be parsed!");
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVarDoubleId() {
		parser = new Parser(new StringReader("var a b, : integer;  begin end."));

		try {
			parser.parse();
			fail("Should not be parsed!");
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVarMissingSemicolon() {
		parser = new Parser(new StringReader("var a , b : integer begin end."));

		try {
			parser.parse();
			fail("Should not be parsed!");
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testVars() {
		parser = new Parser(new StringReader("var a  : integer; \n b : string; \nvar c : real; \nbegin end."));

		try {
			parser.parse();
			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof StringVariable);

			variable = parser.getGlobalVariable("c");
			assertEquals("c", variable.getName());
			assertTrue(variable instanceof RealVariable);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testAsssigment() {
		parser = new Parser(new StringReader("var a  : integer;\nbegin a:=3; a:=1; \nend."));

		try {
			parser.parse();
			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);

			parser.run();

			assertEquals(1, (int) variable.getInteger());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testBegin() {
		parser = new Parser(new StringReader("var a  : integer;\nbegin a:=(1); begin \na :=(a);end;  \nend."));

		try {
			parser.parse();
			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);

			parser.run();

			assertEquals(1, (int) variable.getInteger());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testParenties() {
		parser = new Parser(new StringReader("var a  : integer;\nbegin a:=(1); \na :=(a); \nend."));

		try {
			parser.parse();
			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);

			parser.run();

			assertEquals(1, (int) variable.getInteger());
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testPlus() {
		parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= 1; \nb := a + a;\nc:= a + (a + b);\na:= a + 50;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(51, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(2, (int) variable.getInteger());

			variable = parser.getGlobalVariable("c");
			assertEquals("c", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(4, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testMinus() {
		parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= 1; \nb := a - a;\nc:= a - (a -a);\na:= a - 50;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(-49, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(0, (int) variable.getInteger());

			variable = parser.getGlobalVariable("c");
			assertEquals("c", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(1, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testMultiple() {
		parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= 1; \nb := a * 10;\nc:= b * b;\na:= a * a;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(1, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

			variable = parser.getGlobalVariable("c");
			assertEquals("c", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(100, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testPlusAndMultiple() {
		parser = new Parser(new StringReader("var a,b,c : integer; \nbegin a:= 5 + 5 * 5; b:= (5+5)*5;c:= 5 * 5 + 5;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(30, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(50, (int) variable.getInteger());

			variable = parser.getGlobalVariable("c");
			assertEquals("c", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(30, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testUnaryMinus() {
		parser = new Parser(new StringReader("var a,b,c  : integer;\nbegin a:= -1; \nb := -(a + a);\nc:= 0 + (- b);\na:= -a;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(1, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(2, (int) variable.getInteger());

			variable = parser.getGlobalVariable("c");
			assertEquals("c", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(-2, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testLess() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0; b:=2;\nwhile a < 10 do begin a:= a + 1; b:= b * 2;end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(2048, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testLessEqual() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0; b:=2;\nwhile a <= 10 do begin a:= a + 1; b:= b * 2;end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(11, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(4096, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testMore() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 10; b:=2048;\nwhile a > 0 do begin a:= a - 1; b:= b / 2;end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(0, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(2, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testMoreEqual() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 11; b:=4096;\nwhile a >= 0 do begin a:= a - 1; b:= b / 2;end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(-1, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(1, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testNotEqual() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 11; b:=-100;\nwhile a <> b do begin b:= b + 1;end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(11, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(11, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testLessWithLeftExpression() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile a + 1 -1 < 10 + 1 do begin a:= a + 1;end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(11, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testAnd() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile (a < 10) and (b < 10) do begin a:= a + 1; b:=b+1; end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testOr() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile (a < 10) or (b < 10) do begin a:= a + 2; b:=b+1; end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(20, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testNot() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile not a = 10 do begin a:= a + 2; b:=b+1; end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(5, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testIf() {
		parser = new Parser(new StringReader("var a, b  : integer;\nbegin a:= 0;\nwhile not a = 10 do begin a:= a + 2; if a = 4 then b:=b+1; end;\nend."));

		try {
			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(1, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testIfElse() {
		try {
			parser = new Parser(getReaderFromClasspath("ifelse.test"));

			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(10, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(18, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

    private InputStreamReader getReaderFromClasspath(String fileName) {
        return new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    public void testForTo() {
		try {
			parser = new Parser(getReaderFromClasspath("forto.test"));

			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(21, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(21, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testForDownto() {
		try {
			parser = new Parser(getReaderFromClasspath("fordownto.test"));

			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(-1, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(21, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testBigFile() {
		try {
			parser = new Parser(getReaderFromClasspath("data.test"));

			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(-151, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(0, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testBigFile2() {
		try {
			parser = new Parser(getReaderFromClasspath("data2.test"));

			parser.parse();
			parser.run();

			Variable variable = parser.getGlobalVariable("a");
			assertEquals("a", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(15, (int) variable.getInteger());

			variable = parser.getGlobalVariable("b");
			assertEquals("b", variable.getName());
			assertTrue(variable instanceof IntegerVariable);
			assertEquals(0, (int) variable.getInteger());

		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (LexicalException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public static void main(String[] args) {
		PropertyConfigurator.configureAndWatch("log4j.properties");
		junit.textui.TestRunner.run(ParserTest.class);
	}
}
