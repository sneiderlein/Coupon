package coupon.dao;

import coupon.exception.CouponDBException;
import coupon.model.Company;
import coupon.model.Coupon;

import java.sql.SQLException;
import java.util.Collection;

public interface CompanyDAO {

    void createCompany(Company c) throws SQLException, CouponDBException;
    void removeCompany(Company c);
    void updateCompany(Company c);
    Company getCompany(long id);
    Collection<Company> getAllCompanies();
    Collection<Coupon> getCoupons();
    boolean login(String name, String pass);

}
