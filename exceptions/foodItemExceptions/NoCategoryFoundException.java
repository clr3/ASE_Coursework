package foodItemExceptions;

@SuppressWarnings("serial")
public class NoCategoryFoundException extends Exception{


	public NoCategoryFoundException() {
		
		super("The item ID does not match any category");
	}
}
