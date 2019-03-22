package model;
/**
 * @author armandtene
 * 
 */


import javax.lang.model.element.Name;



public abstract class Customer {
	
	private int CustomerNumber;
	private Name CustomerName;
	private int menu;


/*
 * Simple method to generate short details of the customer
 * 
 * @return String with short details of the customer
 * 
 */

public String getShortDetails() {

	return String.format("CN %d (%s) has ordered menu %1.1f.\n", CustomerNumber, CustomerName);
}

/*
 * Simple method to get the name of the menu selected by the customer
 * 
 * @return String with the number of the menu
 */

public int getMenu() {
	return menu;
}

/*
 * Simple method to set the name of the menu selected by the customer
 * 
 * @return String with the number of the menu
 */

public void setMenu(int menuNumber) {
	this.menu = menuNumber;
}

/* Simple method to return the extra attribute for the customer
 * @return String with extra attribute used */

public int getAttribute()
{
	return menu;
}

}
