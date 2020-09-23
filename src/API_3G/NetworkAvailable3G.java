/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /* API Độ sẵn sàng của mạng vô tuyến */
package API_3G;

//import API_Common.InputStruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author hung_
 */
public class NetworkAvailable3G {

    InputStruct inputData;

    public NetworkAvailable3G(InputStruct inputData) {
        this.inputData = inputData;
    }

    public OutResult getData() {
        System.out.println("get NetworkAvailable3G data");
        OutResult doSanSangModel = new OutResult();
        try {

            String sql = "SELECT count(*) as total, AVG(`value`+0.0) as avg, MIN(`value`+0.0) as min, MAX(`value`+0.0) as max,\n"
                    + "COUNT(IF(`value`+0.0 < -100 , 1, NULL)) as bad_count, COUNT(IF(`value`+0.0 >= -100 , 1, NULL)) as good_count\n"
                    + "FROM `" + inputData.table + "` WHERE kpi='"+inputData.kpi+"' AND operator=" + inputData.operator;
            //if co network_mode
            if (inputData.network_mode != null) {
                sql = "SELECT count(*) as total, AVG(`value`+0.0) as avg, MIN(`value`+0.0) as min, MAX(`value`+0.0) as max,\n"
                        + "COUNT(IF(`value`+0.0 < -100 , 1, NULL)) as bad_count, COUNT(IF(`value`+0.0 >= -100 , 1, NULL)) as good_count\n"
                        + "FROM `" + inputData.table + "` WHERE kpi='"+inputData.kpi+"' AND operator=" + inputData.operator + " AND network_mode='" + inputData.network_mode + "'";
            }
            Statement stmt = inputData.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.last();
            doSanSangModel.count = rs.getInt("total");
            doSanSangModel.mean = rs.getFloat("avg");
            doSanSangModel.min = rs.getFloat("min");
            doSanSangModel.max = rs.getFloat("max");
            doSanSangModel.goodValueCount = rs.getInt("good_count");
            doSanSangModel.badValueCount = rs.getInt("bad_count");

            return doSanSangModel;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class InputStruct {
        public Connection conn = null;
        public String table = null;
        public String kpi = null;
        public Integer operator = null;
        public String network_mode = null;
    }

    public static class OutResult {
        public Float mean;
        public Float min;
        public Float max;
        public Integer count;
        public Integer badValueCount; //<-100dBm
        public Integer goodValueCount;
    }
}
