/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API_3G;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hung_
 */
public class NetworkAccessSuccess3G {
    
    InputStruct inputData;
    public NetworkAccessSuccess3G(InputStruct inputData) {
        this.inputData = inputData;
    }

    public ArrayList<OutResult> getData() {
        System.out.println("get NetworkAccessSuccess3G data");
        ArrayList<OutResult> resultList = new ArrayList<>();
        try {
            String sql = "SELECT time, longitude, latitude, `value`,kpi FROM `" + inputData.table + "` "
                    + "WHERE (kpi='" + inputData.kpi1 + "' OR kpi='" + inputData.kpi2 + "') AND operator=" + inputData.operator + " AND network_type='" + inputData.network_type + "'";
//            System.out.println(sql);
            Statement stmt = inputData.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                try {
                    OutResult truyNhapModel = new OutResult();
                    String date_time = rs.getString("time");
                    truyNhapModel.date = date_time.split(" ")[0];
                    truyNhapModel.time = date_time.split(" ")[1];
                    truyNhapModel.lat=rs.getDouble("latitude");
                    truyNhapModel.lng=rs.getDouble("longitude");
                    String kpi = rs.getString("kpi");
                    truyNhapModel.kpi=kpi;
                    if (kpi.equals(inputData.kpi1)) {
                        truyNhapModel.networkConnectAttemp = rs.getString("value");
                        truyNhapModel.networkConnect = "";
                    } else if (kpi.equals(inputData.kpi2)) {
                        truyNhapModel.networkConnectAttemp = "";
                        truyNhapModel.networkConnect = rs.getString("value");
                    }
                    resultList.add(truyNhapModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static class InputStruct {
        public Connection conn = null;
        public String table = null;
        public Integer operator=null;
        public String network_type=null;
        public String kpi1=null;
        public String kpi2=null;
    }

    public static class OutResult {
        public String kpi=null;
        public String time = null;
        public String date = null;
        public Double lat = null;
        public Double lng = null;
        public String networkConnectAttemp = null;
        public String networkConnect = null;
    }
}
