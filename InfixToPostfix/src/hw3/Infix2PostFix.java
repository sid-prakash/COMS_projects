package edu.iastate.cs228.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Siddharthan Prakash
 *
 * This class converts an input file of infix expressions to postfix expressions
 * that are shown in an output file.
 */
public class Infix2PostFix {

	
	/**
	 * Main method that converts infix expressions from input.txt to postfix expressions
	 * that are shown in an output.txt.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		
		try {
			
			File fileName = new File("src/edu/iastate/cs228/hw3/input.txt");
			Scanner s = new Scanner(fileName);
			String fullpostfix = "";
			
			//Iterates through each line of input file and turns into a postfix expression
			while(s.hasNextLine()) {
				String[] infix = s.nextLine().trim().split("\\s+");
				
				InfixScanner is = new InfixScanner(infix);
				String postfix = is.toPostfix();
				postfix = postfix.substring(0, postfix.length() - 1);
				fullpostfix += postfix + "\n";
			}
			
			s.close();
			fullpostfix = fullpostfix.substring(0, fullpostfix.length() - 1);
			
			String outputFileName = "src/edu/iastate/cs228/hw3/output.txt";
			File f = new File(outputFileName);
			PrintWriter pw = new PrintWriter(f);
			pw.println(fullpostfix);
			pw.close();
			
		}
		catch (FileNotFoundException e) {
			System.out.print("File not found");
		}
		
		
	}

}
