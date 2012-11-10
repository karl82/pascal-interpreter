package cz.rank.pj.pascal.lexan;

/**
 * User: karl
 * Date: Jan 24, 2006
 * Time: 10:57:11 AM
 */
public class LexicalException extends Exception {
	protected int lineNumber;

	public LexicalException(String string, int line) {
		super(string);
		this.lineNumber = line;
	}

	public LexicalException(String string) {
		super(string);
		lineNumber = 0;
	}

	public String getMessage() {
		return new StringBuilder().append("lineNumber:").append(lineNumber).append(" ").append(super.getMessage()).toString();
	}
}