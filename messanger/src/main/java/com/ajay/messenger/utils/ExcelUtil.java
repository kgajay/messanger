package com.ajay.messenger.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ajay.messenger.domain.Book;


@SuppressWarnings({"resource"})
public class ExcelUtil {

	private String excelFilePath;

	public ExcelUtil(String excelFilePath) {
		this.setExcelFilePath(excelFilePath);
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}
	
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
	private Workbook getWorkbook(FileInputStream inputStream)
	        throws IOException {
	    Workbook workbook = null;
	 
	    if (this.excelFilePath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook(inputStream);
	    } else if (this.excelFilePath.endsWith("xls")) {
	        workbook = new HSSFWorkbook(inputStream);
	    } else {
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	 
	    return workbook;
	}
	
	public void readDataFromExcel() {
		FileInputStream inputStream;
		Workbook workbook;
		try {
			inputStream = new FileInputStream(new File(this.excelFilePath));
			workbook = this.getWorkbook(inputStream);
			Sheet firstSheet = (Sheet) workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
	        
	        while (iterator.hasNext()) {
	        	Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            Book aBook = new Book();
	            
	            while (cellIterator.hasNext()) {
	                Cell nextCell = cellIterator.next();
	                int columnIndex = nextCell.getColumnIndex();
	                
	                switch (columnIndex) {
		                case 0:
		                    aBook.setTitle((String) getCellValue(nextCell));
		                    break;
		                case 1:
		                    aBook.setAuthor((String) getCellValue(nextCell));
		                    break;
		                case 2:
		                    aBook.setPrice((double) getCellValue(nextCell));
		                    break;
	                }
	            }
	            System.out.println(aBook.toString());
	            workbook.close();
		        inputStream.close();
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	private void writeBook(Book aBook, Row row) {
	    Cell cell = row.createCell(0);
	    cell.setCellValue(aBook.getTitle());
	 
	    cell = row.createCell(1);
	    cell.setCellValue(aBook.getAuthor());
	 
	    cell = row.createCell(2);
	    cell.setCellValue(aBook.getPrice());
	}
	
	public void writeToExcel(List<Book> listBook) throws IOException {
		 
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        
        int rowCount = 0;
        
        for (Book aBook : listBook) {
            Row row = sheet.createRow(rowCount);
            writeBook(aBook, row);
            rowCount += 1;
        }
     
        try (FileOutputStream outputStream = new FileOutputStream(this.excelFilePath)) {
            workbook.write(outputStream);
        }
	}
	
}
