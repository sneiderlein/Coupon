package coupon.dao;

import com.sun.istack.internal.Nullable;
import coupon.Logger;
import coupon.model.Coupon;
import coupon.model.CouponType;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;


public class CouponDBDAO implements CouponDAO {
    @Override
    public void createCoupon(Coupon c) throws SQLException
    {
        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare a statement
        String sql = "INSERT INTO Coupon SET " +
                "TITLE = ?, START_DATE = ?, END_DATE = ?," +
                "AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE_PATH = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getTitle());
        pst.setDate(2, Date.valueOf(c.getStartDate()));
        pst.setDate(3, Date.valueOf(c.getEndDate()));
        pst.setInt(4, c.getAmount());
        pst.setString(5, c.getType().name());
        pst.setString(6, c.getMessage());
        pst.setDouble(7, c.getPrice());
        pst.setString(8, c.getImagePath());

        //Execute the statement
        pst.execute();

        //Log it
        Logger.log("Coupon added", "Coupon " + c.getTitle() + " was added to the db");

        //Clean up
        pst.close();
        con.close();

    }

    @Override
    public void removeCoupon(Coupon c) throws SQLException
    {

        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare the statement
        String sql = "DELETE FROM Coupon WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setLong(1, c.getId());

        //Execute it
        pst.execute();

        //Log the thing
        Logger.log("Coupon was deleted", "Coupon " + c.getTitle() + " was removed from the DB");

        //Clean up
        pst.close();
        con.close();
    }

    @Override
    public void updateCoupon(Coupon c) throws SQLException
    {

        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare the statement
        String sql = "UPDATE Coupon SET " +
                "TITLE = ?, START_DATE = ?, END_DATE = ?," +
                "AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE_PATH = ? " +
                "WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getTitle());
        pst.setDate(2, Date.valueOf(c.getStartDate()));
        pst.setDate(3, Date.valueOf(c.getEndDate()));
        pst.setInt(4, c.getAmount());
        pst.setString(5, c.getType().name());
        pst.setString(6, c.getMessage());
        pst.setDouble(7, c.getPrice());
        pst.setString(8, c.getImagePath());
        pst.setLong(9, c.getId());

        //Execute it
        pst.execute();

        //Log it
        Logger.log("Coupon " + c.getTitle() + " was updated", pst.toString());

        //Clean up
        pst.close();
        con.close();

    }

    @Override
    public Coupon getCoupon(long id) throws SQLException {
        //Create coupon to return
        Coupon coupon = null;

        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare the statement
        String sql = "SELECT * FROM Coupon WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setLong(1, id);

        //Execute and catch the ResultSet
        ResultSet rs = pst.executeQuery();

        //Read the results
        if(rs.next())
        {
            coupon = new Coupon
                    (
                    rs.getString("TITLE"), rs.getDate("START_DATE").toLocalDate(),
                    rs.getDate("END_DATE").toLocalDate(), CouponType.valueOf(rs.getString("TYPE")),
                    rs.getInt("AMOUNT"), rs.getDouble("PRICE"), rs.getString("IMAGE_PATH"),
                            rs.getString("MESSAGE")
                    );

        }

        //Log it
        Logger.log("Coupon was read from db", pst.toString());

        //Clean up
        pst.close();
        con.close();

        return coupon;
    }

    @Override
    public Collection<Coupon> getAllCoupons() throws SQLException
    {
        //Using this wrapper method no filtering will be done and all coupons will be
        //returned.
        return getAllCoupons(null);
    }


    public Collection<Coupon> getAllCoupons(@Nullable CouponType type) throws SQLException {
        //If nulls sent as arguments no filtering will be done
        boolean filtered = false;
        String filterAddition = "";
        if(type != null)
        {
            filtered = true;
            filterAddition = " WHERE TYPE = ?";
        }

        //Create collection to return
        Collection<Coupon> coupons = new LinkedList<>();

        //Get connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare the statement
        String sql = "SELECT * FROM Coupon" + filterAddition;
        PreparedStatement pst = con.prepareStatement(sql);

        //If filtering(using WHERE clause) query will contain ?'s which will be addressed
        if(filtered)
            pst.setString(1, type.name());

        //Catch the result set
        ResultSet rs = pst.executeQuery();

        int counter = 0;
        //Read the ResultSet
        while(rs.next())
        {

            //Read to the object
            Coupon coupon = new Coupon
                    (
                            rs.getString("TITLE"), rs.getDate("START_DATE").toLocalDate(),
                            rs.getDate("END_DATE").toLocalDate(), CouponType.valueOf(rs.getString("TYPE")),
                            rs.getInt("AMOUNT"), rs.getDouble("PRICE"), rs.getString("IMAGE_PATH"),
                            rs.getString("MESSAGE")
                    );
            //Add to the list
            coupons.add(coupon);

            counter++;
        }

        //Log
        Logger.log(counter + " coupons were loaded", pst.toString());

        //Clean up
        pst.close();
        con.close();

        return coupons;
    }

    @Override
    public Collection<Coupon> getCouponsByType(CouponType type) throws SQLException
    {
        return getAllCoupons(type);
    }

    @Override
    public boolean exists(Coupon c) throws SQLException
    {
        boolean exists = false;

        //Get a connection from the pool
        Connection con = DBConnection.getConnection();

        //Prepare the statement
        String sql = "SELECT EXISTS(SELECT ID FROM Coupon WHERE ID = ? )";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setLong(1, c.getId());

        //Execute
        ResultSet rs = pst.executeQuery();
        rs.next();

        exists = rs.getBoolean(1);

        //Log
        Logger.log(c.getTitle() + ((exists)? " exists in the db":" doesn't exist in the DB"), pst.toString());

        //Clean up
        pst.close();
        con.close();

        return exists;
    }

}
