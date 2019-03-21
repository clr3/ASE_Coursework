package utilities;

/**Given a number,  start a countdown..
 * 
 * This class will be used to measure the time it should take for processing an order 
 * */

public class Countdown {
	
	private int count;//60 seconds
	
	public void Countdown() {}
	
	public int getTimeForOneMin() {
		return count = 60000;
	}
	
	public int updateCount() {
		if(count != 0)	return --count;
		else {
			return 0;
		}
	}
	
}
