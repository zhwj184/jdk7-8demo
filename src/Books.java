import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

 public  class Books{

        public boolean myMethod(int p1, Object p2) throws SecurityException, IOException {
//        	LogManager.getLogManager().readConfiguration();
            // Log object entry
            Logger logger = Logger.getLogger("Books");
//            logger.log(Level.SEVERE, "ddd")
            logger.setLevel(Level.SEVERE);
            Handler[] handlers = logger.getHandlers();
            for(Handler handle: handlers){
            	handle.setLevel(Level.SEVERE);
            }
            if (logger.isLoggable(Level.SEVERE)) {
                logger.entering(this.getClass().getName(), "Method",
                                new Object[]{new Integer(p1), p2});
            }
    
            // Method body
    
            // Log exiting
            boolean result = true;

            if (logger.isLoggable(Level.SEVERE)) {
                logger.exiting(this.getClass().getName(), "Method", new Boolean(result));
    
                //If the method does not return a value
                logger.exiting(this.getClass().getName(), "Method");
            }

            return result;
         }
        public static void main(String[] args) throws SecurityException, IOException {
			new Books().myMethod(1, new HashMap());
		}
    }