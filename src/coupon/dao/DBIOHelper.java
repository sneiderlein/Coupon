package coupon.dao;

import com.sun.istack.internal.Nullable;
import com.sun.org.apache.xml.internal.security.encryption.CipherData;
import coupon.Logger;
import coupon.exception.CouponException;

import java.sql.*;
import java.util.*;

public class DBIOHelper {


    public void addRecord(String table, Map<String, String> recordMap, @Nullable Connection con) throws SQLException
    {
        //Check if map is nullpointer
        if(recordMap == null)
            throw new CouponException("Empty record was entered", "Empty (nullpointer) record for writing to db was sent.");

        //Get connection from the pool if was not passed
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }


        //Get formatted string out of map [result[0]= col, col2][result[1] = 'val', 'val2']
        String[] formatResult = queryFormatVals(recordMap);

        //Prepare sql query
        String sql = "INSERT INTO "+ table +" (" + formatResult[0] + ") VALUES (" + formatResult[1] +")";
        Logger.log("Adding record to DB", sql);
        Statement st = con.createStatement();
        st.execute(sql);

        st.close();
        if(needToClose)
            con.close();

    }

    public void deleteRecord(String table, long id, @Nullable Connection con) throws SQLException
    {
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }


        String sql = "DELETE FROM " + table + " WHERE ID = " + id;
        Statement st = con.createStatement();
        st.execute(sql);


        st.close();
        if(needToClose)
            con.close();

    }

    public void updateRecord(String table, Map<String, String> recordMap ,long id,@Nullable Connection con)throws SQLException
    {
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }


        //Prepare SQL query
        String sql = "UPDATE " + table + " SET ";

        int count = 1;
        for(Map.Entry<String, String> entry : recordMap.entrySet())
        {
            sql += entry.getKey() + " = " + surroundWithSingleQuotes(entry.getValue());
            if(count++ < recordMap.size())
            {
                sql += ", ";
            }


        }
        sql += " WHERE ID = " + id + ";";
        Logger.log("Update DB ", sql);

        Statement st = con.createStatement();
        st.execute(sql);
        st.close();


        if(needToClose)
            con.close();

    }
    public void updateCell(String table, String col, long id, String value, @Nullable Connection con)throws SQLException
    {
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        String sql = "UPDATE " + table + " SET " + col + " = '" + value + "' WHERE ID = " + id;
        Logger.log("Update Cell", sql);

        Statement st = con.createStatement();
        st.execute(sql);


        st.close();

        if(needToClose)
            con.close();
    }

    public Map<String, String> getRecord(String table, long id, @Nullable Connection con)throws SQLException
    {
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        String sql = "SELECT * FROM " + table + " WHERE ID = " + id;

        Logger.log("Getting DB Record", sql);

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        //Create a map which will be returned
        Map<String, String> resultMap = new HashMap<>();

        while(rs.next())
        {
            resultMap.put("ID", rs.getString("ID"));
            resultMap.put("COMP_NAME", rs.getString("COMP_NAME"));
            resultMap.put("PASSWORD", rs.getString("PASSWORD"));
            resultMap.put("EMAIL", rs.getString("EMAIL"));

        }


        rs.close();
        st.close();

        if(needToClose)
            con.close();

        return resultMap;
    }

    public Collection<Map<String, String> > getAllRecords(String tablem, @Nullable Connection con)throws SQLException
    {
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }


        Collection<Map<String, String>  > mapCollection = new LinkedList<>();
        return mapCollection;
    }

    public boolean cellExists(String table, String column,String value, @Nullable Connection con) throws SQLException
    {
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        //Returns whether a row already exists or not
        String sql = "SELECT "+ column +" FROM " + table +" WHERE "+ column +" = '" + value + "';";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        return rs.next();
    }
    /*
    Private methods
     */

    private String[] queryFormatVals(Map<String, String> map)
    {
        //Takes a map of query parameters and returns sql friendly string with ',' between it's members
        //string[0] will contain column names and are not surrounded with single quotes
        //string[1] will contain column values and are surrounded with single quotes

        String[] result = new String[2];
        result[0] = result[1] = "";

        int count = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();

            result[0] += key;
            result[1] += surroundWithSingleQuotes(value);

            if(count++ < map.size())
            {
                result[0] += ", ";
                result[1] += ", ";
            }


        }

        return result;

    }

    private String surroundWithSingleQuotes(String initial)
    {
        return "'" + initial + "'";
    }
}
