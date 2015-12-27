package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBIOHelper {


    public void addRecord(String table, Map<String, String> recordMap) throws SQLException
    {
        Connection con = DBConnection.getConnection();


        String[] formatResult = queryFormatVals(recordMap);


        String sql = "INSERT INTO "+ table +" (" + formatResult[0] + ") VALUES (" + formatResult[1] +")";
        System.out.println(sql);
        Statement st = con.createStatement();
        st.execute(sql);


        con.close();
        st.close();

    }

    public void deleteRecord(String table, long id)
    {

    }

    public void updateRecord(String table, Map<String, String> recordMap)
    {


    }
    public void updateCell(String table, String col, long id, String value)
    {

    }

    public Map<String, String> getRecord(String table, long id)
    {

        Map<String, String> aMap = new HashMap<>();
        return aMap;
    }

    public Collection<Map<String, String> > getAllRecords(String table)
    {

        Collection<Map<String, String>  > mapCollection = new LinkedList<>();
        return mapCollection;
    }


    /*
    Private methods
     */

    private String[] queryFormatVals(Map<String, String> map)
    {
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
