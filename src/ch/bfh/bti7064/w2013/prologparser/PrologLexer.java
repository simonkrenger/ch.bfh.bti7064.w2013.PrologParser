package ch.bfh.bti7064.w2013.prologparser;

import java.util.ArrayList;

/**
 * Lexer class to represent a (only partially implemented) lexigraphical
 * analyser
 * 
 * @author Simon Krenger <krens1@bfh.ch>
 * @author Franziska Corradi <corff1@bfh.ch>
 * 
 */
public class PrologLexer {

	// What follows are the possible classes of characters accepted by this
	// finite state automata.

	/**
	 * All lower chars
	 */
	static final String LOWER_CHARS = "[a-z]";

	/**
	 * All upper chars
	 */
	static final String UPPER_CHARS = "[A-Z]";

	/**
	 * Underscore
	 */
	static final String UNDER = "_";

	/**
	 * Digits
	 */
	static final String DIGITS = "[0-9]";

	/**
	 * Special chars except "_", ":" and "-"
	 */
	static final String SPECIAL = "[^A-Za-z0-9_:-]";

	/**
	 * A colon
	 */
	static final String COLON = ":";

	/**
	 * A dash
	 */
	static final String DASH = "-";

	/**
	 * Current state of the finite state automata.
	 */
	LexerState currentState;

	/**
	 * The current token that is being processed
	 */
	Token currentToken;

	/**
	 * Token class for lexigraphical analysis
	 * 
	 * @author Simon Krenger <krens1@bfh.ch>
	 * 
	 */
	public static class Token {
		public String data;

		public Token(String data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return String.format("<%s>", data);
		}

		public void append(char c) {
			this.data += String.valueOf(c);
		}
	}

	/**
	 * Constructor for the class. Constructs all states and configures the
	 * translations.
	 */
	public PrologLexer() {

		LexerState init = new LexerState("INIT");
		LexerState cons = new LexerState("CONSTANT");
		LexerState var = new LexerState("VARIABLE");
		LexerState spec = new LexerState("SPECIAL_CHAR");
		LexerState colon = new LexerState("COLON");
		LexerState dash = new LexerState("DASH");

		init.addTranslation(new LexerStateTranslation(LOWER_CHARS, cons, false));
		init.addTranslation(new LexerStateTranslation(UPPER_CHARS, var, false));
		init.addTranslation(new LexerStateTranslation(UNDER, var, false));
		init.addTranslation(new LexerStateTranslation(SPECIAL, spec, false));

		cons.addTranslation(new LexerStateTranslation(LOWER_CHARS, cons, false));
		cons.addTranslation(new LexerStateTranslation(DIGITS, cons, false));
		cons.addTranslation(new LexerStateTranslation(UNDER, cons, false));
		cons.addTranslation(new LexerStateTranslation(SPECIAL, spec, true));
		cons.addTranslation(new LexerStateTranslation(COLON, colon, true));

		var.addTranslation(new LexerStateTranslation(UPPER_CHARS, var, false));
		var.addTranslation(new LexerStateTranslation(LOWER_CHARS, var, false));
		var.addTranslation(new LexerStateTranslation(UNDER, var, false));
		var.addTranslation(new LexerStateTranslation(DIGITS, var, false));
		var.addTranslation(new LexerStateTranslation(SPECIAL, spec, true));

		spec.addTranslation(new LexerStateTranslation(UPPER_CHARS, var, true));
		spec.addTranslation(new LexerStateTranslation(UNDER, var, true));
		spec.addTranslation(new LexerStateTranslation(LOWER_CHARS, cons, true));
		spec.addTranslation(new LexerStateTranslation(DIGITS, cons, true));
		spec.addTranslation(new LexerStateTranslation(SPECIAL, spec, true));
		spec.addTranslation(new LexerStateTranslation(COLON, colon, true));

		colon.addTranslation(new LexerStateTranslation(DASH, dash, false));

		dash.addTranslation(new LexerStateTranslation(UPPER_CHARS, var, true));
		dash.addTranslation(new LexerStateTranslation(SPECIAL, spec, true));
		dash.addTranslation(new LexerStateTranslation(LOWER_CHARS, cons, true));
		dash.addTranslation(new LexerStateTranslation(DIGITS, cons, true));

		// Initial state
		currentState = init;
	}

	/**
	 * Method to tokenize a given input string. The string must be preprocessed
	 * (All comments and whitespaces removed).
	 * 
	 * @param input
	 *            Input string to be tokenized
	 * @return A list of tokens
	 */
	public ArrayList<Token> tokenize(String input) {
		ArrayList<Token> tokens = new ArrayList<Token>();

		currentToken = new Token("");

		for (int i = 0; i < input.length(); i++) {
			// Process character by character
			char currentChar = input.charAt(i);

			// Check if there is a translation for this character
			LexerStateTranslation t = currentState.nextTranslation(currentChar);
			if (t != null) {
				if (t.createsToken) {
					tokens.add(currentToken);
					currentToken = new Token("");
				}
				currentToken.append(currentChar);
				currentState = t.nextState;
			} else {
				// There was an error. Print debug information

				System.err.println("Error at character " + i + ": '"
						+ currentChar);
				System.err.println("Current state: " + currentState);

				// We don't want to print all the input. Just print +/-5 chars
				int lower = (i - 5 < 0) ? 0 : i - 5;
				int upper = (i + 5 > input.length()) ? input.length() : i + 5;

				System.err.print(input.substring(lower, i));
				System.err.print("<" + currentChar + ">");
				System.err.println(input.substring(i + 1, upper));

				break;
			}
		}

		return tokens;
	}
}
