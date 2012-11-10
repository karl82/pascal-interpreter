package cz.rank.pj.pascal;

import org.apache.log4j.PropertyConfigurator;
import cz.rank.pj.pascal.parser.Parser;

import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * User: karl
 * Date: Feb 24, 2006
 * Time: 12:03:10 AM
 */
public class PascalInterpreter {
	public static void main(String[] args) {
		PropertyConfigurator.configureAndWatch("log4j.properties");

		try {
			Parser parser;
			if (args.length == 1) {
				parser = new Parser(new FileReader(args[0]));
			} else {
				parser = new Parser(new InputStreamReader(System.in));
			}

			parser.parse();
			parser.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
