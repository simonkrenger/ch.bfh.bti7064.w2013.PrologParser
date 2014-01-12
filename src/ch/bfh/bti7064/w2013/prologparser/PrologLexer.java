package ch.bfh.bti7064.w2013.prologparser;

import java.util.ArrayList;

public class PrologLexer {

	// All lower chars
	static final String LOWER_CHARS = "[a-z]";

	// All upper chars
	static final String UPPER_CHARS = "[A-Z]";

	// Underscore
	static final String UNDER = "_";
	
	// Digits
	static final String DIGITS = "[0-9]";

	// Special chars except "_", ":" and "-"
	static final String SPECIAL = "[^A-Za-z0-9_:-]";

	// A colon
	static final String COLON = ":";

	// A dash
	static final String DASH = "-";

	LexerState currentState;

	Token currentToken;

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

		// Init
		currentState = init;
	}

	public ArrayList<Token> tokenize(String input) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		currentToken = new Token("");

		for (int i = 0; i < input.length(); i++) {
			char currentChar = input.charAt(i);

			LexerStateTranslation t = currentState.nextTranslation(currentChar);
			if (t != null) {
				if(t.createsToken) {
					tokens.add(currentToken);
					currentToken = new Token("");
				}
				currentToken.append(currentChar);
				currentState = t.nextState;
			} else {
				System.err.println("Error at character " + i + ": '"
						+ currentChar);
				System.err.println("Current state: " + currentState);

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
