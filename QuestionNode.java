/**
 * Java II - Winter 2014
 * Zack Vatthikorn Apiratitham
 * March 17, 2014
 * 
 * QuestionNode
 * 
 * This class constructs the QuestionNode for the binary search tree
 * 
 * @author Zack (Vatthikorn) Apiratitham
 * @version 1.0
 */
public class QuestionNode {
	public String data;
    public QuestionNode left; //leftYes
    public QuestionNode right; //rightNo
                
    // constructs a leaf node with given data
    public QuestionNode(String data) {
        this(data, null, null);
    }
                        
    // constructs a branch node with given data, left subtree,
    // right subtree
    public QuestionNode(String data, QuestionNode left,
    					QuestionNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
	
}
