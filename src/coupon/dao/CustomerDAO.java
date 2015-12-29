package coupon.dao;

import coupon.model.Coupon;
import coupon.model.Customer;

import java.sql.SQLException;
import java.util.Collection;

public interface CustomerDAO
{

    void createCustomer(Customer c)throws SQLException;
    void removeCustomer(Customer c)throws SQLException;
    void updateCustomer(Customer c)throws SQLException;
    Customer getCustomer(long id)throws SQLException;
    Collection<Customer> getAllCustomers()throws SQLException;
    Collection<Coupon> getAllCoupons(Customer c)throws SQLException;
    boolean login(String name, String pass)throws SQLException;
    boolean exists(Customer c)throws SQLException;
}
