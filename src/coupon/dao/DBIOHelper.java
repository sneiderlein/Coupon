package coupon.dao;

import com.sun.istack.internal.Nullable;
import com.sun.org.apache.xml.internal.security.encryption.CipherData;
import coupon.Logger;
import coupon.exception.CouponException;
import coupon.model.CouponType;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class DBIOHelper {


    public void addRecord(String table, SortedMap<String, Object> recordMap, @Nullable Connection con) throws SQLException
    {

        /*
        Takes a sorted map and destination table as an argument writes it to the SQL DB.
        The map contains the column name as the key and the value as the value
        Connection can be passed as an argument also, if null is passed, connection will
        be created and closed when done.
         */

        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        //Prepare the statement before executing it
        String sql = "INSERT INTO " + table  + " ( ";
        Iterator<String> keyIter = recordMap.keySet().iterator();
        Iterator<Object> objIter = recordMap.values().iterator();

        //add column names to the statement
        while(keyIter.hasNext())
        {
            sql += keyIter.next();
            if(keyIter.hasNext())
                sql += ", ";
        }

        sql += " ) VALUES (";

        //use ? signs to indicate the values for the prepared statement

        while(objIter.hasNext())
        {
            objIter.next();
            sql += "?";

            if(objIter.hasNext())
                sql += ", ";

        }
        sql += ")";

        //sql should look like "INSERT INTO Company (col1, col2, col3) VALUES (?, ?, ?);

        PreparedStatement pst = con.prepareStatement(sql);
        int count = 1;
        for(Map.Entry<String, Object> entry : recordMap.entrySet())
        {
            //Use correct PreparedStatement setter according to the object type
            if(entry.getValue() instanceof String)
            {
                pst.setString(count++, (String)entry.getValue());
            }
            else if(entry.getValue() instanceof java.sql.Date)
            {

                pst.setDate(count++, (java.sql.Date)entry.getValue());
            }
            else if(entry.getValue() instanceof CouponType)
            {
                pst.setString(count++, ((CouponType)entry.getValue()).name());
            }
            else
            {
                pst.setObject(count++, entry.getValue());
            }
        }

        //Execute the query
        pst.execute();


        Logger.log("Record Added", pst.toString());

        //If the connection was created here we should close it
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


        //Prepare the statement
        String sql = "DELETE FROM " + table + " WHERE ID = " + id;
        Statement st = con.createStatement();
        //Execute the sql query
        st.execute(sql);
        st.close();

        Logger.log("DB record deleted", sql);

        //IF the connection was created here, we should close it
        if(needToClose)
            con.close();

    }

    public void updateRecord(String table, SortedMap<String, Object> recordMap, long id, @Nullable Connection con) throws SQLException
    {

         /*
        Takes a sorted map and destination table as an argument writes it to the SQL DB.
        The map contains the column name as the key and the value as the value
        Connection can be passed as an argument also, if null is passed, connection will
        be created and closed when done.
         */
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        //prepare the statement
        String sql = "UPDATE " + table  + " SET ";

        //We will need an indication to where we are in the loop
        int count = 1;
        for(Map.Entry<String, Object> entry : recordMap.entrySet())
        {
            //Populate the query with '?'s for Prepared statement
            sql += entry.getKey() + " =  ?" ;

            //Don't add ',' in the end of parameter list
            if(count++ < recordMap.size())
            {
                sql += ", ";
            }

        }
        sql += " WHERE ID = " + id + ";";

        PreparedStatement pst = con.prepareStatement(sql);

        //For parameter index in Prepared statement
        int count2 = 1;
        for(Map.Entry<String, Object> entry : recordMap.entrySet())
        {
            //Populate PreparedStatement with the right types
            if(entry.getValue() instanceof String)
            {
                pst.setString(count2++, (String)entry.getValue());
            }
            else if(entry.getValue() instanceof java.sql.Date)
            {

                pst.setDate(count2++, (java.sql.Date)entry.getValue());
            }
            else if(entry.getValue() instanceof CouponType)
            {
                pst.setString(count2++, ((CouponType)entry.getValue()).name());
            }
            else
            {
                pst.setObject(count2++, entry.getValue());
            }
        }

        pst.execute();
        pst.close();


        if(needToClose)
            con.close();


    }

    public Map<String, Object> getRecord(String table, long id, @Nullable Connection con)throws SQLException
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

        ResultSetMetaData meta = rs.getMetaData();
        Map<String, Object> recordMap = new HashMap<>();
        while(rs.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                //Add every column to the Map
                recordMap.put(meta.getColumnName(i), rs.getObject(i));

            }
        }
        if(needToClose)
            con.close();

        return recordMap;

    }

    public Map<String, Object> getRecordByValue(String table, String col, Object value, @Nullable Connection con)throws SQLException
    {
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }
        Map<String, Object> recordMap = null;
        long id = findCellID(table, col, value, con);
        if(id >= 0) {
            recordMap = getRecord(table, id, con);
        }


        if(needToClose)
            con.close();
        return recordMap;
    }


    public Collection<Map<String, Object> > getAllRecords(String table, @Nullable Connection con)throws SQLException
    {
        //Connection can be created outside, and sent here, so that the sender can chain many
        //calls to the DB using only one connection.
        //If connection was created here we have to close it if not the caller will have to.
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        //Return a collection of maps which represent records in DB
        Collection<Map<String, Object>  > mapCollection = new LinkedList<>();

        String sql = "SELECT * FROM " + table;
        Logger.log("Getting All DB Record", sql);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        //We don't now the number of Columns nor they're names so we will have to use MetaData
        ResultSetMetaData meta = rs.getMetaData();

        //One of the records
        Map<String, Object> recordMap = null;

        while(rs.next())
        {
            //Create a new one for every record
            recordMap = new HashMap<>();
            for(int i = 1; i <= meta.getColumnCount(); i++)
            {
                //Add every column to the Map
                recordMap.put(meta.getColumnName(i), rs.getObject(i));

            }

            //Add created map to the collection
            mapCollection.add(recordMap);
        }


        if(needToClose)
            con.close();

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

    public long findCellID(String table, String col, Object val, @Nullable Connection con) throws SQLException
    {
        boolean needToClose = false;
        if(con == null) {
            con = DBConnection.getConnection();
            needToClose = true;
        }

        String sql = "SELECT ID FROM " + table + " WHERE " + col + " = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setObject(1, val);
        ResultSet rs = pst.executeQuery();

        long result = 0;
        if(rs.next())
            result = rs.getInt(1);
        else
            result = -1;

        if(needToClose)
            con.close();

        return result;
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
