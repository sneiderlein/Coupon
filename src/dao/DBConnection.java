package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.*;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


public class DBConnection {

    /*
    *
    * Connection pool helper class
    *
    * Wraps c3p0 connection pool and provides a way to acquire connection from
    * pool easily.
    *
     */

    private static ComboPooledDataSource dataSource;
    public static final String DRIVER_NAME;
    private static final String URL;
    private static final String USER_NAME;
    private static final String PSWD;

    static{

        //TODO: load info about the connection drivers and sql host info from file
        DRIVER_NAME = "com.mysql.jdbc.Driver";
        URL = "jdbc:mysql://sql6.freemysqlhosting.net/sql6100791";
        USER_NAME = "sql6100791";
        PSWD = "smBrxXSKNy";

        dataSource = initDataSource();
    }



    private static ComboPooledDataSource initDataSource()
    {
        //Runs once when the class is referenced so that only one pool is created

        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            //TODO: Throw custom exception
        }
        ds.setJdbcUrl(URL);
        ds.setUser(USER_NAME);
        ds.setPassword(PSWD);
        return ds;
    }


    public static Connection getConnection() throws SQLException
    {
        //borrows a connection from pool and returns it
        return dataSource.getConnection();
    }

    public static void clean()
    {
        dataSource.close();
    }

    }
