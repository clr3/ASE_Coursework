package CoffeeShopUtilities;

import java.io.*;
import java.util.*;

import CoffeeShopUtilities.FoodItem;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;

public class FileManager {
	
	String csv_separator = ";";
	String menu_file = "../../csvFiles/menu_coffeeShop.csv" ;
	/**Cristy's Comment:
	 * 	Still need to decide how discounts will be stored and how they will work
	 * */
	String discounts = "../../csvFiles/discounts.csv" ;	
	String orderHistory = "../../csvFiles/OrderHistory.csv" ;
	
	public FileManager() {
		
	}
	
	/**
	 * Reads and prints out the contents of the file
	 * */
	public void read_data_by_line(String csvFile) throws FileNotFoundException {
		File file = new File(csvFile);
		Scanner inputStream = new Scanner(file);
		int count = 0;
		while(inputStream.hasNext()) {
			if(count>0) {		//Ignore first line on the file
				String data = inputStream.nextLine();
				System.out.println( count + " > "+data);
			}
			count++;
		}
		inputStream.close();
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
	/*
	 *No class for CustomerOrder:
	 *
	public  ArrayList<CustomerOrder> read_orders_file(){
		
	} */
	
}
