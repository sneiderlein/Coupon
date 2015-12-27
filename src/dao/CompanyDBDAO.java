package dao;

import exception.CouponException;
import model.Company;
import model.Coupon;

import java.sql.*;
import java.util.Collection;

public class CompanyDBDAO implements CompanyDAO {


    /*
    Attributes
    */

    Connection con; //Will be null, and it's ok
    Statement st;

    /*
    Constructors
    */

    public CompanyDBDAO()
    {

    }
    
    /*
    Methods
    */

    @Override
    public void createCompany(Company c)throws SQLException, CouponException {

        //get a connection
        Connection con = DBConnection.getConnection();

        //Check if a company like that already exists in the database
        if(exists(con, c)) throw new CouponException("Company '" + c.getCompName() +"' already exists");

        //if not thrown out, add the company
        String query = "INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL)" +
                       "VALUES (?, ? ,?)";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, c.getCompName());
        pst.setString(2, c.getPassword());
        pst.setString(3, c.getEmail());

        pst.executeUpdate();

        System.out.println("Added: " + c.getCompName());
        con.close();
        pst.close();
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
