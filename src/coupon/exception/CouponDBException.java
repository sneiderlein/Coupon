package coupon.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class CouponDBException extends CouponException{

    public CouponDBException(String titleMessage, String description) {
        super(titleMessage, description);
    }
}
