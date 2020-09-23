/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;


import API_Common.DataSessionDrop;
import java.sql.Connection;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hung_
 */
public class Sheet5TyLeTruyenTaiDuLieu {
    XSSFWorkbook templateFile;
    Connection conn;
    String table;
    Integer sheet_idx;

    public Sheet5TyLeTruyenTaiDuLieu(XSSFWorkbook templateFile, Connection conn, Integer sheet_idx) {
        this.templateFile = templateFile;
        this.conn = conn;
        this.table = "2020_p0514092020ph2_hgg_data_qcvn";
        this.sheet_idx = sheet_idx;
    }
    public void run(){
        DataSessionDrop.InputStruct inputStruct=new DataSessionDrop.InputStruct();
        inputStruct.conn = this.conn;
        inputStruct.table = this.table;
        inputStruct.operator = 2;
        inputStruct.network_type = "3G";
        inputStruct.kpi1 = "36.1";
        inputStruct.kpi2 = "36.2";
        
        DataSessionDrop dataSessionDrop = new DataSessionDrop(inputStruct);
        ArrayList<DataSessionDrop.OutResult> resultList= dataSessionDrop.getData();
        Integer rowId=7;
        XSSFSheet sheet = this.templateFile.getSheetAt(sheet_idx);
        XSSFCellStyle border_style = this.templateFile.createCellStyle();
        border_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        for(DataSessionDrop.OutResult outResult:resultList){
            try {
                Row row = sheet.getRow(rowId);
                if(rowId>100){
                    row=sheet.createRow(rowId);
                }
                row.createCell(0).setCellValue(outResult.time);
                row.createCell(1).setCellValue(outResult.date);
                row.createCell(2).setCellValue(outResult.lat);
                row.createCell(3).setCellValue(outResult.lng);
                row.createCell(4);
                row.createCell(5);
                if(outResult.avg_through_put!=null){
                    row.getCell(4).setCellValue(outResult.avg_through_put);
                }
                
                if(outResult.avg_through_put_not_complete !=null){
                    row.getCell(5).setCellValue(outResult.avg_through_put_not_complete);
                }
                
                row.getCell(0).setCellStyle(border_style);
                row.getCell(1).setCellStyle(border_style);
                row.getCell(2).setCellStyle(border_style);
                row.getCell(3).setCellStyle(border_style);
                
                row.getCell(5).setCellStyle(border_style);
                
                rowId++;
            } catch (Exception e) {
//                System.out.println("");
//                System.out.println(""+rowId);
                e.printStackTrace();
            }
        }
    }
}
