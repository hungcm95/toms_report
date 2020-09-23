/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;

import API_Common.GetNetworkSpeedDetail;
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
public class Sheet6TocDoDL {
    XSSFWorkbook templateFile;
    Connection conn;
    String table;
    Integer sheet_idx;

    public Sheet6TocDoDL(XSSFWorkbook templateFile, Connection conn, Integer sheet_idx) {
        this.templateFile = templateFile;
        this.conn = conn;
        this.table = "2020_p0514092020ph2_hgg_data_qcvn";
        this.sheet_idx = sheet_idx;
    }
    public void run(){
        GetNetworkSpeedDetail.InputStruct inputStruct=new GetNetworkSpeedDetail.InputStruct();
        inputStruct.conn=this.conn;
        inputStruct.table=this.table;
        inputStruct.operator=2;
        inputStruct.kpi="11";
        inputStruct.network_type="3G";
        GetNetworkSpeedDetail networkSpeedDetail=new GetNetworkSpeedDetail(inputStruct);
        ArrayList<GetNetworkSpeedDetail.OutResult> resultList= networkSpeedDetail.getData();
        System.out.println(""+resultList.size());
        
        Integer rowId = 3;
        
        XSSFSheet sheet = this.templateFile.getSheetAt(sheet_idx);
//        System.out.println("size " + resultAccessList.size());
        XSSFCellStyle border_style = this.templateFile.createCellStyle();
        border_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        border_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        
        for(GetNetworkSpeedDetail.OutResult outResult:resultList){
            try {
                Row row = sheet.getRow(rowId);
                if(rowId>100){
                    row=sheet.getRow(rowId);
                }
                row.createCell(0).setCellValue(outResult.time);
                row.createCell(1).setCellValue(outResult.date);
                row.createCell(2).setCellValue(outResult.lat);
                row.createCell(3).setCellValue(outResult.lng);
                row.createCell(4).setCellValue(outResult.speed);
                
                row.getCell(0).setCellStyle(border_style);
                row.getCell(1).setCellStyle(border_style);
                row.getCell(2).setCellStyle(border_style);
                row.getCell(3).setCellStyle(border_style);
                row.getCell(4).setCellStyle(border_style);
                
                rowId++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
