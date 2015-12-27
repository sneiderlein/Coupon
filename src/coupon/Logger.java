package coupon;

import java.time.LocalDateTime;

public class Logger {

    /*
    Attributes
    */
    private static boolean logToTerminal = true;
    private static boolean logToFile = false;
    private static boolean logExceptions = true;

    
    /*
    Methods
    */

    public static void log(String title, String message)
    {
        LocalDateTime time = LocalDateTime.now();
        if(logToTerminal)
            logToTerminal(time, title, message);
        if(logToFile)
            logToFile(time, title, message);


    }

    public static void log(Exception e)
    {
        log(e.getClass().getSimpleName(),e.getMessage());
    }

    private static void logToTerminal(LocalDateTime time, String title, String message)
    {
        System.out.println(time + "-> " + title + " : " + message);
    }

    private static void logToFile(LocalDateTime time, String title, String message)
    {

    }

    public static void exLog(String titleMessage, String description) {

        if(logExceptions)
            log(titleMessage,description);

    }
}
