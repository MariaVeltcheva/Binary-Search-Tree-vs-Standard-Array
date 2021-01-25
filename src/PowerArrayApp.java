import java.util.*;
import java.io.*;

public class PowerArrayApp{
   
   /**Holds the data items obtained from cleaned_data.csv**/ 
   public static String[] dataItems;
   /**Stores the number of data items that will be loaded into the dataItems[] array.**/ 
   public static int arraySize;
   /**Stores the file path of a file that will store a set of counter values for an array of a specific size.**/ 
   public static String filePath;
	
   /**Navigates between various methods and variable states depending on the 
    * arguments passed when running PowerArrayApp from the command line.
    * <p>
    * @param args[0] Date/time string to be queried by printDateTime().
    * @param args[1] Number from 0 to 500 which will determine the number of data items loaded into the dataItems[] array.
    * @param args[2] String that denotes the path to the file which will store the counter values for an array of a specific size.
    * @param args[3] Subset textfile to be traversed and queried by printDateTime method.
    * **/
   public static void main(String args[]){
	 
	  /*Determine size of Array and name of file to store counter
	    according to command line arguments*/
	  if (args.length == 0 || args.length == 1){
		arraySize = 500;
		filePath = "../textfiles/Part2/Part2_ComparisonCount.txt";
	  }else{
		arraySize = Integer.parseInt(args[1]);
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
   
   /**Populates the dataItems[] array with data from cleaned_data.csv.
    * The Date/Time, Power and Voltage values of each line in the csv
    * are combined into a single dataItem which is then stored in the array. **/
   public static void loadData(){
	  File f = new File("../data/cleaned_data.csv");
      Scanner s = null;
      dataItems = new String[arraySize];
      int count = -1;//counter to keep track of array index
      
      try{
		 s = new Scanner(f);
		 while(s.hasNext() && count<= arraySize-1){
			 String line = s.nextLine();
			 String[] parts = line.split(",");
			 String dataItem = parts[0] + ", " + parts[1] + ", " + parts[3];
			 if (count>=0)
				dataItems[count] = dataItem;
		
			 count++;
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
    * argument passed when running PowerArrayApp.
    * @param  dateTime  dateTime to be searched for in the dataItems[] array.**/
   public static void printDateTime(String dateTime){
	   boolean found = false;
	   int opCount = 0;//counter to keep track of how many comparisons the function makes
	   
	   //loop through dataItems and compare each dataItem with the given string
	   for (String dataItem: dataItems){
		   String[] parts = dataItem.split(",");
		   String datePart = parts[0];  
		   String powerPart = parts[1];
		   String voltagePart = parts[2];
		   opCount++;
		   if(dateTime.equals(datePart)){
			   System.out.println(" ");
			   System.out.println(String.format("%-25s %s","Date/time: "," " + datePart));
			   System.out.println(String.format("%-25s %s","Power: " , powerPart));
			   System.out.println(String.format("%-25s %s","Voltage: ", voltagePart));
			   System.out.println(" ");
			   System.out.println(String.format("%-25s %s","Operation Count: "," " + opCount));
			   found = true;
			   break;
		   }
	   }
	   if (found==false){
		   System.out.println("Date/time not found");
		   System.out.println(" ");
		   System.out.println(String.format("%-25s %s","Operation Count: "," " + opCount));
	   }
	   //write opCount value to a file for analysis purposes
	   try{
		   PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		   w.println(opCount);
           w.close();
	   }
	   catch(IOException e){
		   System.out.println(e);
	   }
	   
   }
   
   /**Prints out the Date/Time, Power and Voltage values for all dateTime records. 
    * This method is invoked if no arguments are passed when 
    * running PowerArrayApp from the command line.**/
   public static void printAllDateTimes(){
	   int opCount = 0;
	   for (String dataItem: dataItems){
		   String[] parts = dataItem.split(",");
		   String datePart = parts[0];  
		   String powerPart = parts[1];
		   String voltagePart = parts[2];
		   
		   System.out.println(String.format("%-25s %s","Date/time: ", " " + datePart));
		   System.out.println(String.format("%-25s %s","Power: " , powerPart));
		   System.out.println(String.format("%-25s %s","Voltage: ", voltagePart));
		   System.out.println(" ");
	   }
	   System.out.println(String.format("%-25s %s","Operation Count: "," " + opCount));
	   
	   //write opCount value to a file for analysis purposes
	   try{
		   PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		   w.println(String.format("%-25s %s","No parameters ", opCount));
           w.close();
	   }
	   catch(IOException e){
		   System.out.println(e);
	   }
   }
}
