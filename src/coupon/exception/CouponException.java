package coupon.exception;

import coupon.Logger;

import java.time.LocalDate;
import java.time.LocalTime;


public class CouponException extends RuntimeException {
    /*
        Attributes
        */
    protected String titleMessage;
    protected String description;
    protected LocalDate date;
    protected LocalTime time;


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

        Logger.exLog(titleMessage, description);
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
