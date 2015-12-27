package test;

import com.mchange.v2.c3p0.*;
import dao.CompanyDBDAO;
import dao.DBConnection;
import dao.DBIOHelper;
import exception.CouponException;
import model.Company;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class Test {


    public static void main(String[] args) {

        CompanyDBDAO dao = new CompanyDBDAO();

        try {
        Company c1 = new Company(2, "IBM", "passser", "jjdh@asddd.com");
        Company c2 = new Company(3, "2IBM", "passser", "jjdh@asddd.com");
        Company c3 = new Company(4, "3IBM", "passser", "jjdh@asddd.com");
        Company c4 = new Company(5, "4IBM", "passser", "jjdh@asddd.com");




//            dao.createCompany(c1);
//            System.out.println("I never come here");
//            dao.createCompany(c2);
//            dao.createCompany(c3);
//            dao.createCompany(c4);
            DBIOHelper helper = new DBIOHelper();

            Map<String, String> sendingMap = new HashMap<>();
            sendingMap.put("COMP_NAME", c1.getCompName());
            sendingMap.put("PASSWORD", c1.getPassword());
            sendingMap.put("EMAIL", c1.getEmail());

            helper.addRecord("Company",sendingMap);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(CouponException e)
        {
            System.out.println(e.toString());
        }

        System.out.println("Exiting");
    }
}
