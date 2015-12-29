package coupon.test;

import coupon.dao.*;
import coupon.exception.CouponDBException;
import coupon.exception.CouponException;
import coupon.model.Company;
import coupon.model.Coupon;
import coupon.model.CouponType;
import coupon.model.Customer;

import java.time.*;
import java.util.*;


public class Test {


    public static void main(String[] args) throws Exception{

        CouponDAO dao = new CouponDBDAO();


        String message = "Coupon shmoupon!";

        Coupon c1 = new Coupon(
                "The Shmoupon",  LocalDate.now(), LocalDate.of(2016, 2, 27),
                CouponType.FOOD, 14, 78.23, "THEPATHHH!", message);

           c1.setId(6);
        System.out.println(dao.exists(c1));



       }


}