package ch.bfh.bti7064.w2013.prologparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrologReader {

	private File inputfile;
	private Scanner scanner;
	
	public PrologReader(File in) {
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
		
		
		 while (scanner.hasNextLine()) {
			 String line = scanner.nextLine();

			 for (int i = 0; i < line.length(); i++) {
				    char c = line.charAt(i);        
				    if(c == '%') {
				    	break;
				    } else if(c != ' ' && c != '\t') {
				    	preprocessed += c;
				    }
				}
		 }
		 return preprocessed;
	}
}
