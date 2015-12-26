package dao;

import model.Company;
import model.Coupon;

import java.util.Collection;

public class CompanyDBDAO implements CompanyDAO {


    /*
    Attributes
    */
    
    
    
    /*
    Constructors
    */
    
    
    /*
    Methods
    */

    @Override
    public void createCompany(Company c) {

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
}
