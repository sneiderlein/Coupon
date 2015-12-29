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

        CouponDAO couponDAO = new CouponDBDAO();
        CompanyDAO companyDAO = new CompanyDBDAO();
        CustomerDAO customerDAO = new CustomerDBDAO();




       Customer cust = customerDAO.getCustomer(3);
        System.out.println("Coupons that belong to "+ cust.getCustName());
        System.out.println(customerDAO.getAllCoupons(cust));



       }


}