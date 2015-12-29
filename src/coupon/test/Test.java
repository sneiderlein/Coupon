package coupon.test;

import coupon.dao.*;
import coupon.exception.CouponDBException;
import coupon.exception.CouponException;
import coupon.model.Company;
import coupon.model.Coupon;
import coupon.model.CouponType;
import coupon.model.Customer;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class Test {


    public static void main(String[] args) throws SQLException {

        Customer c1 = new Customer("My name", "My password is", "My email is");
        Customer c2 = new Customer("Your name is", "Your password is", "your email is");

        CustomerDAO cDao = new CustomerDBDAO();


        System.out.println(cDao.getCustomer(2));


       }


}