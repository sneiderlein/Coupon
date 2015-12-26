package model;

import java.util.Collection;

public class Customer {

    /*
    Attributes
    */
    private long id;
    private String custName;
    private String Password;
    private Collection<Coupon> coupons;
    
    /*
    Constructors
    */

    public Customer(long id, String custName, String password, Collection<Coupon> coupons) {
        this.id = id;
        this.custName = custName;
        Password = password;
        this.coupons = coupons;
    }
    /*
    Methods
    */

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", custName='" + custName + '\'' +
                ", Password='" + Password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
