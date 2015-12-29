package coupon.dao;

import coupon.Logger;
import coupon.model.Coupon;
import coupon.model.CouponType;
import coupon.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by farhod on 29/12/15.
 */
public class CustomerDBDAO implements CustomerDAO {


    @Override
    public void createCustomer(Customer c) throws SQLException
    {
        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "INSERT INTO Customer (CUST_NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCustName());
        pst.setString(2, c.getPassword());
        pst.setString(3, c.getEmail());

        //Execute
        pst.execute();

        //log the fact
        Logger.log("Customer added", pst.toString());

        //Clean up
        pst.close();
        con.close();


    }

    @Override
    public void removeCustomer(Customer c) throws SQLException
    {
        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "DELETE FROM Customer WHERE CUST_NAME = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCustName());

        //Execute
        pst.execute();

        //log it
        Logger.log("Customer deleted", pst.toString());

        //Clean up
        pst.close();
        con.close();
    }

    @Override
    public void updateCustomer(Customer c)throws SQLException
    {

        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "UPDATE Customer SET CUST_NAME = ?, PASSWORD = ?, EMAIL = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCustName());
        pst.setString(2, c.getPassword());
        pst.setString(3, c.getEmail());

        //execute
        pst.execute();

        //log it
        Logger.log("Customer updated", pst.toString());

        //clean up
        pst.close();
        con.close();

    }

    @Override
    public Customer getCustomer(long id) throws SQLException
    {
        //Create a customer instance to return (meanwhile null)
        Customer customer = null;

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "SELECT * FROM Customer WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setLong(1, id);

        //Get result set
        ResultSet rs = pst.executeQuery();

        //Read the result set
        if(rs.next())
        {
            customer = new Customer(rs.getString("CUST_NAME"),
                    rs.getString("PASSWORD"), rs.getString("EMAIL"));
            customer.setId(rs.getLong("ID"));
        }

        //Log it
        if(customer != null)
            Logger.log("Customer Successfully read from DB", pst.toString());
        else
            Logger.log("No customer was found", pst.toString());

        //Clean up
        pst.close();
        rs.close();
        con.close();

        //If nothing was found null will be returned
        return customer;
    }

    @Override
    public Collection<Customer> getAllCustomers() throws SQLException
    {
        //Create a collection that will be returned
        Collection<Customer> customers = new LinkedList<>();

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "SELECT * FROM Customer";
        PreparedStatement pst = con.prepareStatement(sql);

        //Get the result set
        ResultSet rs = pst.executeQuery();

        //Read the result set
        int customerCount = 0;
        while(rs.next())
        {
            Customer c = new Customer(rs.getString("CUST_NAME"),
                    rs.getString("PASSWORD"), rs.getString("EMAIL"));
            c.setId(rs.getLong("ID"));

            customers.add(c);
            customerCount++;
        }

        //Log it
        Logger.log(customerCount + " customers were loaded", pst.toString());

        //CLean up
        pst.close();
        rs.close();
        con.close();

        return customers;
    }

    @Override
    public Collection<Coupon> getAllCoupons(Customer c)throws SQLException
    {
        //Create return list
        Collection<Coupon> allPossesedCoupons = new LinkedList<>();

        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Get all ID's of the coupons that belong to the customer
        String sql = "SELECT * FROM Coupon " +
                "WHERE Coupon.ID IN (  " +
                "SELECT COUPON_ID " +
                "FROM Customer_Coupon " +
                "WHERE CUST_ID = ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setLong(1, c.getId());

        //Execute and catch the resultSet
        ResultSet rs = pst.executeQuery();

        //Read the contents to the list
        int counter = 0;
        while(rs.next())
        {
            Coupon coupon = new Coupon
                    (
                            rs.getString("TITLE"), rs.getDate("START_DATE").toLocalDate(),
                            rs.getDate("END_DATE").toLocalDate(), CouponType.valueOf(rs.getString("TYPE")),
                            rs.getInt("AMOUNT"), rs.getDouble("PRICE"), rs.getString("IMAGE_PATH"),
                            rs.getString("MESSAGE")
                    );
            allPossesedCoupons.add(coupon);
            counter++;
        }
        //Log it
        Logger.log(counter + " coupons were loaded", pst.toString());

        //Clean up
        pst.close();
        con.close();

        return allPossesedCoupons;
    }

    @Override
    public boolean login(String name, String pass)throws SQLException
    {
        boolean success = false;

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //prepare a statement
        String sql = "SELECT CUST_NAME, PASSWORD FROM Customer WHERE CUST_NAME = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, name);

        //Get a result if there is one
        ResultSet rs = pst.executeQuery();
        if(rs.next())
        {
            //Check if it matches
            if(name.equals(rs.getString("CUST_NAME"))
                    && pass.equals(rs.getString("PASSWORD")))
            {
                success = true;
                Logger.log("User " + name + " logged", "Password and username match");
            }
            else {
                success = false;
                Logger.log("User " + name + " logging failed", "Incorrect Password or Username");
            }
        }
        else
        {
            //TODO: Create exceptions and throw them here
            Logger.log("User " + name + " was not found", pst.toString());
        }

        //Clean up
        pst.close();
        rs.close();
        con.close();

        return success;

    }

    @Override
    public boolean exists(Customer c) throws SQLException
    {
        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "SELECT EXISTS(SELECT 1 FROM Customer WHERE CUST_NAME=?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCustName());

        //Execute it and get the result set
        ResultSet rs = pst.executeQuery();

        //Check the answer
        rs.next();
        boolean exists = rs.getBoolean(1);

        //Log it
        Logger.log("Customer exists check", "Customer '" + c.getCustName() +
                ((exists)?"' exists":"' doesn't exist"));

        //Clean up
        rs.close();
        con.close();
        return exists;
    }


}
