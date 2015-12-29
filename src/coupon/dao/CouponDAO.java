package coupon.dao;

import coupon.model.Coupon;
import coupon.model.CouponType;

import java.sql.SQLException;
import java.util.Collection;

public interface CouponDAO
{
    void createCoupon(Coupon c)throws SQLException;
    void removeCoupon(Coupon c)throws SQLException;
    void updateCoupon(Coupon c)throws SQLException;
    Coupon getCoupon(long id)throws SQLException;
    Collection<Coupon> getAllCoupons()throws SQLException;
    Collection<Coupon> getCouponsByType(CouponType type)throws SQLException;
    boolean exists(Coupon c)throws SQLException;

}
