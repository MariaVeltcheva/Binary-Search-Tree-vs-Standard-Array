//implemented as per code in the slides
/**The BinarySearchTree class serves as the implementation of an orderd 
 * Binary Tree data structure where nodes in this case contain a concatenation 
 * String values and are orderd lexically.**/
public class BinarySearchTree{

	private BinaryTreeNode rootNode;
	private int findCount;
	
	/**Creates an empty BinarySearchTree**/
	public BinarySearchTree(){
		rootNode = null;
		findCount = 0;
	}
	
	/**Non-recursive version of the insert method - 
	 * if the tree is not empty, this method invokes the recursive version 
	 * which inserts a node into its tree.
	 * @param s Date/Time, Power and Voltage instance to be stored by the node created by insert. **/
	public void insert (String s){
      if (rootNode == null)
         rootNode = new BinaryTreeNode(s, null, null);
      else{
         insert(s,rootNode);
	 }
    }
    
    /**Recursive version of the insert method - this method recurses down
     * the tree to find the position of insertion and thereafter creates a new
     * node in that position.
     * @param s Date/Time, Power and Voltage instance to be stored by the node created by insert.
     * @param node the node being compared to the given String to find the position of insertion.**/
    public void insert(String s, BinaryTreeNode node){
		if (s.compareTo(node.getData()) < 0){
			if (node.getLeft() == null){
				node.setLeft(s);
			}else
				insert(s, node.getLeft());
		}else{
			if (node.getRight() == null){
				node.setRight(s);
			}else
				insert(s, node.getRight());
		}
	}
	
	/**Prints the value of the data stored in a node.
	 * @param node the node who's data will be printed by the visit method.**/
	public void visit (BinaryTreeNode node){
		System.out.println(format(node.getData()));
	}
	
	/**Invoked when the visit method is called - 
	 * this method formats the data stored in a node by separating its 
	 * Date/Time, Power and Voltage values for easier reading.
	 * @param dataItem the dataItem which needs to be formatted.
	 * @return the formatted String.**/
	public String format(String dataItem){
		String[] parts = dataItem.split(",");
	    String datePart = parts[0];  
	    String powerPart = parts[1];
	    String voltagePart = parts[2];	
	    
	    return String.format("%-25s %s", "Date/time: ", " " + datePart) + "\n" 
	    + String.format("%-25s %s", "Power: ", powerPart) + "\n"
	    + String.format("%-25s %s", "Voltage: ", voltagePart + "\n");
	}
	
	/**Non-recursive version of the preOrder method - this method invokes 
	 * the recursive version which prints out the value of each node.**/
	public void preOrder(){
		preOrder (rootNode);
	}
	
	/**Recursive version of the preOrder method which recurses down the tree and invokes 
	 * the visit method to print out the value of each node - the result is unordered.
	 * @param node the node that will be passed to the visit function for its data to be printed.**/
	public void preOrder(BinaryTreeNode node){
		if (node != null){
			visit(node);
			preOrder(node.getLeft());
			preOrder(node.getRight());
		}   
	}
	
	/**Non-recursive version of the find method - if the tree is not empty,
	 * this method invokes the recursive version and searches for a given dateTime
	 * in the tree.
	 * @param s the dateTime record that will be searched for in the tree.
	 * @return the node if found, null if not found.**/
	public BinaryTreeNode find(String s){
		if (rootNode == null){
			findCount =0;
			return null;
		}else{
			findCount= 0;
			return find(s, rootNode);
		}
	}
	/**Recursive version of the find method - this method recurses down the tree
	 * and compares the given dateTime with the data stored in each node. If found, 
	 * the node is returned, if not then null is returned. The method also keeps track 
	 * of how many comparisons were made when searching for a given node(ie. findCount).
	 * @param s the dateTime record that will be searched for in the tree.
	 * @param node the node who's data will be compared to the given dateTime record.
	 * @return the node if found, null if not found.**/
	public BinaryTreeNode find(String s, BinaryTreeNode node){
		String[] parts = node.getData().split(",");
		String dateTime = parts[0];
		if (s.compareTo(dateTime) == 0){
			findCount++;
			return node;
		}else if (s.compareTo(dateTime) < 0){
			findCount+=2;
			return (node.getLeft() == null) ? null : find(s, node.getLeft());
		}else{
			findCount+=2;
			return (node.getRight() == null) ? null : find(s, node.getRight());
		}
	}
	
	/**Returns the value of findCount which is the number of comparisons 
	 * that were made when searching for a node in its tree.
	 * The value of findCount is reset whenever a new node is searched
	 * for, so the findCount attribute will always correspond to the last node searched.
	 * @return the number of comparisons made when searching for a node.**/
	public int getFindCount(){
		return this.findCount;
	}
    
}
