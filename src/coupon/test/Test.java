package coupon.test;

import coupon.dao.CompanyDAO;
import coupon.dao.CompanyDBDAO;
import coupon.dao.DBIOHelper;
import coupon.exception.CouponDBException;
import coupon.exception.CouponException;
import coupon.model.Company;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Test {


    public static void main(String[] args) throws SQLException {

//        CompanyDBDAO dao = new CompanyDBDAO();
//
//        try {
        Company c1 = new Company("weeeeee", "OOOOOO", "asdasdasjgjgjgjg@asddd.com");
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
        CompanyDBDAO dao = new CompanyDBDAO();


        c1.setId(21);

        System.out.println(dao.login("Joy1", "theNewPass"));




        System.out.println("Exiting");
    }

    public static void printTable(String table)
    {
        DBIOHelper helper = new DBIOHelper();
        Collection<Map<String, String> > map = null;
        try {
            map = helper.getAllRecords(table, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Map<String, String> lil : map)
        {
            System.out.println(lil);
        }
    }
}
