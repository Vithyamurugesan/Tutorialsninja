package com.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.testng.annotations.DataProvider;

public class DPExcel {

    @DataProvider(name = "validData", parallel = true)
    public Object[][] validDataProvider() {
        return getExcelData("src/test/resources/DPfortutorialsninja.xlsx", "Sheet1");
    }

    @DataProvider(name = "invalidData", parallel = true)
    public Object[][] invalidDataProvider() {
        return getExcelData("src/test/resources/DPfortutorialsninja.xlsx", "Sheet2");
    }

    public Object[][] getExcelData(String fileName, String sheetName) {

        Object[][] data = null;

        try {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCols = sheet.getRow(0).getLastCellNum();

            data = new Object[totalRows - 1][totalCols];

            for (int i = 1; i < totalRows; i++) { // skip header
                XSSFRow row = sheet.getRow(i);

                for (int j = 0; j < totalCols; j++) {
                    Cell cell = row.getCell(j);

                    if (cell != null) {
                        data[i - 1][j] = cell.toString().trim();
                    } else {
                        data[i - 1][j] = "";
                    }
                }
            }

            workbook.close();
            fis.close();

        } catch (IOException e) {
            System.out.println("Excel Read Error: " + e.getMessage());
        }

        return data;
    }
}