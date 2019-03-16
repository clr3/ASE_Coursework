package CoffeeShopUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Logger {
	   private static Logger log_instance = null;
	   public static final String LOGFILE_PREFIX = "CoffeeShop";
   	   private static File logFile;

	   protected Logger() {
	       createLogFile();
	   }
	   public static Logger getInstance() {
	      if(log_instance == null) {
	    	  log_instance = new Logger();
	      }
	      return log_instance;
	   }
	   
		public void createLogFile(){
			String pwd = System.getProperty("user.dir");
			File logsFolder = new File(pwd + '/' + "logs");
			if(!logsFolder.exists()){
				logsFolder.mkdir();
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Calendar cal = Calendar.getInstance();
		   	
		   	String logFileName =  LOGFILE_PREFIX + '-' +  dateFormat.format(cal.getTime()) + ".log";
			logFile = new File(logsFolder.getName(),logFileName);
			try{
				logFile.createNewFile();
			}catch(IOException e){
				System.err.println("ERROR: Cannot create log file");
			}
		}
		
		public void log(String message){
			try{
				FileWriter out = new FileWriter(logFile, true);
				out.write(message.toCharArray());
				out.write(System.lineSeparator());
				out.close();
			}catch(IOException e){
				System.err.println("ERROR: Could not write to log file");
			}
		}
	}