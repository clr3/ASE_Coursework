package CoffeeShopUtilities;
/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * FileManager
 * - knows the files addresses
 * - can convert from files to data types
 * */
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import CoffeeShopUtilities.FoodItem;
import customerOrderExceptions.NoOrderIdException;
import customerOrderExceptions.noCustomerIdException;
import customerOrderExceptions.noOrderItemException;
import customerOrderExceptions.noTimestampException;
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
	 * 
	 * @Params String from csv file
	 * @Returns FoodItem
	 * 
	 * @ThrowException If there is missing information on the line
	 * 
	 * */
	private FoodItem create_foodItem_fromCSV(String s) throws NoCategoryFoundException,
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
	 * @params menu_file name
	 * @throws FileNotFoundException 
	 * @exception print message is item is inclomplete but create menu with valid data
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
	
	/**
	 * Creates a HashMap<String, FoodItem> that holds food items 
	 * 				Where the key is the FoodItem.ItemID
	 * 
	 * @return HashMap<String, FoodItem> 
	 * */
	public HashMap<String, FoodItem> create_menu() throws FileNotFoundException{
		return create_menu(menuFile);
	}
	
	/*No File for discount yet */
	public ArrayList<String> read_discounts(){
		ArrayList<String> discounts = new ArrayList<String>();
		return discounts;
	}
	
	/**
	 * @author Cristina Rivera
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
	 * @author Cristina Rivera
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
	 * @Param String line_from_csv_file 
	 * @Param Menu to create the food items
	 * 	
	 * @Returns customer Order with a single item stored in it 
	 * 
	 * @throws noCustomerIdException 
	 * @throws noTimestampException 
	 * @throws NoOrderIdException 
	 * @throws noOrderItemException 
	 * 
	 * */
	public  CustomerOrder create_CustomerOrder_from_string(String s, Menu menu) throws noCustomerIdException, noTimestampException, NoOrderIdException, noOrderItemException{
		
		String[] order = new String[4];
		
		for(int i = 0; i<4; i++) {
		
		}
		
		if(s.contains(separator)) { order = s.split(separator);}
		if(s.contains(separator2)) { order = s.split(separator2);}

		CustomerOrder newOrder = new CustomerOrder();
		String[] oder = s.split(";");

		/*Check for the date first*/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (oder[3].isEmpty()) throw new noTimestampException();
		try {
			date = format.parse(order[3]);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		newOrder.setTimestamp(date);

		/*Check Order ID*/
		if (order[0].isEmpty()) throw new NoOrderIdException();
		newOrder.setOrderId(order[0]);
		
		/*Check Customer ID*/
		if (order[1].isEmpty()) throw new noCustomerIdException();
		newOrder.setCustomerId(order[1]);
		
		/*Check for item Key and create Item*/
		if(order[2].isEmpty()) throw new noOrderItemException();
		FoodItem fItem = getFoodItem(oder[2], menu);
		newOrder.addItem(fItem);
	
		
		return newOrder;
	}

	
	/**
	 * 
	 * This method reads the order_history.csv file and returns the records as a String array list
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Params 
	 * 
	 * @Returns ArrayList<CustomerOrder> with customer orders read from the file
	 * Will return an ArrayList with all valid custmer orders from the file
	 * 
	 * */
	public  ArrayList<CustomerOrder> read_orderHistory(String file_name, Menu menu) throws FileNotFoundException{
				
		ArrayList<CustomerOrder> orderHistories = new ArrayList<CustomerOrder>();
		
		File file = new File(file_name);
		
		Scanner inputStream = new Scanner(file);
		
		int count = 0;
		
		while(inputStream.hasNext()) {
			String data = inputStream.nextLine();
			
			if(count>0) {		//Ignore first line on the file
				CustomerOrder newOrder = null;
				
					try {
						
						newOrder = create_CustomerOrder_from_string(data, menu);
						orderHistories.add(newOrder);
					} catch (noCustomerIdException e) {
						System.out.println(e.getMessage());
					} catch (noTimestampException e) {						
						System.out.println(e.getMessage());
					} catch (NoOrderIdException e) {
						System.out.println(e.getMessage());
					} catch (noOrderItemException e) {
						System.out.println(e.getMessage());
					}
				
			}
			count++;
		}
		inputStream.close();
	
		return orderHistories;
	}
	
	/**
	 * @author Cristina Rivera
	 * 
	 * 
	 * */
	
	/**
	 * @author Sethu Lekshmy<sl1984@hw.ac.uk>
	 * 
	 * This method reads the order_history.csv file and builds CustomerOrder objects. 
	 * The method returns a HashMap with key as OrderId and value as CustomerOrder. Each CustomerOder object containing 
	 * one of more FoodItems.
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Params Menu to convert the text into 
	 * @Returns  HashMap <String, CustomerOrder>
	 * 
	 * */
	public  HashMap <String, CustomerOrder> buildCustomerOrdersFromOrderHistory(String file, Menu menu) {
		
		ArrayList<CustomerOrder> orderHistories = null;
		try {
			orderHistories = read_orderHistory(file, menu);
			
		}catch (FileNotFoundException e) {
			System.out.println("No past orders");
		}
		
		HashMap <String, CustomerOrder> orderMap = new HashMap <String, CustomerOrder>();
		
		for (CustomerOrder order: orderHistories){
			
			//build the CustomerOrder if its a new order. 
			//If order exists, then add/append the order item to existing CustomerOrder
			if (orderMap.containsKey(order.getOrderId())) {
				
				CustomerOrder custOrder = orderMap.get(order.getOrderId());
				
				ArrayList<FoodItem> currentFoodItemList = custOrder.getOrderItems();				
				currentFoodItemList.addAll(order.getOrderItems());
				
				orderMap.get(order.getOrderId()).setOrderItems(currentFoodItemList);
				
			} else {
				orderMap.put(order.getOrderId(), order);
			}
			
		}
		return orderMap;
	}
	
	/**
	 * @Return HashMap <String, CustomerOrder> for the main order_history
	 * 
	 * @CristysComment: I think we could create different files for each day of orders
	 * This would be easy to implement if we want to do later, but probably not so necessary (unless we had a lot of data)
	 * */
	public  HashMap <String, CustomerOrder> buildCustomerOrdersFromOrderHistory(Menu menu) {
		return buildCustomerOrdersFromOrderHistory(orderHistoryFile, menu);
	}
	/**
	 * @author Sethu Lekshmy<sl1984@hw.ac.uk>
	 * This method returns the FoodItem for the given foodItemId
	 * 
	 * 
	 * @Params String foodItemId
	 * @Returns void
	 * 
	 * */
	FoodItem getFoodItem(String foodItemId, Menu menu) {
		FoodItem fItem = null;
		EnumMap<FoodCategory ,HashMap<String , FoodItem>> menuEnumMap = menu.getMenu();
		Collection<HashMap<String , FoodItem>> menuMapList = menuEnumMap.values();
		for (HashMap<String , FoodItem> menuMap : menuMapList) {
			if (menuMap.containsKey(foodItemId)) {
				fItem = menuMap.get(foodItemId);
				//System.out.println ("FoodItem found from Menu for the given food Item Id "+ foodItemId +" :FoodItem: "+fItem.getName());
			}
			
		}
		return fItem;
	}
}
