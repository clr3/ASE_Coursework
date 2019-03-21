package service;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.StaffGuiController;
import model.FoodCategory;

public class TimerGUI{
	private OrderManager orderManager = OrderManager.getInstance();
	private StaffManager smanager;
	private JFrame f =new JFrame();
	public HashMap<FoodCategory, JTextField> processTimeInputMapList = new HashMap<FoodCategory, JTextField>();
	 JButton b = new JButton("Save"); 
	 
	private StaffGuiController control;
	 
	public TimerGUI(StaffManager sm, StaffGuiController c) {
		this.smanager = sm;
		//this.control = c;
		//this.orderManager.timerPage = this;
		createPage();
	}
	
	public TimerGUI getPage() {
		return this;
	}
    public void createPage() {
        JPanel jp = new JPanel();
        jp.add(createProcessTimePanel());
        JButton b1=new JButton("Coffee Shop - Order Processing Time Settings");;  
        JButton b2=new JButton("Copyright @ HW");;  
          
        f.add(b1,BorderLayout.NORTH);  
        f.add(b2,BorderLayout.SOUTH);  
        //f.add(jp,BorderLayout.WEST);  
        f.add(jp,BorderLayout.CENTER);  

        f.setSize(600,600);  
        f.setVisible(false); 
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
	
	
    private JPanel createProcessTimePanel() {
    	processTimeInputMapList.clear();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout( p, BoxLayout.Y_AXIS));
    	for (HashMap.Entry<FoodCategory, Integer> entry : orderManager.getAllProcessTime().entrySet()) {
    	    FoodCategory key = entry.getKey();
    	    int value = entry.getValue();
    	    int timeInSec = value / 1000;
            JLabel l = new JLabel(key.toString());
            JTextField t = new JTextField(5);
            t.setText(String.valueOf(timeInSec));
            processTimeInputMapList.put(key, t);
            
           
            b.addActionListener(control.processTimeActionListener(key));

    		JPanel p1 = new JPanel();
    		p1.setLayout(new FlowLayout());
    		p1.add(l);
    		p1.add(t);
    		p1.add(b);
            p.add(p1);
    	}
    	return p;
    }
    
    public void showTimerPage() {
    	f.setVisible(true);
    }

    public void addSaveActionListener(ActionListener al) {
		b.addActionListener(al);
	}
}