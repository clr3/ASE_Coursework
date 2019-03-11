/**
* Menu order GUI class for Coffee Shop
*/
package CoffeeShopGUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CoffeeShopController.MenuController;
import CoffeeShopUtilities.FoodCategory;
import CoffeeShopUtilities.FoodItem;
import CoffeeShopUtilities.Menu;
import CoffeeShopUtilities.OrderManager;

/**
* Menu order GUI class for Coffee Shop
*
* @author  Arthidevi Balavignesh
*/
public class MenuGUI extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame f =new JFrame();
    private JPanel currentFoodItemPanel = new JPanel();
    private JPanel totalCostPanel = new JPanel();
    private JLabel totalCostValue;
    public HashMap<String,Integer> cart = new HashMap<String,Integer>();
    public double totalCost = 0;
    public Menu menu_obj;
	private static DecimalFormat df2 = new DecimalFormat("###.##");
	private OrderManager om;
	private MenuController mc;
    /** 
     * Constructor for Menu GUI
     * 
     * @param Menu menu_obj1
     */
    public MenuGUI(Menu menu_obj1,OrderManager omgr, MenuController mc){
    		this.mc = mc;
    		this.om = omgr;
    		this.menu_obj = menu_obj1;
   }
    
    public void createPage() {
        JPanel jp = new JPanel();
        jp.add(createCategoryPanel());
        JButton b1=new JButton("Coffee Shop - Food Menu");;  
        JButton b2=new JButton("Copyright @ HW");;  
          
        f.add(b1,BorderLayout.NORTH);  
        f.add(b2,BorderLayout.SOUTH);  
        f.add(jp,BorderLayout.WEST);  
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
	            categoryButton.addActionListener(mc.categoryActionListener(categoryName));
	            jf.add(categoryButton);  
	        }
	        //jf.add(createComboButton());
	        mc.showFirstCategory();
        }        
        return jf;
    }
   

    
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
        totalCostValue = new JLabel(df2.format(totalCost));
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
    public void addFoodItems(String categoryName ) {
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
	            String itemKey = entry.getKey();
	            FoodItem itemValue = entry.getValue();
	            jf.add(addFoodItem(categoryName, itemKey, itemValue));
	            if (categoryName == FoodCategory.COMBO.toString()) {
	            	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	            	//String discountDetailStr = String.format("%-10s", itemValue.getDescription());
	            	JLabel discountDetailLabel = new JLabel(itemValue.getDescription());
	            	discountDetailLabel.setForeground(Color.ORANGE);
	            	panel.add(discountDetailLabel);
	            	jf.add(panel);
	            }
	            
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
        
        Button resetButton = new Button("Reset");
        resetButton.addActionListener(mc.resetButtonActionListener());
        panel.add(resetButton);
        
        Button orderButton = new Button("Order Food");
        orderButton.addActionListener(mc.orderButtonActionListener());
        panel.add(orderButton);
        
        Button closeButton = new Button("Close Menu");
        closeButton.addActionListener(mc.closeButtonActionListener());
        panel.add(closeButton);
    	return panel;
    }
    
	/**
	 * Returns Food Item panel
	 *
	 * @return JPanel panel
	 */
    private JPanel addFoodItem(String category, String itemKey, FoodItem itemValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
    	String itemCountLabelName = String.format("%5s", mc.getItemCountFromCart(itemKey));
        JLabel itemCountLabel = new JLabel(itemCountLabelName);
    	String itemPriceLabelName = String.format("%6s", df2.format(itemValue.getPrice()));
        JLabel itemPriceLabel = new JLabel(itemPriceLabelName);
    	String itemCartPriceLabelName = String.format("%6s", df2.format(mc.getItemCartPrice(itemKey, itemValue.getPrice())));
        JLabel itemCartPriceLabel = new JLabel(itemCartPriceLabelName);

    	String minusButtonLabel = String.format("%5s", "-");
        Button minusButton = new Button(minusButtonLabel);
        minusButton.addActionListener(mc.minusButtonActionListener(category, itemKey,itemValue, itemCountLabel, itemCartPriceLabel));
        
    	String plusButtonLabel = String.format("%5s", "+");
        Button plusButton = new Button(plusButtonLabel);
        plusButton.addActionListener(mc.plusButtonActionListener(category, itemKey,itemValue, itemCountLabel, itemCartPriceLabel));
        
    	String foodItemName = String.format("%-30s", itemValue.getName());
        JLabel foodName = new JLabel(foodItemName);
        panel.add(foodName);
        panel.add(itemPriceLabel);
        panel.add(minusButton);
        panel.add(itemCountLabel);
        panel.add(plusButton);
        panel.add(itemCartPriceLabel);
        return panel;
    }
    

   
	/**
	 * Add item to the cart, updates item price and total cost
	 *
	 */
    public void addItemToCart(String category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
    	int count = mc.addItemToCart(itemKey);
        itemCountLabel.setText(Integer.toString(count));
        double itemTotalPrice =  itemValue.getPrice();
        totalCost += itemTotalPrice;
        itemTotalPrice = itemTotalPrice * count;
        itemCartPriceLabel.setText(df2.format(itemTotalPrice));
        itemCartPriceLabel.revalidate();
        itemCartPriceLabel.repaint();
        totalCostValue.setText(df2.format(totalCost));
        totalCostPanel.revalidate();
        totalCostPanel.repaint();
    }
    
	/**
	 * Remove item from the cart, updates item price and total cost
	 *
	 */
    public void removeItemFromCart(String category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
        int count = 0;
        double itemTotalPrice =  itemValue.getPrice();
        if (cart.containsKey(itemKey)) {
        	count = mc.removeItemFromCart(itemKey);
            totalCost = totalCost - itemTotalPrice;
            totalCostValue.setText(df2.format(totalCost));
            totalCostPanel.revalidate();
            totalCostPanel.repaint();
        }
        itemCountLabel.setText(Integer.toString(count));
        itemTotalPrice = itemTotalPrice * count;
        itemCartPriceLabel.setText(df2.format(itemTotalPrice));
        itemCartPriceLabel.revalidate();
        itemCartPriceLabel.repaint();
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
    
}