/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;

import API_Common.SignalInfoByRange;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hung_
 */
public class tmp {
    public static void main(String[] args) {
        try {
            SignalInfoByRange.InputStruct inputStruct=new SignalInfoByRange.InputStruct();
            inputStruct.conn=DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/kpi_mobifone_2019", "root", "123456");
            inputStruct.kpi="20";
            inputStruct.network_type="3G";
            inputStruct.operator=2;
            inputStruct.table="2019_p0127062019ph5_hdg_rscp_a1";
            inputStruct.range_from=-75F;
            SignalInfoByRange signalInfoByRange=new SignalInfoByRange(inputStruct);
            Integer abc=signalInfoByRange.getData();
            System.out.println(abc);
            
        } catch (SQLException ex) {
            Logger.getLogger(tmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
