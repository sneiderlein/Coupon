package exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CouponException extends RuntimeException{

    /*
    Attributes
    */
    private String titleMessage;
    private String description;
    private LocalDate date;
    private LocalTime time;
    
    
    /*
    Constructors
    */
    public CouponException(String message)
    {
        this(message, "");
    }

    public CouponException(String titleMessage, String description)
    {
        super(titleMessage);
        this.titleMessage = titleMessage;
        this.description = description;

        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    /*
    Getters
     */

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return titleMessage;
    }

    /*
    Methods
     */

    @Override
    public String toString() {
        return date.toString() + " | "+ time.toString() + " -> "
                + titleMessage + ": " + description;
    }
}
