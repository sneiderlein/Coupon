package coupon.dao;

import coupon.Logger;
import coupon.exception.CouponDBException;
import coupon.model.Company;
import coupon.model.Coupon;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> companyMap = new HashMap<>();

        companyMap.put("COMP_NAME", c.getCompName());
        companyMap.put("PASSWORD", c.getPassword());
        companyMap.put("EMAIL", c.getEmail());

        try (Connection con = DBConnection.getConnection())
        {

            boolean exists = helper.cellExists("Company", "COMP_NAME",c.getCompName(), con );
            if(exists) {
                throw new CouponDBException("Record already exists", c.getCompName() + " already exists in DB.");
            }
            helper.addRecord("Company",companyMap, con);
            Logger.log(c.getCompName(), "Added to DB");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCompany(Company c) {

    }

    @Override
    public void updateCompany(Company c) {

    }

    @Override
    public Company getCompany(long id) {
        return null;
    }

    @Override
    public Collection<Company> getAllCompanies() {
        return null;
    }

    @Override
    public Collection<Coupon> getCoupons() {
        return null;
    }

    @Override
    public boolean login(String name, String pass) {
        return false;
    }

    /*
    Private methods
     */

    private boolean exists(Connection con, Company company) throws SQLException
    {
        //Returns whether a row already exists or not
        String sql = "SELECT COMP_NAME FROM Company WHERE COMP_NAME = '" + company.getCompName() + "';";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return rs.next();

    }
}
