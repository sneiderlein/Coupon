package coupon.dao;

import coupon.Logger;
import coupon.exception.CouponDBException;
import coupon.model.Company;
import coupon.model.Coupon;

import java.sql.*;
import java.util.*;

public class CompanyDBDAO implements CompanyDAO {


    /*
    Attributes
    */

    private DBIOHelper helper;



    /*
    Constructors
    */

    public CompanyDBDAO()
    {
        helper = new DBIOHelper();
    }
    
    /*
    Methods
    */

    @Override
    public void createCompany(Company c)throws CouponDBException {

        //Helper accepts a Map containing all company info so we will create one
        Map<String, String> companyMap = companyToMap(c);

        try (Connection con = DBConnection.getConnection())
        {

            boolean exists = helper.cellExists("Company", "COMP_NAME",c.getCompName(), con );
            if(exists) {
                throw new CouponDBException("Record already exists", c.getCompName() + " already exists in DB.");
            }
            helper.addRecord("Company",companyMap, con);
            Logger.log("Created Company", "Company " + c.getCompName() + " was successfully created");

        }
        catch (SQLException e)
        {
            Logger.log(e);
            throw new CouponDBException("Error", "Couldn't create a new company " + c.getCompName());
        }
    }

    @Override
    public void removeCompany(Company c) {

        try(Connection con = DBConnection.getConnection())
        {
            //Check if Record with this ID Exists otherwise throw an exception
            boolean exists = helper.cellExists("Company", "ID", "" + c.getId(), con);
            if(exists)
            {
                //if ok, delete and log
                helper.deleteRecord("Company", c.getId(), con);
                Logger.log("Deleted Company", "Company " + c.getCompName() + " was successfully deleted");
            }
            else
            {
                throw new CouponDBException("Trying to Delete " + c.getCompName(), "Company with that name does not exist in DB");
            }
        }
        catch(SQLException e)
        {
            Logger.log(e);
            throw new CouponDBException("Error", "Couldn't delete the company" + c.getCompName());

        }


    }

    @Override
    public void updateCompany(Company c) {

        try(Connection con = DBConnection.getConnection())
        {
            boolean exists = helper.cellExists("Company", "ID", "" + c.getId(), con);
           if(exists) {
               Map<String, String> map = companyToMap(c);
               helper.updateRecord("Company",map, c.getId(), con);
           }
            else
           {
               throw new CouponDBException("Update: Company doesn't exist", "Company " + c.getCompName() + " doesn't exist in DB.");
           }

        }
        catch(SQLException e)
        {
            Logger.log(e);
            throw new CouponDBException("Error while updating company", "Could not update company " + c.getCompName());
        }


    }

    @Override
    public Company getCompany(long id) {

        //Create a company object to fill it with retrieved info
        Company company = null;
        try(Connection con = DBConnection.getConnection())
        {
            //Check if a record with this ID exists
            boolean exists = helper.cellExists("Company", "ID", ""+id, con);

            if(exists)
            {
                //map to company will convert the map that helper returns to a new company
                company = mapToCompany(helper.getRecord("Company", id, con));
            }
            else
            {
                throw new CouponDBException("Company doesn't exist", "Company with ID " + id + " doesn't exist in DB");
            }

        }
        catch (SQLException e)
        {
            Logger.log(e);
            throw new CouponDBException("Could not retrieve company", "Could not retrieve company with ID " + id);
        }

        return company;
    }

    @Override
    public Collection<Company> getAllCompanies() {
        //helper will return collection of maps instead of companies, so we will have to convert them to companies

        List<Company> companyList = null;
        try {
            Collection<Map<String, String> > colMap = helper.getAllRecords("Company", null);//if called with null, connection will be created automatically
            companyList = new LinkedList<>();

            for(Map<String, String> compMap: colMap)
            {
                //mapToCompany() will convert maps to companies
                companyList.add(mapToCompany(compMap));
            }
        } catch (SQLException e) {
            Logger.log(e);
            throw new CouponDBException("Retrieve all Companies", "Error, couldn't retrieve all the companies");
        }

        return companyList;
    }

    @Override
    public Collection<Coupon> getCoupons() {
        //TODO: Implement when done with Coupons IO

        return null;
    }

    @Override
    public boolean login(String name, String pass) {
        boolean loginSuccess = false;

        try(Connection con = DBConnection.getConnection())
        {
            boolean exists = helper.cellExists("Company", "COMP_NAME", name, con);

            if(exists)
            {
                Company co = mapToCompany(helper.getRecord("Company", "COMP_NAME" , name, con));
                if(name.equals(co.getCompName()) && pass.equals(co.getPassword()))
                {
                    loginSuccess = true;
                    Logger.log("Login", name + " has just logged in.");
                }
                else
                {
                    Logger.log("Login failed", "Password and username do not match");
                }
            }
            else
            {
                throw new CouponDBException("Log in failed", "Username " + name + " could not be found");
            }

        }
        catch(SQLException e)
        {
            Logger.log(e);
            throw new CouponDBException("Error logging in", "Had some problem with DB while trying to login for user: " + name);
        }
        return loginSuccess;
    }

    /*
    Private methods
     */


    private Map<String, String> companyToMap(Company c)
    {
        Map<String, String> map = new HashMap<>();
        map.put("ID", "" + c.getId());
        map.put("COMP_NAME", c.getCompName());
        map.put("PASSWORD", c.getPassword());
        map.put("EMAIL", c.getEmail());

        return map;
    }

    private Company mapToCompany(Map<String, String> map)
    {
        Company newCompany = new Company(map.get("COMP_NAME"), map.get("PASSWORD"), map.get("EMAIL"));
        newCompany.setId(Long.parseLong(map.get("ID")));

        return newCompany;

    }
}
