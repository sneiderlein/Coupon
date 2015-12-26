package test;

import com.mchange.v2.c3p0.*;

import java.sql.*;


public class Test {

    /*
    Attributes
    */
    
    
    
    /*
    Constructors
    */
    
    
    /*
    Methods
    */


    public static void main(String[] args) throws Exception{


        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://sql6.freemysqlhosting.net/sql6100791");
        cpds.setUser("sql6100791");
        cpds.setPassword("smBrxXSKNy");

        Connection con = cpds.getConnection();
//
//        Statement st = con.createStatement();

       // Class.forName("com.mysql.jdbc.Driver").newInstance();
        //Connection con = DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net/sql6100791", "sql6100791", "smBrxXSKNy");

        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO Company (ID, COMP_NAME, PASSWORD, EMAIL) VALUES ('1', 'somename', 'pass', 'email');"
        );

        pst.executeUpdate();

//        st.close();
        pst.close();
        con.close();

    }
}
