/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;

import API_3G.NetworkAccessSuccess3G;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hung_
 */
public class Sheet3TyLeTruyNhap {

    XSSFWorkbook templateFile;
    Connection conn;
    String tablePrefix;
    Integer sheet_idx;

    public Sheet3TyLeTruyNhap(XSSFWorkbook templateFile, Connection conn, Integer sheet_idx, String tablePrefix) {
        this.templateFile = templateFile;
        this.conn = conn;
//        this.table = "2020_p0514092020ph2_hgg_data_qcvn";
        this.tablePrefix=tablePrefix;
        this.sheet_idx=sheet_idx;
    }

    public void run() {
        NetworkAccessSuccess3G.InputStruct inputStruct = new NetworkAccessSuccess3G.InputStruct();
        inputStruct.conn = this.conn;
        inputStruct.table = this.tablePrefix+"_data_qcvn";
        inputStruct.operator = 2;
        inputStruct.network_type = "3G";
        inputStruct.kpi1 = "35.1";
        inputStruct.kpi2 = "35.2";

        NetworkAccessSuccess3G accessSuccess3G = new NetworkAccessSuccess3G(inputStruct);

        ArrayList<NetworkAccessSuccess3G.OutResult> resultAccessList = accessSuccess3G.getData();

        Integer rowId = 3;
        XSSFSheet sheet = this.templateFile.getSheetAt(sheet_idx);
//        System.out.println("size " + resultAccessList.size());
        Integer cout_kpi1 = 0;
        Integer cout_kpi2 = 0;
        XSSFCellStyle border_style = this.templateFile.createCellStyle();
        border_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        for (NetworkAccessSuccess3G.OutResult access : resultAccessList) {
            try {
                if (access.kpi.equals(inputStruct.kpi1)) {
                    cout_kpi1++;
                }
                if (access.kpi.equals(inputStruct.kpi2)) {
                    cout_kpi2++;
                }
                Row row = sheet.getRow(rowId);
                if(rowId>100){
                    row=sheet.createRow(rowId);
                }
                row.createCell(0).setCellValue(access.time);
                row.createCell(1).setCellValue(access.date);
                row.createCell(2).setCellValue(access.lat);
                row.createCell(3).setCellValue(access.lng);
                row.createCell(4).setCellValue(access.networkConnectAttemp);
                row.createCell(5).setCellValue(access.networkConnect);
                
                row.getCell(0).setCellStyle(border_style);
                row.getCell(1).setCellStyle(border_style);
                row.getCell(2).setCellStyle(border_style);
                row.getCell(3).setCellStyle(border_style);
                row.getCell(4).setCellStyle(border_style);
                row.getCell(5).setCellStyle(border_style);
                
                rowId++;
            } catch (Exception e) {
                System.out.println(""+rowId);
                e.printStackTrace();
            }
        }
        try {
            Row row3 = sheet.getRow(2);
//        System.out.println("row3 "+row3+" "+row3.getCell(0).getCellStyle().toString());
            row3.getCell(9).setCellValue(cout_kpi1);
            Row row4 = sheet.getRow(3);
            row4.getCell(9).setCellValue(cout_kpi2 + "");
            if (cout_kpi1 == 0) {
//                sheet.getRow(4).getCell(10).setCellValue("NA");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
