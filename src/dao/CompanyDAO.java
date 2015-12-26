package dao;

import model.Company;
import model.Coupon;

import java.util.Collection;

public interface CompanyDAO {

    void createCompany(Company c);
    void removeCompany(Company c);
    void updateCompany(Company c);
    Company getCompany(long id);
    Collection<Company> getAllCompanies();
    Collection<Coupon> getCoupons();
    boolean login(String name, String pass);

}