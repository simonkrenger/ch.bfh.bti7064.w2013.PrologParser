package ch.bfh.bti7064.w2013.prologparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrologPreprocessor {

	private File inputfile;
	private Scanner scanner;

	public PrologPreprocessor(File in) {
		try {
			this.inputfile = in;
			scanner = new Scanner(this.inputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String preprocess() {
		String preprocessed = "";

		// Remove %-style comments, whitespaces and tabs
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			boolean literal_mode = false;
			
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '%') {
					break;
				} else if(c == '\'') {
					literal_mode = !literal_mode;
				} else if (c == ' ' && literal_mode) {
					// Keep spaces within literals
					preprocessed += c;
				} else if (c != ' ' && c != '\t') {
					preprocessed += c;
				}
			}
		}

		// Also remove /* ... */ style comments:
		preprocessed = preprocessed.replaceAll(
				"/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/", "");

		return preprocessed;
	}
}
