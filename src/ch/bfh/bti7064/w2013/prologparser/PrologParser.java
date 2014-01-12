/**
 * 
 */
package ch.bfh.bti7064.w2013.prologparser;

import java.io.File;
import java.util.ArrayList;

/**
 * @author simon
 * 
 */
public class PrologParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			File f = new File(args[0]);
			if (f.exists() && !f.isDirectory()) {
				PrologPreprocessor preader = new PrologPreprocessor(f);
				String prog = preader.preprocess();

				PrologLexer lex = new PrologLexer();
				ArrayList<PrologLexer.Token> tlist = lex.tokenize(prog);
				
				for(PrologLexer.Token t : tlist) {
					System.out.println(t);
				}
				
				
				System.out.println(prog);
			} else {
				System.err.println("Error: Specified file does not exist.");
				printUsage();
			}
		} else {
			System.err.println("Error: Number of command line arguments wrong");
			printUsage();
		}

	}

	private static void printUsage() {
		System.err.println("Usage:");
		System.err.println("PrologParser file.pl");
	}

}
