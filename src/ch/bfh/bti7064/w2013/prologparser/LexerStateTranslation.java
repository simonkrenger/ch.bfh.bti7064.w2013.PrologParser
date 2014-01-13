package ch.bfh.bti7064.w2013.prologparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to represent a state translation in the finite state automata (FSA).
 * 
 * @author Simon Krenger <krens1@bfh.ch>
 * 
 */
public class LexerStateTranslation {

	/**
	 * Pattern that matches this translation
	 */
	private Pattern translationPattern;

	/**
	 * The next state (that this translation translates to)
	 */
	public LexerState nextState;

	/**
	 * Flag if the translation creates a new token or if it is still the same
	 * token.
	 */
	public boolean createsToken;

	/**
	 * Constructor for the class.
	 * 
	 * @param regex
	 *            Regex to be matched when reading a character
	 * @param nextState
	 *            State this translation translates to
	 * @param token
	 *            Flag if the translation creates a new token or if it is still
	 *            the same token.
	 */
	public LexerStateTranslation(String regex, LexerState nextState,
			boolean token) {

		this.translationPattern = Pattern.compile(regex);
		this.nextState = nextState;
		this.createsToken = token;
	}

	/**
	 * Method to check if this translation matches the given character.
	 * 
	 * @param c
	 *            Character to be evaluated
	 * @return Returns TRUE if this translation matches the pattern.
	 */
	public boolean translates(char c) {
		String s = String.valueOf(c);
		Matcher m = translationPattern.matcher(s);
		return m.matches();
	}
}
