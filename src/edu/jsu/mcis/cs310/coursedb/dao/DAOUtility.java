package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        JsonArray records = new JsonArray();
        
        try {
            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    JsonObject obj = new JsonObject();

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        
                        if (value instanceof Integer) {
                            obj.put(columnName, (Integer) value);
                        } else if (value instanceof Double) {
                            obj.put(columnName, (Double) value);
                        } else if (value instanceof Boolean) {
                            obj.put(columnName, (Boolean) value);
                        } else {
                            obj.put(columnName, String.valueOf(value)); 
                        }
                    }

                    records.add(obj);
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return Jsoner.serialize(records);
    }
}