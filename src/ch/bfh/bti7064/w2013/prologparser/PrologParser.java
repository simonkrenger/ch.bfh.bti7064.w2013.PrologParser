/**
 * 
 */
package ch.bfh.bti7064.w2013.prologparser;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * Program to tokenize a Prolog program
 * 
 * @author Simon Krenger <krens1@bfh.ch>
 * @author Franziska Corradi <corff1@bfh.ch>
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
				PrologPreprocessor proc = new PrologPreprocessor(f);
				String prog = proc.preprocess();

				PrologScanner lex = new PrologScanner();
				ArrayList<PrologScanner.Token> tlist = lex.tokenize(prog);

				System.out.println("Preprocessed output:");
				System.out.println(prog);
				
				System.out.println("Tokens:");
				for (PrologScanner.Token t : tlist) {
					System.out.println(t);
				}
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
		System.out.println("Usage:");
		System.out.println("java -jar PrologParser.jar file.pl");
	}
}
