package coupon.test;

import coupon.dao.CompanyDAO;
import coupon.dao.CompanyDBDAO;
import coupon.dao.DBIOHelper;
import coupon.exception.CouponDBException;
import coupon.exception.CouponException;
import coupon.model.Company;
import coupon.model.Coupon;
import coupon.model.CouponType;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class Test {


    public static void main(String[] args) throws SQLException {

//        CompanyDBDAO dao = new CompanyDBDAO();
//
//        try {
        Company c1 = new Company("WHeeel", "asdasdOOOOOO", "ererjgjg@asddd.com");
        c1.setId(21);
        Company c2 = new Company("2IByeBM", "jkasldkj", "jjdh@asddd.com");
//        Company c3 = new Company("Joy", "asldkjjfi", "jjdh@asddd.com");
//        Company c4 = new Company("Sway", "irotieo", "jjdh@asddd.com");
//
//
//
//
//            DBIOHelper helper = new DBIOHelper();
//            helper.deleteRecord("Company", 12l, null);
//
//            dao.createCompany(c1);
//            dao.createCompany(c2);
//            dao.createCompany(c3);
//            dao.createCompany(c4);
//
//
//
//        }
//        catch(CouponDBException e)
//        {
//            e.printStackTrace();
//        }
//        catch(CouponException e)
//        {
//            System.out.println(e.toString());
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//        }

        DBIOHelper helper = new DBIOHelper();
        CompanyDBDAO dao = new CompanyDBDAO();


        SortedMap<String, Object> map = new TreeMap<>();

        map.put("AMOUNT", "76");
        map.put("END_DATE", Date.valueOf(LocalDate.now()));
        map.put("MESSAGE", "BLAHVLAHBLAH");
        map.put("PRICE", "89.34");
        map.put("START_DATE", Date.valueOf(LocalDate.now()));
        map.put("TITLE", "Other coupon");
        map.put("TYPE", CouponType.FOOD);





 //       dao.createCompany(c2);

        try {
            System.out.println(dao.login("Jdsdsd", "asdasd"));
        }
        catch(CouponException e)
        {

        }
    }

}