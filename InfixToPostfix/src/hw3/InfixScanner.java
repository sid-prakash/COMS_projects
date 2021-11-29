package edu.iastate.cs228.hw3;

import java.util.Stack;

/**
 * 
 * @author Siddharthan Prakash
 *
 * This class is used to convert from infix expression to postfix expression
 */
public class InfixScanner 
{
	
	/**
	 * 	Instance array variable that holds each of the operand or operator from
	 * 	the infix expression
	 */
	private String[]infix;
	
	
	/**
	 * Constructor that accepts an array of the infix expression and 
	 * copies them onto the infix instance variable
	 * 
	 * @param infix - input array of String
	 */
	protected InfixScanner(String[] infix)
	{
		this.infix = new String [infix.length];
		for (int i = 0; i < infix.length; i++) {
			this.infix[i] = infix[i];
		}
	}
	
	/**
	 * Carries out the conversion from infix to postfix
	 * 
	 * @return postfix - String of the postfix expression of the infix
	 */
	protected String toPostfix() {
		
		Stack<String> opStack = new Stack<String>();
		String postfix = "";
		
		if (error() != null) {
			return error();
		}
		
		for(int i = 0; i < infix.length; i++) {
			String op = infix[i];
			
			//if the infix expression is not an operator
			if (!isOP(op)) {
				postfix += op + " ";
			}
			
			//if the infix expression is ")"
			else if (op.equals(")")) {
				while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
					postfix += opStack.pop() + " ";
				}
				opStack.pop();
			}
			
			//if the infix expression is "("
			else if (!opStack.isEmpty() && (op.equals("(") || opStack.peek().equals("("))) {
				opStack.push(op);
			}
			
			//if the infix expression has "^" 
			else if (!opStack.isEmpty() && op.equals("^") && opStack.peek().equals("^")) {
				opStack.push(op);
			}
			
			//if the infix expression is an operator
			else {
				while (!opStack.isEmpty() && perc(op) <= perc(opStack.peek())) {
					postfix += opStack.pop() + " ";
				}
				opStack.push(op);
			}
		}
		
		while (!opStack.isEmpty()) {
			postfix += opStack.pop() + " ";
		}
		
		return postfix;
	}
	
	
	/**
	 * Checks if the input string is an operator or not
	 * "(", ")", "^", "*", "/", "%", "+" and "-" are considered operators
	 * 
	 * @param str - string inputed to check if it is an operator or not
	 * @return true if str is an operator and false if str is not an operator
	 */
	private boolean isOP(String str) {
		if(str.equals("(") || str.equals(")") || str.equals("^") || str.equals("*") || str.equals("/") ||
				str.equals("%") || str.equals("+") || str.equals("-") || str.equals("–"))
			return true;
		else {
			return false;
		}
	}
	
	
	/**
	 * Assigns a precedence number based on the type of operator inputed
	 * "^" > "*" = "/" = "%" > "+" = "-"
	 * 
	 * @param str
	 * @return int i that represents a number based on the precedence of the operator
	 */
	private int perc(String str) {
		int i = 0;
		if(str.equals("^")) i = 3;
		else if(str.equals("*")) i = 2;
		else if(str.equals("/")) i = 2;
		else if(str.equals("%")) i = 2;
		else if(str.equals("+")) i = 1;
		else if(str.equals("-")) i = 1;
		else if(str.equals("-")) i = 1;
		else if(str.equals("–")) i = 1;
		return i;
	}
	
	
	/**
	 * Sets a rank number depending on the string.
	 * Parenthesis, "(" and ")", rank = 0.
	 * Operator, "^", "*", "/", "%", "+" and "-", rank = -1.
	 * Operand, all non parenthesis or non operator, rank = 1.
	 * 
	 * @param str
	 * @return rank
	 */
	private int rank(String str) {
		
		if (perc(str) > 0) {
			return -1;
		}
		else if (str.equals("(") || str.equals(")")) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	
	/**
	 * Returns error messages if inputed int i is less than or greater than 0.
	 * Else, return null, no error.
	 * 
	 * @param i - rank of string
	 * @param op - the string included in the error message
	 * @return - Error message or null
	 */
	private String opExpected (int i, String op) {
		if (i > 1) {
			return "Error: too many operands (" + op + ") ";
		}
		else if (i < 0) {
			return "Error: too many operators (" + op + ") ";
		}
		return null;
	}
	
	
	/**
	 * Iterated through the infix array and checks for errors.
	 * 5 types of errors can occur:
	 * 		[1] Error: too many operands (12)
	 * 		[2] Error: too many operators (-)
	 * 		[3] Error: no opening parenthesis detected
	 * 		[4] Error: no closing parenthesis detected
	 * 		[5] Error: no subexpression detected ()		//when 3 + ( ) + 5
	 * 
	 * @return String of the type of error, or null if no error is found
	 */
	private String error () {
		
		int rank = 0;
		int totalRank = 0;
		int parenthesis = 0;
		int tempRank = 0;
		
		for (int i = 0; i < infix.length; i++) {
			String op = infix[i];
			int previous = i - 1;
			
			rank = rank(op);
			totalRank += rank;
			tempRank += rank;
			
			if (opExpected(totalRank, op) != null) {
				return opExpected(totalRank, op);
			}
			
			if (op.equals("(")) {
				parenthesis += 1;
				tempRank = 0;
			}
			else if (op.equals(")")) {
				parenthesis += -1;
			}
			
			if (tempRank < 0) {
				return "Error: too many operators (" + op + ") ";
			}
			
			if (previous >= 0 && infix[previous].equals("(") && infix[i].equals(")")) {
				return "Error: no subexpression detected () ";
			}
			
			if (parenthesis < 0) {
				return "Error: no opening parenthesis detected ";
			}
			
		}
		
		
		if (totalRank == 0) {
			return "Error: too many operators (" + infix[infix.length - 1] + ") ";
		}
		if (parenthesis > 0) {
			return "Error: no closing parenthesis detected ";
		}
		
		return null;
	}
	
}
