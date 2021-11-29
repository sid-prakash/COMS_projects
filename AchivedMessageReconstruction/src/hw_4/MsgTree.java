package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * @author Siddharthan Prakash
 * 
 * Class that constructs a character tree from an file. The file includes
 * strings that are the preorder traversal of the character tree. 
 * The file also includes encoded string of 0 and 1 for the left and right
 * side of the tree.
 *
 */
public class MsgTree {

	// Public instance variable that holds the character of the MsgTree
	public char payloadChar;
	
	// left MsgTree of the current MsgTree
	public MsgTree left;
	
	// right MsgTree of the current MsgTree
	public MsgTree right;
	
	// root of the MsgTree
	public MsgTree root;
	
	//Constructor building the tree from a string
	public MsgTree(String encodingString){
		
		root = new MsgTree(encodingString.charAt(0));
		MsgTree parent = root;
		boolean l = true;	//true if left, false if right
		
		Stack<MsgTree> treeStack = new Stack<MsgTree>();
		treeStack.push(root);
		
		for (int i = 1; i < encodingString.length(); i++) {
			
			char c = encodingString.charAt(i);
			MsgTree curr = new MsgTree(c);
			
			if (c == '^') {
				if (l) {
					parent.left = curr;
				}
				else {
					parent.right = curr;
				}
				l = true;
				treeStack.push(curr);
				parent = curr;
			}
			else {
				if (l) {
					parent.left = curr;
				}
				else {
					parent.right = curr;
				}
				if (!treeStack.empty()) {
					parent = treeStack.pop();
				}
				l = false;
			}
		}
		
	}
	
	//Constructor for a single node with null children
	public MsgTree(char payloadChar){
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	} 
	
	//method to print characters and their binary codes
	public static void printCodes(MsgTree root, String code){
		if (root == null) {
			return;
		}
		
		String str = code;
		if (root.payloadChar != '^') {
			if (root.payloadChar == '\n') {
				System.out.print("    " + "\\n" + "     " + str + "\n");
			}
			else {
				System.out.print("    " + root.payloadChar + "      " + str + "\n");
			}
			str = "";
		}
		
		printCodes(root.left, str + "0");
		printCodes(root.right, str + "1");
	}
	
	//method that decodes the message that is encoded string
	public void decode(MsgTree codes, String msg) {
		MsgTree temp = codes;
		
		for (int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			if (c == '0') {
				if (temp.left.payloadChar != '^') {
					 System.out.print(temp.left.payloadChar);
					 temp = codes;
				}
				else {
					temp = temp.left;
				}
			}
			else {
				if (temp.right.payloadChar != '^') {
					System.out.print(temp.right.payloadChar);
					temp = codes;
				}
				else {
					temp = temp.right;
				}
			}
		}
	}
	
	// Main method that converts the character string and code to the desired message
	public static void main(String[] args) {
		try {
			
			System.out.print("Please enter filename to decode: ");
			Scanner s = new Scanner(System.in);
			String str  = s.next();
			s.close();
			File fileName = new File("src/edu/iastate/cs228/hw4/" + str);
			Scanner sc = new Scanner(fileName);
			
			String preorder = sc.nextLine();
			String secondline = sc.nextLine();
			String code = "";
			
			if(sc.hasNextLine()) {
				code = sc.nextLine();
				preorder += "\n" + secondline;
			}
			else {
				code = secondline;
			}
			
			MsgTree t = new MsgTree(preorder);
			System.out.println("character" + "  " + "code");
			System.out.println("-------------------------");
			MsgTree.printCodes(t.root, "");
			System.out.println();
			System.out.println("MESSAGE:");
			t.decode(t.root, code);
			
			sc.close();
			
		}
		catch (FileNotFoundException e) {
			System.out.print("File not found");
		}
		
	}
}
