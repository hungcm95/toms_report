/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hung_
 */
public class BaoCaoToms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/kpi_mobifone_2019", "root", "123456");
            String table = "2020_p0514092020ph2_hgg_clvp_qcvn";
            InputStream inp = new FileInputStream("QCVN81_3G_Q2_2020_HGG_Template_Linh.xlsx");
            XSSFWorkbook fWorkbook = new XSSFWorkbook(inp);
            Sheet2DoSanSang doSanSang = new Sheet2DoSanSang(fWorkbook, conn, 1);
            doSanSang.run();
            table = "2020_p0514092020ph2_hgg_data_qcvn";
            Sheet3TyLeTruyNhap tyLeTruyNhap = new Sheet3TyLeTruyNhap(fWorkbook, conn, 2);
            tyLeTruyNhap.run();
            table = "2020_p0514092020ph2_hgg_data_qcvn";
            Sheet4TreTruyNhap treTruyNhap = new Sheet4TreTruyNhap(fWorkbook, conn, 3);
            treTruyNhap.run();
            
            table="2020_p0514092020ph2_hgg_data_qcvn";
            Sheet5TyLeTruyenTaiDuLieu truyenTaiDuLieu = new Sheet5TyLeTruyenTaiDuLieu(fWorkbook, conn, 4);
            truyenTaiDuLieu.run();

            table = "2020_p0514092020ph2_hgg_data_qcvn";
            Sheet6TocDoDL tocDoDl = new Sheet6TocDoDL(fWorkbook, conn, 5);
            tocDoDl.run();

            table = "2020_p0514092020ph2_hgg_data_qcvn";
            Sheet7TocDoUL tocDoUL = new Sheet7TocDoUL(fWorkbook, conn, 6);
            tocDoUL.run();
            table = "2020_p0514092020ph2_hgg_data_qcvn";
            Sheet8TocDoDL2 tocDoDL2 = new Sheet8TocDoDL2(fWorkbook, conn, 7);
            tocDoDL2.run();

            FileOutputStream os = new FileOutputStream("QCVN81_.xlsx");
            fWorkbook.setForceFormulaRecalculation(true);

            fWorkbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
            fWorkbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
