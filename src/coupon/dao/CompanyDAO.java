package coupon.dao;

import coupon.exception.CouponDBException;
import coupon.model.Company;
import coupon.model.Coupon;

import java.sql.SQLException;
import java.util.Collection;

public interface CompanyDAO {

    void createCompany(Company c) throws SQLException;
    void removeCompany(Company c) throws SQLException;
    void removeAllItsCoupons(Company c) throws SQLException;
    void updateCompany(Company c) throws SQLException;
    Company getCompany(long id) throws SQLException;
    Collection<Company> getAllCompanies() throws SQLException;
    Collection<Coupon> getCoupons(Company c) throws SQLException;
    boolean login(String name, String pass) throws SQLException;
    boolean exists(Company c) throws SQLException;
}
