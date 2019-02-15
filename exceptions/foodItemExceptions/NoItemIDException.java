package foodItemExceptions;

public class NoItemIDException extends Exception {
	
	public NoItemIDException() {
		
		super("Missing item ID");
	}
}
