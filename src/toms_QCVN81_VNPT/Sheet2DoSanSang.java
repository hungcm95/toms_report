/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;

import API_3G.NetworkAvailable3G;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hung_
 */
public class Sheet2DoSanSang {

    XSSFWorkbook templateFile;
    Connection conn;
    String tablePrefix;
    Integer sheet_idx;
    String imgUrl = "img1.png";

    public Sheet2DoSanSang(XSSFWorkbook templateFile, Connection conn, Integer sheet_idx, String tablePrefix) {
        this.templateFile = templateFile;
        this.conn = conn;
//        this.tablePrefix = "2020_p0514092020ph2_hgg_clvp_qcvn";
        this.tablePrefix = tablePrefix;
        this.sheet_idx = sheet_idx;
    }

    public void run() {
        try {
            //lay ket qua tu database
            NetworkAvailable3G.InputStruct inputStruct = new NetworkAvailable3G.InputStruct();

            inputStruct.conn = this.conn;
            inputStruct.table = this.tablePrefix+"_clvp_qcvn";
            inputStruct.operator = 2;
            inputStruct.kpi = "20.1";

            NetworkAvailable3G networkAvailable3G = new NetworkAvailable3G(inputStruct);

            NetworkAvailable3G.OutResult result = networkAvailable3G.getData();

            XSSFSheet sheet = this.templateFile.getSheetAt(sheet_idx);

            Row row4 = sheet.getRow(3);
            Cell cell_mean = row4.getCell(0);
            cell_mean.setCellValue(result.mean);
            Cell cell_min = row4.getCell(1);
            cell_min.setCellValue(result.min);
            Cell cell_max = row4.getCell(2);
            cell_max.setCellValue(result.max);
            Cell cell_count_total = row4.getCell(3);
            cell_count_total.setCellValue(result.count);

            Row row8 = sheet.getRow(7);
            Cell cell_B8 = row8.getCell(1);
            cell_B8.setCellValue(result.badValueCount);
            Cell cell_C8 = row8.getCell(2);
            cell_C8.setCellValue(result.goodValueCount);
            Cell cell_D8 = row8.getCell(3);
            cell_D8.setCellValue(result.count);

            Row row9 = sheet.getRow(8);
            Cell cell_B9 = row9.getCell(1);
            Float PDF_bad = (float) Math.round((float) result.badValueCount * 10000 / result.count) / 100;
            cell_B9.setCellValue(String.format("%.02f", PDF_bad));
            Cell cell_C9 = row9.getCell(2);
            Float PDF_good = (float) Math.round((float) result.goodValueCount * 10000 / result.count) / 100;
            cell_C9.setCellValue(String.format("%.02f", PDF_good));
            row9.getCell(3).setCellValue(100);

            Row row3 = sheet.getRow(2);
            row3.getCell(13).setCellValue(result.goodValueCount);
//            Row row4 = sheet.getRow(3);
            row4.getCell(13).setCellValue(result.count);

            Row row5 = sheet.getRow(4);
            String strFormula = "N3/N4";
//            row5.getCell(13).setCellType(HSSFCell.CELL_TYPE_FORMULA);
            row5.getCell(13).setCellFormula(strFormula);

            cell_B9.setCellValue(String.format("%.02f", PDF_bad));

            //dong 51 cho do thi
            sheet.getRow(50).getCell(2).setCellValue(PDF_bad);
            sheet.getRow(50).getCell(3).setCellValue(PDF_good);
            //dong 52
            sheet.getRow(51).getCell(2).setCellValue(PDF_bad);

            //add picture
            InputStream my_banner_image = new FileInputStream(imgUrl);
            /* Convert picture to be added into a byte array */
            byte[] bytes = IOUtils.toByteArray(my_banner_image);
            /* Add Picture to Workbook, Specify picture type as PNG and Get an Index */
            int my_picture_id = templateFile.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            /* Close the InputStream. We are ready to attach the image to workbook now */
            my_banner_image.close();
            /* Create the drawing container */
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            /* Create an anchor point */
            XSSFClientAnchor my_anchor = new XSSFClientAnchor();
            /* Define top left corner, and we can resize picture suitable from there */
            my_anchor.setCol1(1);
            my_anchor.setRow1(12);
            XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
            /* Call resize method, which resizes the image */
            my_picture.resize();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
