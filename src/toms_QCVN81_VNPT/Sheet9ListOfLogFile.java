/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toms_QCVN81_VNPT;

import API_Common.ListLogFile;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hung_
 */
public class Sheet9ListOfLogFile {

    XSSFWorkbook templateFile;
    Connection conn;
    Integer sheet_idx;
    String tablePrefix;

    public Sheet9ListOfLogFile(XSSFWorkbook templateFile, Connection conn, Integer sheet_idx, String tablePrefix) {
        this.templateFile = templateFile;
        this.conn = conn;
        this.sheet_idx = sheet_idx;
        this.tablePrefix = tablePrefix;
    }

    public void run() {
        ArrayList<ListLogFile.InputStruct> inputList = new ArrayList<>();
        //lay do san sang mang vo tuyen log
        ListLogFile.InputStruct NetReadyInput = new ListLogFile.InputStruct();
        NetReadyInput.kpi_id = "kpi_201";
        NetReadyInput.conn = this.conn;
        NetReadyInput.table = this.tablePrefix + "_clvp_qcvn";
        NetReadyInput.kpi = "20.1";
        NetReadyInput.network_type = "3G";
        NetReadyInput.operator = 2;
        inputList.add(NetReadyInput);

        //ty le truy nhap thanh cong dich vu
        ListLogFile.InputStruct kpi_351 = new ListLogFile.InputStruct();
        kpi_351.kpi_id = "kpi_351";
        kpi_351.conn = this.conn;
        kpi_351.table = this.tablePrefix + "_data_qcvn";
        kpi_351.kpi = "35.1";
        kpi_351.network_type = "3G";
        kpi_351.operator = 2;
        inputList.add(kpi_351);

        //thoi gian tre truy nhap trung binh
        ListLogFile.InputStruct kpi_352 = new ListLogFile.InputStruct();
        kpi_352.kpi_id = "kpi_352";
        kpi_352.conn = this.conn;
        kpi_352.table = this.tablePrefix + "_data_qcvn";
        kpi_352.kpi = "35.2";
        kpi_352.network_type = "3G";
        kpi_352.operator = 2;
        inputList.add(kpi_352);

        //ty le truyen tai du lieu bi roi
        ListLogFile.InputStruct kpi_361_drop = new ListLogFile.InputStruct();
        kpi_361_drop.kpi_id = "kpi_361_drop";
        kpi_361_drop.conn = this.conn;
        kpi_361_drop.table = this.tablePrefix + "_data_qcvn";
        kpi_361_drop.kpi = "36.1";
        kpi_361_drop.network_type = "3G";
        kpi_361_drop.operator = 2;
        inputList.add(kpi_361_drop);

        ListLogFile.InputStruct kpi_363_drop = new ListLogFile.InputStruct();
        kpi_363_drop.kpi_id = "kpi_363_drop";
        kpi_363_drop.conn = this.conn;
        kpi_363_drop.table = this.tablePrefix + "_data_qcvn";
        kpi_363_drop.kpi = "36.3";
        kpi_363_drop.network_type = "3G";
        kpi_363_drop.operator = 2;
        inputList.add(kpi_363_drop);

        // pd>=pDavg
        ListLogFile.InputStruct kpi_361_pd = new ListLogFile.InputStruct();
        kpi_361_pd.kpi_id = "kpi_361_pd";
        kpi_361_pd.conn = this.conn;
        kpi_361_pd.table = this.tablePrefix + "_data_qcvn";
        kpi_361_pd.kpi = "36.1";
        kpi_361_pd.network_type = "3G";
        kpi_361_pd.operator = 2;
        kpi_361_pd.addtional_condition = " value>=1024";
        inputList.add(kpi_361_pd);

        //pu>=pUavg
        ListLogFile.InputStruct kpi_363_pu = new ListLogFile.InputStruct();
        kpi_363_pu.kpi_id = "kpi_363_pu";
        kpi_363_pu.conn = this.conn;
        kpi_363_pu.table = this.tablePrefix + "_data_qcvn";
        kpi_363_pu.kpi = "36.3";
        kpi_363_pu.network_type = "3G";
        kpi_363_pu.operator = 2;
//        kpi_363_pu.addtional_condition = " value>=1024";
        inputList.add(kpi_363_pu);

        //get data
        ListLogFile listLogFile = new ListLogFile(inputList);
        HashMap<String, ArrayList<String>> outTable = listLogFile.getData();

        //insert sheet
        Sheet sheet = templateFile.getSheetAt(sheet_idx);
        Integer rowId = 2;
        for (String logFileName : outTable.keySet()) {
            try {
                ArrayList<String> kpiIdList = outTable.get(logFileName);
                Row row = sheet.getRow(rowId);
                if (rowId > 100) {
                    row = sheet.createRow(rowId);
                }
                row.createCell(1).setCellValue(logFileName);

                for (String kpiId : kpiIdList) {
                    switch (kpiId) {
                        case "kpi_201": //do san sang
                            row.createCell(3).setCellValue("x");
                            break;
                        case "kpi_351": //ty le truy nhap thanh cong
                            row.createCell(4).setCellValue("x");
                            break;
                        case "kpi_352": // thoi gian tre truy nhap
                            row.createCell(5).setCellValue("x");
                            break;
                        case "kpi_361_drop": //ty le truyen tai du lieu bi roi (dl)
                            row.createCell(6).setCellValue("x");
                            break;
                        case "kpi_363_drop": //ty le truyen tai du lieu bi roi (ul)
                            row.createCell(6).setCellValue("x");
                            break;
                        case "kpi_361_pd": //pd and %pd
                            row.createCell(7).setCellValue("x");
                            row.createCell(9).setCellValue("x");
                            break;
                        case "kpi_363_pu": //pu
                            row.createCell(8).setCellValue("x");
                            break;
                        
                        default:
                            System.out.println("no match");
                    }
                }
                
                rowId++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
