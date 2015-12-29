package coupon.model;

import java.util.Collection;

public class Customer {

    /*
    Attributes
    */
    private long id;
    private String custName;
    private String password;
    private Collection<Coupon> coupons;
    private String email;
    
    /*
    Constructors
    */

    public Customer(String custName, String password, String email) {
        this.custName = custName;
        this.password = password;
        this.email = email;
    }
    /*
    Getters and Setters
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Collection<Coupon> coupons) {
        this.coupons = coupons;
    }

    public String getEmail() {
        return email;
    }

    /*
    Methods
    */

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", custName='" + custName + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
