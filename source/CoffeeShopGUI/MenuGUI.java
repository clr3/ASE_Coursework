/**
* Menu order GUI class for Coffee Shop
*/
package CoffeeShopGUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CoffeeShopController.OrderController;
import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FoodCategory;
import CoffeeShopUtilities.FoodItem;
import CoffeeShopUtilities.Menu;
import CoffeeShopUtilities.*;

/**
* Menu order GUI class for Coffee Shop
*
* @author  Arthidevi Balavignesh
*/
public class MenuGUI extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame f =new JFrame();
    private JLabel totalCostValue;
    private JPanel itemsPanel = new JPanel();
    private JPanel currentFoodItemPanel = new JPanel();
    //private HashMap<String,Integer> cart = new HashMap<String,Integer>();
    //private double totalCost = 0;
    private Menu menu_obj = new Menu(true);
	private static DecimalFormat df2 = new DecimalFormat("###.##");
	
	private ArrayList<FoodItem> labels = new ArrayList<FoodItem>();
	private ArrayList<JButton> plusButtons = new ArrayList<JButton>();
	private ArrayList<JButton> minusButtons = new ArrayList<JButton>();
	

	Button orderButton = new Button("Order Food");
    Button resetButton = new Button("Reset");

    
	
	//Each menu has 1 order attached to it
	private CustomerOrder order = new CustomerOrder();
	private OrderManager om;

	private OrderController controller = new OrderController(this,order,om);
	
	private int t = 0; //int to keep count of all the objects in the menu
    
    /** 
     * Constructor for Menu GUI
     * 
     * @param Menu menu_obj1
     */
    public MenuGUI(OrderManager omgr, CustomerOrder o){
    		this.om = omgr;
    		this.order = o;
            JPanel jp = new JPanel();
            jp.add(createCategoryPanel());
            JButton b1=new JButton("Coffee Shop - Food Menu");;  
            JButton b2=new JButton("Copyright @ HW");;  
              
            f.add(b1,BorderLayout.NORTH);  
            f.add(b2,BorderLayout.SOUTH);  
            f.add(jp,BorderLayout.WEST);  
            //itemsPanel.add(createItemsDisplay());
            f.add(currentFoodItemPanel,BorderLayout.CENTER);  

              
            f.setSize(600,600);  
            f.setVisible(false); 
            f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   }
    
    /** 
     * Shows Menu page
     */
    public void showMenuPage() {
    	f.setVisible(true);
    }
    
    /** 
     * Hide Menu page
     */
    public void hideMenuPage() {
    	f.setVisible(false);
    }
    
	/**
	 * Returns Category list panel
	 *
	 * @return JPanel panel
	 */
    private JPanel createCategoryPanel() {
        JPanel jf = new JPanel();
        jf.setLayout (new BoxLayout (jf, BoxLayout.Y_AXIS));  
        jf.setSize(400,600);
        jf.setVisible(true);
        
        if(menu_obj.getMenu().size() > 0) {
	        Set<FoodCategory> keys = menu_obj.getMenu().keySet();
	        for(FoodCategory key: keys){
	            String categoryName = key.toString();
	            Button categoryButton = new Button(categoryName);
	            categoryButton.addActionListener(new ActionListener() {
	                @Override
	                 public void actionPerformed(ActionEvent e) {
	                	  updateItemsDisplay(key);
	                 }
	            });
	            jf.add(categoryButton);  
	        }
	        //jf.add(createComboButton());
	        //showFirstCategory();
        }        
        return jf;
    }
   
	/**
	 * Shows first category fooditems by default
	 *
    private void showFirstCategory() {
        Map.Entry<FoodCategory, HashMap<String , FoodItem>> entry = menu_obj.getMenu().entrySet().iterator().next();
        String key = entry.getKey().toString();
        addFoodItems(entry.getKey().getCategory());
    }*/
    
	/**
	 * Returns Total cost panel
	 *
	 * @return JPanel panel
	 */
    private JPanel createTotalCostPanel() {
        JPanel tcPanel = new JPanel();
        tcPanel.setLayout(new FlowLayout());
        tcPanel.setSize(400,200);  
        tcPanel.setVisible(true);
    	String totalCostName = String.format("%80s", "Total Cost");

        JLabel totalCostLabel = new JLabel(totalCostName);
        totalCostLabel.setForeground(Color.BLUE);
        totalCostValue = new JLabel(getTotalOrderPrice());
        tcPanel.add(totalCostLabel);
        tcPanel.add(totalCostValue);
        return tcPanel;
    }
    
	/**
	 * Returns Menu header panel
	 *
	 * @return JPanel panel
	 */
    private JPanel createMenuHeaderPanel() {
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new FlowLayout());
        headPanel.setSize(600,200);  
        headPanel.setVisible(true);
    	String headerName = String.format("%-30s%6s%20s%6s\n", "Food Item ", "Price/unit ",
    			"Add/Remove ", "Price ");
        JLabel headerNameLabel = new JLabel(headerName);
        headerNameLabel.setForeground(Color.RED);
        headPanel.add(headerNameLabel);
        return headPanel;
    }

	/**
	 * Adds food items to the FoodItem main panel
	 *
	 */
    private void addFoodItems(FoodCategory categoryName) {
        removePanel(currentFoodItemPanel);
        currentFoodItemPanel = new JPanel();
        
        f.revalidate();
        f.repaint();

        JPanel jf = new JPanel();
        jf.setLayout (new BoxLayout (jf, BoxLayout.Y_AXIS));  
        jf.setSize(400,400);  
        jf.setVisible(true);
        
        jf.add(createMenuHeaderPanel());
        
        HashMap<String, FoodItem> foodItems = menu_obj.getFoodItemsByCategory(categoryName);
        if(foodItems!= null && foodItems.size() > 0) {
	        for (Map.Entry<String, FoodItem> entry : foodItems.entrySet()) {
	        	FoodItem itemValue = entry.getValue();
	            this.labels.add(itemValue);

	        	
	            jf.add(addFoodItem(itemValue));
	            t++;
	            
	            /*if (categoryName == FoodCategory.COMBO.toString()) {
	            	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	            	//String discountDetailStr = String.format("%-10s", itemValue.getDescription());
	            	JLabel discountDetailLabel = new JLabel(itemValue.getDescription());
	            	discountDetailLabel.setForeground(Color.ORANGE);
	            	panel.add(discountDetailLabel);
	            	jf.add(panel);
	            }*/
	            
	        }
        }
        
        jf.add(createTotalCostPanel());
        jf.add(createButtonPanel());
        currentFoodItemPanel.add(jf);
        f.add(currentFoodItemPanel,BorderLayout.CENTER);  
        f.revalidate();
    }
    
	/**
	 * Returns Button panel (Reset & Order)
	 *
	 * @return JPanel panel
	 */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        //Moved To OrderController
        panel.add(resetButton);
        
        //Moved to OrderController
        panel.add(orderButton);
        
        Button closeButton = new Button("Close Menu");
        closeButton.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
            	hideMenuPage();
             }
        });
        
        panel.add(closeButton);
    	return panel;
    }
    
	/**
	 * Returns Food Item panel
	 *
	 * @return JPanel panel
	 */
    private JPanel addFoodItem(FoodItem itemValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        /*
         * 
         */
    	String itemCountLabelName = String.format("%5s", getItemCountFromCart(itemValue));
        JLabel itemCountLabel = new JLabel(itemCountLabelName);
    	String itemPriceLabelName = String.format("%6s", df2.format(itemValue.getPrice()));
        JLabel itemPriceLabel = new JLabel(itemPriceLabelName);
    	String itemCartPriceLabelName = String.format("%6s", df2.format(order.totalItemPrice(itemValue)));
        JLabel itemCartPriceLabel = new JLabel(itemCartPriceLabelName);

    	String minusButtonLabel = String.format("%5s", "-");
    	String plusButtonLabel = String.format("%5s", "+");

        
        this.minusButtons.add(new JButton(minusButtonLabel));
        this.plusButtons.add(new JButton(plusButtonLabel));
        
        
    	String foodItemName = String.format("%-30s", itemValue.getName());
        JLabel foodName = new JLabel(foodItemName);
        panel.add(foodName);
        panel.add(itemPriceLabel);
        panel.add(this.minusButtons.get(t));
        panel.add(itemCountLabel);
        panel.add(this.plusButtons.get(t));
        panel.add(itemCartPriceLabel);
        return panel;
    }
    
	/*
	 * Get item count from the cart
	 *
	 * @return String item count as string
	 *
    private String getItemCountFromCart(String itemKey) {
        int count = 0;
        if (cart.containsKey(itemKey)) {
            count = cart.get(itemKey).intValue();
        }
        return Integer.toString(count);
    }*/
    
    /**
     * Method to update the display where all the items are shown
     * */
    
    public void updateItemsDisplay(FoodCategory cat) {  	
    	addFoodItems(cat);    	
    }
    
    
    /**
	 * Get item count from the cart
	 *
	 * @return String item count as string
	 */
    private String getItemCountFromCart(FoodItem f) {
        
        return Integer.toString(order.itemCount(f));
    }
    /**
   	 * 
   	 *
   	 * @return String Total Price of the order
   	 */
       private String getTotalOrderPrice() {
           
           return  order.getFinalBillAmount().toString();
       }
    
 

   
    
	/**
	 * Removes the fooditems panel
	 *
	 */
    private void removePanel(JPanel tempPanel) {
        Component[] componentList = currentFoodItemPanel.getComponents();
        for(Component c : componentList){

            if(c instanceof JCheckBox){
                currentFoodItemPanel.remove(c);
            }
        }
        currentFoodItemPanel.revalidate();
        currentFoodItemPanel.repaint();
        f.remove(currentFoodItemPanel);
    }

	
    /**
	 * Add Action Listener to all ADD buttons in the menu
	 * */
	public void setAddButtonsActionListener(ActionListener al) {
		for(int i = 0; i < plusButtons.size(); i++) {
			controller.setNewItem(labels.get(i));
			controller.setItemCategory(labels.get(i));
			plusButtons.get(i).addActionListener(al);
		}
	}
	/**
	 * Add Action Listener to all Remove buttons in the menu
	 * */	
	public void setRemoveButtonsActionListener(ActionListener al) {
		for(int i = 0; i < plusButtons.size(); i++) {
			controller.setNewItem(labels.get(i));
			controller.setItemCategory(labels.get(i));
			minusButtons.get(i).addActionListener(al);
		}
	}
	/**
	 * Add Action Listener "Remove All Items" button in the menu
	 * */	
	public void setRemoveAllActionListener(ActionListener al) {
		resetButton.addActionListener(al);
	}
	/**
	 * Add Action Listener "Order" button in the menu
	 * @func open the customerOrderGUI JDialog
	 * 
	 * */	
	public void setOrderActionListener(ActionListener al) {
		orderButton.addActionListener(al);
	}
}