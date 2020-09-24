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
            InputStream inp = new FileInputStream("QCVN81_Template.xlsx");
            XSSFWorkbook fWorkbook = new XSSFWorkbook(inp);
            
            String tablePrefix="2020_p0514092020ph2_hgg";
            
            Sheet2DoSanSang doSanSang = new Sheet2DoSanSang(fWorkbook, conn, 1,tablePrefix);
            doSanSang.run();
            
            Sheet3TyLeTruyNhap tyLeTruyNhap = new Sheet3TyLeTruyNhap(fWorkbook, conn, 2,tablePrefix);
            tyLeTruyNhap.run();
            
            Sheet4TreTruyNhap treTruyNhap = new Sheet4TreTruyNhap(fWorkbook, conn, 3,tablePrefix);
            treTruyNhap.run();
            
            Sheet5TyLeTruyenTaiDuLieu truyenTaiDuLieu = new Sheet5TyLeTruyenTaiDuLieu(fWorkbook, conn, 4,tablePrefix);
            truyenTaiDuLieu.run();

            Sheet6TocDoDL tocDoDl = new Sheet6TocDoDL(fWorkbook, conn, 5,tablePrefix);
            tocDoDl.run();

            Sheet7TocDoUL tocDoUL = new Sheet7TocDoUL(fWorkbook, conn, 6,tablePrefix);
            tocDoUL.run();
            
            Sheet8TocDoDL2 tocDoDL2 = new Sheet8TocDoDL2(fWorkbook, conn, 7,tablePrefix);
            tocDoDL2.run();
            
            Sheet9ListOfLogFile listOfLogFile=new Sheet9ListOfLogFile(fWorkbook, conn, 8, tablePrefix);
            listOfLogFile.run();

            FileOutputStream os = new FileOutputStream("QCVN81_.xlsx");
            fWorkbook.setForceFormulaRecalculation(true);

            fWorkbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
            fWorkbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
