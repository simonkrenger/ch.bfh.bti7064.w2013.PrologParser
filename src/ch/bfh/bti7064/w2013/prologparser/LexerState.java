package ch.bfh.bti7064.w2013.prologparser;

import java.util.ArrayList;

public class LexerState {

	private String stateName;
	
	ArrayList<LexerStateTranslation> translations = new ArrayList<LexerStateTranslation>();
	
	public LexerState(String name) {
		this.stateName = name;
	}
	
	public void addTranslation(LexerStateTranslation t) {
		translations.add(t);
	}
	
	public LexerStateTranslation nextTranslation(char c) {
		for(LexerStateTranslation t : translations) {
			if(t.translates(c)) {
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
