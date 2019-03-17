package CoffeeShopGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CoffeeShopController.StartPageController;
import CoffeeShopModel.OrderManager;


/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * JPanel which provides access to: 
 *  				menu (for customers)
 * 					staff view 
 * */

public class StartPageGUI extends JPanel{
	

	private GridBagConstraints constraints = new GridBagConstraints();
	
	private JFrame frame;
	
	private JButton staffButton = new JButton("Staff");
	private JButton customerButton = new JButton("View Menu");;
	private JButton exitButton = new JButton("Exit");
	private MenuGUI menu_gui;
	private StartPageController controller = new StartPageController(this);
	
	
	//private MenuController menuController;
	private OrderManager om;
	

	/**Initialise */
	public StartPageGUI() {}
  
	public StartPageGUI(JFrame frame ) {
		start(frame);
	}
	
	public void start(JFrame frame) {
		this.frame = frame;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(staffButton);
        buttonPanel.add(customerButton);
        buttonPanel.add(exitButton);
        
        JLabel b1=new JLabel("    Coffee Shop");;  
		JLabel wIcon = new JLabel("");
		String pwd = System.getProperty("user.dir");
		File imgFolder = new File(pwd + '/' + "images");
        BufferedImage wPic;
		try {
			wPic = ImageIO.read(new File((imgFolder+"/coffee.png")));
	        wIcon = new JLabel(new ImageIcon(wPic));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(wIcon);
        headerPanel.add(b1);
        
        JLabel b2=new JLabel("Copyright @ HW");;  
          
      
        JPanel panel = new JPanel();
        panel.setSize(600,600);
        panel.setLayout(new BorderLayout());        
        panel.add(buttonPanel,BorderLayout.CENTER);
        panel.add(headerPanel,BorderLayout.PAGE_START); 
        panel.add(b2, BorderLayout.SOUTH); 
        add(panel);
        
	}
	

	
	/**Create Customer Button Action Listener
	 * */
	public void setCustomerButtonActionListener(ActionListener al) {
		customerButton.addActionListener(al);
	}
	
	
	/**
	 * Create Button to exist the app
	 * */
	public void addExitActionListener(ActionListener al) {
		exitButton.addActionListener(al);
	}
	/**
	 * Create Staff Button 
	 * */
	public void addStaffActionListener(ActionListener al) {
		staffButton.addActionListener(al);
	}
	public void closeFrame() {
		frame.setVisible(false);
		//frame.dispose();
	}
	
	
}
