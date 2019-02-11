package CoffeeShopUtilities;

import java.io.*;
import java.util.*;

import CoffeeShopGUI.FoodItem;

public class FileManager {
	
	String csv_separator = ",";
	String menu;
	String discounts;
	String orderHistory;
	
	public FileManager() {
		
	}
	
	private void read_csv_file(String csvFile) {
		
	}
	private FoodItem create_foodItem_fromCSV(String[] s) {
		FoodItem n = new FoodItem();
		
		n.setCategory(category);
		return n;
	}
	public void write_to_csv() {
		
	}
	
	public HashMap<String, FoodItem> create_menu(){
		HashMap<String, FoodItem> menu = new HashMap<String, FoodItem>();
		return menu;
	}  
	
	public ArrayList<String> read_discounts(){
		ArrayList<String> discounts = new ArrayList<String>();
		return discounts;
	}
	/*
	 *No class for this yet:
	 *
	public  ArrayList<CustomerOrder> read_orders_file(){
		
	} */
}
