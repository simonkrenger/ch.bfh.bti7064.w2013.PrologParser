package ch.bfh.bti7064.w2013.prologparser;

import java.util.ArrayList;

/**
 * Class to represent a state of the lexer.
 * 
 * @author Simon Krenger <krens1@bfh.ch>
 * 
 */
public class LexerState {

	/**
	 * Name of this state
	 */
	private String stateName;

	/**
	 * Possible translations from this state
	 */
	ArrayList<LexerStateTranslation> translations = new ArrayList<LexerStateTranslation>();

	/**
	 * Constructor for the class. Takes the name of the state as an argument.
	 * 
	 * @param name
	 *            Name of the state
	 */
	public LexerState(String name) {
		this.stateName = name;
	}

	/**
	 * Method to add a translation to this state. This translation is now
	 * possible from this state. See <code>nextTranslation</code>.
	 * 
	 * @param t
	 *            The translation to be added to this state
	 */
	public void addTranslation(LexerStateTranslation t) {
		translations.add(t);
	}

	/**
	 * Method to get the translation when reading the character <code>c</code>.
	 * 
	 * @param c
	 *            Character that is read in the FSA.
	 * @return Returns the translation that matches the character. May return
	 *         <code>NULL</code> if there is no translation for this character
	 *         (this is usually an error).
	 */
	public LexerStateTranslation nextTranslation(char c) {
		for (LexerStateTranslation t : translations) {
			if (t.translates(c)) {
				return t;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "LexerState [stateName=" + stateName + "]";
	}
}
