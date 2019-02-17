package CoffeeShopUtilities;
/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * */
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import CoffeeShopUtilities.FoodItem;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;

public class FileManager {
	
	String csv_separator = ",";
	String menu_file = "../../csvFiles/menu_coffeeShop.csv" ;
	/**Cristy's Comment:
	 * 	Still need to decide how discounts will be stored and how they will work
	 * */
	String discounts = "../../csvFiles/discounts.csv" ;	
	String orderHistoryFile = "csvFiles/order_history.csv" ;
	
	public FileManager() {
		
	}
	
	/**
	 * Reads and prints out the contents of the file
	 * */
	public  ArrayList<String> read_data_by_line(String csvFile) throws FileNotFoundException {
		File file = new File(csvFile);
		ArrayList<String> csv_line_list = new ArrayList<String>();
		
		
		Scanner inputStream = new Scanner(file);
		int count = 0;
		while(inputStream.hasNext()) {
			String data = inputStream.nextLine();
			if(count>0) {		//Ignore first line on the file
				
				//System.out.println( coStringunt + " > "+data);
				csv_line_list.add(data);
				
				
			}
			count++;
		}
		inputStream.close();
		return csv_line_list;
	}
	
	/**
	 * Create a new food item from a line of text from the csv file
	 * 
	 * @Params String from csv file
	 * @Returns FoodItem
	 * 
	 * @ThrowException If there is missing information on the file 
	 * 
	 * */
	public FoodItem create_foodItem_fromCSV(String s) throws NoCategoryFoundException,
						NoItemIDException, NoItemNameFoundException {
		String[] item = s.split(csv_separator);
		FoodItem newItem = new FoodItem();

		/*IF > Checking If there is item ID
		 *ELSE > Find out if it's a category
		 *		 Save the Item ID
		 * */
		if(item[0] == null) throw new NoItemIDException();
		else {
			FoodCategory category2 = newItem.findCategoryFromID(item[0]); //Throws exception 
			newItem.setCategory(category2);
			newItem.setItemID(item[0]);
		}
		/* Checking If there is a Name for the Item
		 * */
		if(item[1] == null) throw new NoItemNameFoundException();
		else { newItem.setName(item[1]);}
		/* Checking If there is a Price for the Item
		 * */
		if(item[2] == null) throw new NoItemNameFoundException();
		else { newItem.setPrice(Double.parseDouble(item[2]));}
		
	
		/* Check for a description
		 * Will Still create an Item without a descritpion
		 * */
		String description = "";
		if(item[3] != null) { description = item[3];}
		newItem.setDescription(description);
		
		/* Checking If there is a Category for the Item
		 * */
		if(item[4] == null) {
			throw new NoItemNameFoundException();
		} else { 
			newItem.setCategory(FoodCategory.valueOf(item[4]));
		}
		
		return newItem;
	}
	
	
	public void write_to_csv() {
		
	}
	
	/**
	 * Creates a HashMap that holds food items 
	 * */
	public HashMap<String, FoodItem> create_menu(){
		HashMap<String, FoodItem> menu = new HashMap<String, FoodItem>();
		return menu;
	}  
	
	/*No File for discount yet */
	public ArrayList<String> read_discounts(){
		ArrayList<String> discounts = new ArrayList<String>();
		return discounts;
	}
	
	/**
	 * 
	 * This method reads the order_history.csv file and returns the records as a String array list
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Params 
	 * @Returns ArrayList<String>
	 * 
	 * */
	public  ArrayList <String> readOrderHistory(){
		
		ArrayList<String> orderHistories = null;
		try {
			orderHistories = read_data_by_line(orderHistoryFile);
		} catch (FileNotFoundException e) {
			System.out.println ("OrderManager failed to load the order history. File not found error!");
		}
		return orderHistories;
	}

	/**
	 * Writes supplied text to file
	 * 
	 * @param filename the name of the file to be written to
	 * @param report the text to be written to the file
	 */
	public void writeToFile(String filename, String report) {
	
		 FileWriter fw;
		 try {
		    fw = new FileWriter(filename);
		    fw.write(report);
		 	fw.close();
		 }
		 //message and stop if file not found
		 catch (FileNotFoundException fnf){
			 System.out.println(filename + " not found ");
			 System.exit(0);
		 }
		 //stack trace here because we don't expect to come here
		 catch (IOException ioe){
		    ioe.printStackTrace();
		    System.exit(1);
		 }
	}
}
