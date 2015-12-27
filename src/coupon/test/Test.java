package coupon.test;

import coupon.dao.CompanyDBDAO;
import coupon.dao.DBIOHelper;
import coupon.exception.CouponDBException;
import coupon.exception.CouponException;
import coupon.model.Company;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class Test {


    public static void main(String[] args) throws SQLException {

//        CompanyDBDAO dao = new CompanyDBDAO();
//
//        try {
//        Company c1 = new Company("Hello", "Something", "asddd@asddd.com");
//        Company c2 = new Company("2IByeBM", "jkasldkj", "jjdh@asddd.com");
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
//        Map<String, String> shit = new HashMap<>();
//        shit.put("COMP_NAME", "thename");
//        shit.put("PASSWORD", "thepass");
//        shit.put("EMAIL", "theMail");


        System.out.println(helper.getRecord("Company", 19L, null));

        System.out.println("Exiting");
    }
}
