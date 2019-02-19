package CoffeeShopUtilities;
/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * */
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import CoffeeShopUtilities.FoodItem;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;

public class FileManager {
	
	String separator = ",";
	String separator2 = ";"; 
	String menuFile = "../../csvFiles/menu_coffeeShop.csv" ;
	/**Cristy's Comment:
	 * 	Still need to decide how discounts will be stored and how they will work
	 * */
	String discounts = "../../csvFiles/discounts.csv" ;	
	String orderHistoryFile = "../../csvFiles/order_history.csv" ;
	
	
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
	 * @throws NoPriceFoundException 
	 * 
	 * @Params String from csv file
	 * @Returns FoodItem
	 * 
	 * @ThrowException If there is missing information on the line
	 * 
	 * */
	public FoodItem create_foodItem_fromCSV(String s) throws NoCategoryFoundException,
						NoItemIDException, NoItemNameFoundException, NoPriceFoundException {
		String[] item = new String[5];
		
		for(int i = 0; i<5; i++) {
		
		}
		if(s.contains(separator)) { item = s.split(separator);}
		if(s.contains(separator2)) { item = s.split(separator2);}

		FoodItem newItem = new FoodItem();

		/*IF > Checking If there is item ID
		 *ELSE > Find out if it's a category
		 *		 Save the Item ID
		 * */
		if(item[0].isEmpty()) throw new NoItemIDException();
		else {
			/* Checking If there is a Category for the Item
			 * */
			if(item.length < 5) {	//If there is no category given (only 4 entries)
				FoodCategory category2 = newItem.findCategoryFromID(item[0]); //Throws exception 
				newItem.setCategory(category2);
			} else { 
				newItem.setCategory(FoodCategory.valueOf(item[4]));
			}
			
			newItem.setItemID(item[0].toUpperCase());
		}
		/* Checking If there is a Name for the Item
		 * */
		if(item[1].isEmpty()) throw new NoItemNameFoundException();
		else { newItem.setName(item[1]);}
		/* Checking If there is a Price for the Item
		 * */
		if(item[2].isEmpty()) throw new NoPriceFoundException();
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
	 * Creates a HashMap<String, FoodItem> that holds food items 
	 * 				Where the key is the FoodItem.ItemID
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * */
	public HashMap<String, FoodItem> create_menu(String menu_file) throws FileNotFoundException{
		File file = new File(menu_file);
		HashMap<String, FoodItem> menu = new HashMap<String, FoodItem>();
		
		Scanner inputStream = new Scanner(file);
		int count = 0;
		
		while(inputStream.hasNext()) {
			String data = inputStream.nextLine();
			
			if(count>0) {		//Ignore first line on the file
				FoodItem newItem = null;
				
				try {
					newItem = create_foodItem_fromCSV(data);
					menu.put(newItem.getItemID(), newItem);	
				} catch (NoCategoryFoundException e) {

					System.out.println(e.getMessage());
				} catch (NoItemIDException e) {

					System.out.println(e.getMessage());
				} catch (NoItemNameFoundException e) {

					System.out.println(e.getMessage());
				} catch (NoPriceFoundException e) {

					System.out.println(e.getMessage());
				}
			}
			count++;
		}
		inputStream.close();
		
		
		return menu;
	}  
	
	public HashMap<String, FoodItem> create_menu() throws FileNotFoundException{
		return create_menu(menuFile);
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
	
		String file = "csvFiles/"+filename;
		
		FileWriter fw;
		 try {
		    fw = new FileWriter(file);
		    fw.write(report);
		 	fw.close();
		 }
		 //message and stop if file not found
		 catch (FileNotFoundException fnf){
			 System.out.println(file + " not found ");
			 System.exit(0);
		 }
		 //stack trace here because we don't expect to come here
		 catch (IOException ioe){
		    ioe.printStackTrace();
		    System.exit(1);
		 }
	}
	
	/**
	 * 
	 *  
	 * 
	 * @Params String line_from_csv_file , Date check_for_"today"
	 * 	
	 * @Returns customer Order with a single item stored in it 
	 * 
	 * */
	public  CustomerOrder read_customerOrder(String s, Date date){
		
		String[] order = new String[4];
		
		for(int i = 0; i<4; i++) {
		
		}
		
		if(s.contains(separator)) { order = s.split(separator);}
		if(s.contains(separator2)) { order = s.split(separator2);}

		CustomerOrder newOrder = new CustomerOrder();

		/*Check the date first. Return if the date is not the date given
		 * */
		
		return newOrder;
	}

	
	/**
	 * 
	 * This method reads the order_history.csv file and returns the records as a String array list
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Params 
	 * @Returns HashMap <String, CustomerOrder>
	 * 			where 		String =
	 * 						customerOrder = order with 1 item as a string
	 * 
	 * Used the method from OrderManager to do this.
	 * 
	 * */
	public  ArrayList<String> read_orderHistory(){
		
		HashMap <String, CustomerOrder> orderMap = new HashMap <String, CustomerOrder>();
		
		ArrayList<String> orderHistories = null;
		try {
			orderHistories = read_data_by_line(orderHistoryFile);
		} catch (FileNotFoundException e) {
			System.out.println ("OrderManager failed to load the order history. File not found error!");
		}
		return orderHistories;
	}
	
	/**
	 * 
	 * This method reads the order_history.csv file and builds CustomerOrder objects. 
	 * The method returns a HashMap with key as OrderId and value as CustomerOrder. Each CustomerOder object containing 
	 * one of more FoodItems.
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Params String order_history csv file
	 * @Returns ArrayList<CustomerOrder>
	 * 
	 * */
	public  HashMap <String, CustomerOrder> buildCustomerOrdersFromOrderHistory(ArrayList<String> orderHistories, Menu menu) throws FileNotFoundException{
		
		HashMap <String, CustomerOrder> orderMap = new HashMap <String, CustomerOrder>();
		
		for (String order: orderHistories){
			String[] item = order.split(";");
			
			CustomerOrder custOrder = null;
			
			FoodItem fItem = custOrder.getFoodItem(item[2], menu);
			
			ArrayList<FoodItem> fItemList = new ArrayList<FoodItem>();
			fItemList.add(fItem);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(item[3]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//build the CustomerOrder if its a new order. If order exists, then add/append the order item to existing CustomerOrder
			if (orderMap.containsKey(item[0])) {
				custOrder = orderMap.get(item[0]);
				ArrayList<FoodItem> currentFoodItemList = custOrder.getOrderItems();
				currentFoodItemList.add(fItem);
				custOrder.setOrderItems(currentFoodItemList);		
			} else {
				custOrder = new CustomerOrder(item[0], item[1], fItemList, new BigDecimal(0), date);
				orderMap.put(item[0], custOrder);
			}
			
		}
		return orderMap;
	}
}
