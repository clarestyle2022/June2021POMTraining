package com.qa.democart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class ExcelUtil {

	private static String TEST_DATA_SHEET ="./src/test/resources/TestData/OpenCartTestData.xlsx";
    private static Workbook book;
    private static Sheet sheet;
    
	public static Object[][] getTestData(String sheetName) {  // shettname eg book1 book 2
		
		Object data[][] = null;
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET); //read
			book = WorkbookFactory.create(ip);  //interact with excel sheet and will creat a replica of  the excel sheet in java memeory
			sheet = book.getSheet(sheetName);
			
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for(int i=0; i<sheet.getLastRowNum(); i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}


