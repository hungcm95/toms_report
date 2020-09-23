/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API_Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author hung_
 */
public class SignalInfoByRange {
    
    InputStruct inputStruct;

    public SignalInfoByRange(InputStruct inputStruct) {
        this.inputStruct = inputStruct;
    }
    
    public Integer getData(){
        try {
            String sql="SELECT count(*) as count FROM `"+inputStruct.table+"` "
                    + "WHERE `value`+0.0 BETWEEN coalesce("+inputStruct.range_from+", `value`+0.0) "
                    + "AND coalesce("+inputStruct.range_to+", `value`+0.0);";
            System.out.println(sql);
            Statement stmt=inputStruct.conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            rs.last();
            return rs.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static class InputStruct {
        public Connection conn = null;
        public String table = null;
        public Integer operator=null;
        public String network_type=null;
        public String kpi = null;
        public Float range_from=null;
        public Float range_to=null;
    }

//    public static class OutResult {
//        
//        public Integer range_count;
//    }
    
}
