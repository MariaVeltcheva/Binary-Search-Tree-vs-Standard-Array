import java.util.*;
import java.io.*;

public class PowerBSTApp{
	
	/**BinarySearchTree that will hold the data items obtained from cleaned_data.csv**/
	public static BinarySearchTree dataItems;
	/**Stores the number of data items that will be loaded into the dataItems BinarySearchTree.**/
	public static int BSTSize;
	/**Stores the file path of a file that will store a set of counter values for a tree of a specific size.**/
	public static String filePath;
	
	/**Navigates between various methods and variable states depending on the 
    * arguments passed when running PowerBSTApp from the command line.
    * <p>
    * @param args[0] Date/time string to be queried by printDateTime().
    * @param args[1] Number from 0 to 500 which will determine the number of data items loaded into the dataItems BinarySearchTree.
    * @param args[2] String that denotes the path to the file which will store the counter values for a tree of a specific size.
    * @param args[3] Subset textfile to be traversed and queried by printDateTime method.
    * **/
	public static void main(String args[]){
		
	  /*Determine size of BST and name of file to store counter
	    according to command line arguments*/
	  if (args.length == 0 || args.length==1){
		BSTSize = 500;
		filePath = "../textfiles/Part4/Part4_ComparisonCount.txt";
	  }else{
		BSTSize = Integer.parseInt(args[1]);
		filePath = args[2];
	  }	
	 
	  loadData();
	  
	  
	  /*Call either printDateTime() or printAllDateTimes() according
      to command line arguments provided*/
      if (args.length == 0){ 
		 printAllDateTimes();
	  }else if (args.length == 1){  
		  printDateTime(args[0]);
	  }else if (args.length == 4){ 
		  //if subset file provided as paramter, traverse and pass each date to printDateTime to be queried
		  File f = new File(args[3]);
		  Scanner s = null;
		  try{
			 s = new Scanner(f);
			 while(s.hasNext()){
				 String line = s.nextLine();
				 String[] parts = line.split(",");
				 String date = parts[0];
				 printDateTime(date);
			 }
		  }
		  catch(IOException e)
		  {
			 System.out.println(e);
		  }

		}
	
	}

	/**Populates the dataItems tree with data from cleaned_data.csv.
    * The Date/Time, Power and Voltage values of each line in the csv
    * are combined into a single dataItem which is then added as a node in the tree. **/
	public static void loadData(){
	  File f = new File("../data/cleaned_data.csv");
	  Scanner s = null;
	  dataItems = new BinarySearchTree();//initialise empty Binary Search Tree
	  
	  int lineCount = -1;
	  try{
		 s = new Scanner(f);
		 while(s.hasNext() && lineCount<= BSTSize-1 ){
			 String line = s.nextLine();
			 String[] parts = line.split(",");
			 String dataItem = parts[0] + ", " + parts[1] + ", " + parts[3];
			 if (lineCount>=0)
				dataItems.insert(dataItem);
			 
			 lineCount++;
		 }
	  }
	  catch(IOException e)
	  {
		 System.out.println(e);
	  }
	}
	
   /**Prints out the Date/time, Power and Voltage values for
    * the given dateTime record, or "Date/time not found" if the record is not found.
    * The method also keeps count of how many comparisons were made when searching 
    * for the given datTime record, and writes that value to a text file along with the given dateTime. 
    * The file it is written to is determined by the command line
    * argument passed when running PowerBSTApp.
    * @param  dateTime  dateTime to be searched for in the dataItems BinarySearchTree.**/	
	public static void printDateTime(String dateTime){
		BinaryTreeNode result = dataItems.find(dateTime);
		int findCount = dataItems.getFindCount();
		if (result == null){
			System.out.println("Date/time not found");
			System.out.println(" ");
			System.out.println(String.format("%-25s %s","Operation Count: "," " + findCount));
		}else{
			System.out.println(" ");
			dataItems.visit(result);
			System.out.println(String.format("%-25s %s","Operation Count: "," " + findCount));
		}
		
		//write opCount value to a file for analysis purposes
		try{
		   PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		   w.println(findCount);
           w.close();
	   }
	   catch(IOException e){
		   System.out.println(e);
	   }
	}
	
   /**Prints out the Date/Time, Power and Voltage values for all dateTime records. 
    * This method is invoked if no arguments are passed when 
    * running PowerBSTApp from the command line.**/	
	public static void printAllDateTimes(){
	   dataItems.preOrder();
	   System.out.println(String.format("%-25s %s","Operation Count: "," " + 0));
		 
	   //write opCount value to a file for analysis purposes
	   try{
		   PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		   w.println(String.format("%-25s %s","No parameters", 0));
           w.close();
	   }
	   catch(IOException e){
		   System.out.println(e);
	   }
		 
	}
}
