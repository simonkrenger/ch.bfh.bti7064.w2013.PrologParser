package ch.bfh.bti7064.w2013.prologparser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrologLexer {

	String currentToken = "";
	
	public ArrayList<String> tokenize(String input) {
		ArrayList<String> tokens = new ArrayList<String>();
		
		for(int i=0; i < input.length(); i++) {
			char cur = input.charAt(i);
			char next;
			if(i+1 != input.length()) {
				next = input.charAt(i+1);
			} else {
				next = '\0';
			}
			whatever(cur, next);
		}
		
		
		return tokens;
	}
	
	private void whatever(char cChar, char nChar) {
	
		String currentChar = String.valueOf(cChar);
		String nextChar = String.valueOf(nChar);
		
		// Append, no matter what
		currentToken += currentChar;
		
		// Patterns!
		Pattern lowerCaseCharPattern = Pattern.compile("[a-z]");
		Pattern upperCaseCharPattern = Pattern.compile("[A-Z]");
		Pattern digitPattern = Pattern.compile("[0-9]");
		
		Pattern underlinePattern = Pattern.compile("\\_");
		
		Pattern colonPattern = Pattern.compile(":");
		Pattern minusPattern = Pattern.compile("-");
		Pattern dotPattern = Pattern.compile(".");
		
		Pattern openParenthesesPattern = Pattern.compile("\\(");
		Pattern closeParenthesesPattern = Pattern.compile("\\)");
		Pattern openBracketPattern = Pattern.compile("\\[");
		Pattern closeBracketPattern = Pattern.compile("\\]");
		
		Pattern specialBracketPattern = Pattern.compile("\\W");
		
		// Matchers!
		Matcher lowerCaseCharMatcher = lowerCaseCharPattern.matcher(nextChar);
		Matcher upperCaseCharMatcher = upperCaseCharPattern.matcher(nextChar);
		/*
		Matcher digitMatcher = Matcher.compile("[0-9]");
		
		Matcher underlineMatcher = Matcher.compile("\\_");
		
		Matcher colonMatcher = Matcher.compile(":");
		Matcher minusMatcher = Matcher.compile("-");
		Matcher dotMatcher = Matcher.compile(".");
		
		Matcher openParenthesesMatcher = Matcher.compile("\\(");
		Matcher closeParenthesesMatcher = Matcher.compile("\\)");
		Matcher openBracketMatcher = Matcher.compile("\\[");
		Matcher closeBracketMatcher = Matcher.compile("\\]");
		
		Matcher specialBracketMatcher = Matcher.compile("\\W");
		*/
		
		
	}
}
