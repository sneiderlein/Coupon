package coupon.dao;

import coupon.Logger;
import coupon.exception.CouponDBException;
import coupon.exception.CouponException;
import coupon.model.Company;
import coupon.model.Coupon;

import java.sql.*;
import java.util.*;

public class CompanyDBDAO implements CompanyDAO {

    @Override
    public void createCompany(Company c) throws SQLException
    {
        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCompName());
        pst.setString(2, c.getPassword());
        pst.setString(3, c.getEmail());

        //Execute
        pst.execute();

        //log the fact
        Logger.log("Company added", pst.toString());

        //Clean up
        pst.close();
        con.close();
    }

    @Override
    public void removeCompany(Company c) throws SQLException
    {
        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "DELETE FROM Company WHERE COMP_NAME = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCompName());

        //Execute
        pst.execute();

        //log it
        Logger.log("Company deleted", pst.toString());

        //Clean up
        pst.close();
        con.close();

    }

    @Override
    public void updateCompany(Company c)throws SQLException
    {
        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "UPDATE Company SET COMP_NAME = ?, PASSWORD = ?, EMAIL = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCompName());
        pst.setString(2, c.getPassword());
        pst.setString(3, c.getEmail());

        //execute
        pst.execute();

        //log it
        Logger.log("Company updated", pst.toString());

        //clean up
        pst.close();
        con.close();

    }

    @Override
    public Company getCompany(long id) throws SQLException
    {
        //Create a company instance to return (meanwhile null)
        Company companyToReturn = null;

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "SELECT * FROM Company WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setLong(1, id);

        //Get result set
        ResultSet rs = pst.executeQuery();

        //Read the result set
        if(rs.next())
        {
            companyToReturn = new Company(rs.getString("COMP_NAME"),
                                                rs.getString("PASSWORD"), rs.getString("EMAIL"));
            companyToReturn.setId(rs.getLong("ID"));

        }

        //Log it
        if(!(companyToReturn == null))
            Logger.log("Company Successfully read from DB", pst.toString());
        else
        Logger.log("No company was found", pst.toString());

        //Clean up
        pst.close();
        rs.close();
        con.close();

        //If nothing was found null will be returned
        return companyToReturn;
    }

    @Override
    public Collection<Company> getAllCompanies() throws SQLException
    {
        //Create a collection that will be returned
        Collection<Company> companies = new LinkedList<>();

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "SELECT * FROM Company";
        PreparedStatement pst = con.prepareStatement(sql);

        //Get the result set
        ResultSet rs = pst.executeQuery();

        //Read the result set
        int companyCount = 0;
        while(rs.next())
        {
            Company c = new Company(rs.getString("COMP_NAME"),
                    rs.getString("PASSWORD"), rs.getString("EMAIL"));
            c.setId(rs.getLong("ID"));

            companies.add(c);
            companyCount++;
        }

        //Log it
        Logger.log(companyCount + " companies were loaded", pst.toString());

        //CLean up
        pst.close();
        rs.close();
        con.close();

        return companies;
    }

    @Override
    public Collection<Coupon> getCoupons()
    {

        //TODO: Implement when working with coupons
        return null;
    }

    @Override
    public boolean login(String name, String pass)throws SQLException
    {
        boolean success = false;

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //prepare a statement
        String sql = "SELECT COMP_NAME, PASSWORD FROM Company WHERE COMP_NAME = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, name);

        //Get a result if there is one
        ResultSet rs = pst.executeQuery();
        if(rs.next())
        {
            //Check if it matches
            if(name.equals(rs.getString("COMP_NAME"))
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
    public boolean exists(Company c) throws SQLException {
        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "SELECT EXISTS(SELECT 1 FROM Company WHERE COMP_NAME=?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getCompName());

        //Execute it and get the result set
        ResultSet rs = pst.executeQuery();

        //Check the answer
        rs.next();

        boolean exists = rs.getBoolean(1);

        //Clean up
        rs.close();
        con.close();
        return exists;
    }


}
