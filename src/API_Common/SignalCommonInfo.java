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
public class SignalCommonInfo {
    
    InputStruct inputStruct;

    public SignalCommonInfo(InputStruct inputStruct) {
        this.inputStruct = inputStruct;
    }
    
    public OutResult getData(){
        try {
            OutResult outResult=new OutResult();
            String sql="SELECT count(*) as total, AVG(`value`+0.0) as avg, MIN(`value`+0.0) as min, MAX(`value`+0.0) as max,\n"
                    + "FROM `" + inputStruct.table + "` WHERE kpi='"+inputStruct.kpi+"' "
                    + "AND network_type='"+inputStruct.network_type+"' AND operator=" + inputStruct.operator;
            Statement stmt=inputStruct.conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            rs.last();
            outResult.count=rs.getInt("total");
            outResult.mean=rs.getFloat("avg");
            outResult.min=rs.getFloat("min");
            outResult.max=rs.getFloat("max");
            
            return outResult;
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
    }

    public static class OutResult {
        public Float mean;
        public Float min;
        public Float max;
        public Integer count;
    }
}
