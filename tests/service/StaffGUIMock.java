package service;

import views.StaffGUI;
import views.TimerGUI;

public class StaffGUIMock extends StaffGUI {
	public StaffGUIMock(OrderManager o, StaffManager sm, TimerGUI timerGui) {
		super(o, sm, timerGui);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void reRenderQueue() {
		System.out.println("UI mock");
	}
	
	@Override
	public void createPage() {
		System.out.println("UI mock");
	}

}
