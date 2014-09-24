/**
 * Java II - Winter 2014
 * Zack Vatthikorn Apiratitham
 * March 17, 2014
 * 
 * QuestionTree
 * 
 * This class reads and constructs a binary tree from a text file.
 * It also asks user questions based on the file input and is
 * able to improve its intellect by letting users put in new questions/answers
 * to store inside a text file for further uses.
 * 
 * @author Zack (Vatthikorn) Apiratitham
 * @version 1.0
 */
import java.io.*;
import java.util.*;

public class QuestionTree {
	private QuestionNode overallRoot;
	private Scanner console;

	/**
	 * This is a constructor for this class
	 */
	public QuestionTree() {
		console = new Scanner(System.in);
		overallRoot = new QuestionNode("computer");
	}
	
	/**
	 * This method will be called if the client want to replace the current tree by
	 * reading another tree from a file
	 * @param input - the file for the class to read
	 */
	public void read(Scanner input) {
		overallRoot = readHelper(input);
	}
	
	/**
	 * This is a helper method for read method
	 * @param input - input from user
	 * @return QuestionNode - if it's a question then recursively
	 * 						  if not (it's an answer node) then put that node
	 * 						  that the very bottom of the tree
	 */
	private QuestionNode readHelper(Scanner input) {
		String kind = input.nextLine();
		String text = input.nextLine();
		
		//if the input line is Q: then take in the following line as a question
		if (kind.equals("Q:")) {
			return new QuestionNode(text, readHelper(input), readHelper(input));
		} else { //if not just return the node with the text as the leaf node
			return new QuestionNode(text);
		}
	}
	
	/**
	 * This method will be called if the client wants to store the current tree 
	 * to an output file
	 * @param out - a file to write to
	 */
	public void write(PrintStream out) {
		overallRoot = writeHelper(overallRoot, out);
	}
	/**
	 * This is a helper method for the write method
	 * @param root - a QuestionNode passed from the main write method. Initially an overallRoot
	 * @param out - a file to write to
	 * @return recursive stuff
	 */
	private QuestionNode writeHelper(QuestionNode root, PrintStream out) {
		
		if (root != null) {
			//if it's not a leaf node then print out as a question
			if (root.left != null || root.right != null) {
				out.println("Q:");
				out.println(root.data);
			} else { //else it is a leaf node
				out.println("A:");
				out.println(root.data);
			}
			//recursion down each branch. x = change(x)
			root.left = writeHelper(root.left, out);
			root.right = writeHelper(root.right, out);
		}
		return root;  
	}
	
	/**
	 * This method uses the current tree to ask the user a series of yes/no questions
	 * until the computer guesses their object correctly, or until it fails, in which case
	 * it expands the tree to include their object and a new question to distinguish
	 * their object from others.
	 * 
	 * @return QuestionNode - the askQuestionHelp method
	 */
	public QuestionNode askQuestions() {
		return askQuestionsHelp(overallRoot);
	}
	
	/**
	 * This is a helper method for askQuestions() method
	 * @param root - the root passed from the main askQuestion method.
	 * 				 also the follow-up roots from recursion
	 * @return root - from recursion
	 */
	private QuestionNode askQuestionsHelp(QuestionNode root) {

        String object = "";
        QuestionNode objectNode = null;
        String questionInput = "";
        String objectAns = "";
		
		//if it's a leaf node then check the answer
        if (root.left == null && root.right == null) { 
			System.out.print("Would your object happen to be " + root.data + "? (y/n)? ");
			String answerLeaf = console.nextLine();
			
			// if answer is yes then the computer wins
			if (answerLeaf.equalsIgnoreCase("y")) {
				System.out.println("Great, I got it right!"); //end of the game
                
			// if the answer is no then the computer loses.
			// it asks user to put in answer/question to make it smarter.
			} else if (answerLeaf.equalsIgnoreCase("n")) {
				System.out.print("What is the name of your object? ");
				object = console.nextLine();
				objectNode = new QuestionNode(object);
				System.out.print("Please give me a yes/no question that\n"
						+ "distinguishes between your object\n"
						+ "and mine--> ");
				questionInput = console.nextLine();
				System.out.print("And what is the answer for you object? (y/n)? ");
				objectAns = console.nextLine();
				
				//if answer of the object is "y" then put it in the left node, if it's "n" then put it in right node
                if (objectAns.equalsIgnoreCase("y")) {
                	//new node from root.data for the user's question node to point to
                    QuestionNode rootData = new QuestionNode(root.data);
                    //create new question node from user's input and point left and right accordingly
                    QuestionNode question = new QuestionNode(questionInput, objectNode, rootData);
                                   
                    root = question; //set the root to point to the new question node
		        } else if (objectAns.equalsIgnoreCase("n")) {
		        	//essentially the same thing as the above case
			        QuestionNode rootData = new QuestionNode(root.data);
                    QuestionNode question = new QuestionNode(questionInput, rootData, objectNode);
			        root = question;
		        } else {
                    return root;
                }						
			} else {
				System.out.println("Invalid input. Please answer y or n.");
			}
		} else { //if it's not the leaf node then print out the question with (y/n)?
			System.out.print(root.data + " (y/n)? ");
			String answerBranch = console.nextLine();
			//if answer is yes, go down the left branch recursively
			if (answerBranch.equalsIgnoreCase("y")) {
				root.left = askQuestionsHelp(root.left);
			//if answer is no, go down the right branch recursively
			} else if (answerBranch.equalsIgnoreCase("n")) {
				root.right = askQuestionsHelp(root.right);
			} else {
				System.out.println("Please answer y or n");
				root = askQuestionsHelp(root);
			}
		}
        return root;
    }

	// post: asks the user a question, forcing an answer of "y" or "n";
	//       returns true if the answer was yes, returns false otherwise
	public boolean yesTo(String prompt) {
		System.out.print(prompt + " (y/n)? ");
	    String response = console.nextLine().trim().toLowerCase();
	    while (!response.equals("y") && !response.equals("n")) {
			System.out.println("Please answer y or n.");
	        System.out.print(prompt + " (y/n)? ");
	        response = console.nextLine().trim().toLowerCase();
	    }
	    return response.equals("y");
    }
}