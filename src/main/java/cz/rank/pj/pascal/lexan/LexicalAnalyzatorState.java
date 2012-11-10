package cz.rank.pj.pascal.lexan;

/**
 * User: karl
 * Date: Jan 26, 2006
 * Time: 4:49:55 PM
 */
enum LexicalAnalyzatorState {
	START,
	FINISH,
	INTEGER,
	REAL,
	ID,
	STRING,
	STRING_QUOTE,
	STRING_QUOTE2,
	LESS,
	// --Commented out by Inspection (1/26/06 2:53 PM): LESS_EQUAL,
	// --Commented out by Inspection (1/26/06 2:53 PM): MORE_EQUAL,
	LBRACKET,
	// --Commented out by Inspection (1/26/06 2:53 PM): RBRACKET,
	LPAREN, // --Commented out by Inspection (1/26/06 2:53 PM): RPAREN,
	MORE,
	COLON,
	SEMICOLON,
	ASSIGMENT,
	COMMENT,
	PLUS,
	MINUS,
	STAR,
	SLASH,
	AND,
	OR,
	NOT,
	EOF,
	ERROR,
	DOT
}
