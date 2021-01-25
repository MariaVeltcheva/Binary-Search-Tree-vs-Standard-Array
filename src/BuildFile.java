import java.util.*;
import java.io.*;

/**This class is used to create subsets of data from the cleaned_data.csv. 
 * The class accepts as arguments the size of the subset
 * and file path to the textfile that will store the subset of data.**/
public class BuildFile{
   
   /**Stores a subset of the Date/Time strings from cleaned_data.csv**/
   public static String[] dataItems;
   /**Stores the size of the subset data file.**/
   public static int N;
   /**Stores the file path of the textfile that will store the subset of data. **/
   public static String textfile;

   /**Navigates between various variable states depending on the 
    * arguments passed when running BuildFile from the command line.
    * @param args[0] the number of Date/Time Strings to store in the subset data file.
    * @param args[1] the file path of the textfile that the subset of data will be stored in.**/
   public static void main(String args[]){
	   //set the value of N and the name of the textfile to be written to
		N = Integer.parseInt(args[0]);
		textfile = args[1];
	  
	    loadData();
   }
   
	/**Populates the subset data file with data from cleaned_data.csv. 
    * Only the Date/Time value of each line in the csv is stored in the 
    * subset data file as the Date/Time value is unique for each line. **/
   public static void loadData(){
	  File f = new File("../data/cleaned_data.csv");
      Scanner s = null;
      dataItems = new String[N];
      int count = -1;//counter to keep track of array index
      
      //Load dataItems from the csv into an array
      try{
		 s = new Scanner(f);
		 while(s.hasNext() && count<=N-1){
			 String line = s.nextLine();
			 String[] parts = line.split(",");
			 String dataItem = parts[0];
			 if (count>=0)
				dataItems[count] = dataItem;

			 count++;
		 }
	  }
      catch(IOException e)
      {
         System.out.println(e);
      }
      //load dataItems from the array into the textfile
      try{
		   PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(textfile, true)));
		   for (String dateTime : dataItems){
				w.println(dateTime);
		   }
           w.close();
	   }
	   catch(IOException e){
		   System.out.println(e);
	   }
   }
   
}

