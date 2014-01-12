package ch.bfh.bti7064.w2013.prologparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerStateTranslation {
	
	private Pattern translationPattern;

	public LexerState nextState;

	public boolean createsToken;

	public LexerStateTranslation(String regex, LexerState nextState,
			boolean token) {

		this.translationPattern = Pattern.compile(regex);
		this.nextState = nextState;
		this.createsToken = token;
	}
	
	public boolean translates(char c) {
		String s = String.valueOf(c);
		Matcher m = translationPattern.matcher(s);
		return m.matches();
	}
}
