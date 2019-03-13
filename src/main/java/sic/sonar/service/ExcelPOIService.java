package sic.sonar.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import sic.sonar.common.SICServiceException;
import sic.sonar.model.TargetInfoVO;

public class ExcelPOIService {
	final int targetColumnIndex = 7;
	final int writeColumnIndex = 9;
//	final int targetlastRowIndex = 7;
	
	public int modifyXLSX(String filePath, String outputFilePath, List<TargetInfoVO> targetInfoList) throws SICServiceException, EncryptedDocumentException, InvalidFormatException, IOException {
		int modifyCount = 0;
		// Obtain a workbook from the excel file
	    Workbook workbook;
			workbook = WorkbookFactory.create(new File(filePath));

	    // Get Sheet at index 0
	    Sheet sheet = workbook.getSheetAt(0);

	    // Get Row at index 1
	    int curRow = 1;
	    for(TargetInfoVO targetInfoVO : targetInfoList) {
	    	Row row = sheet.getRow(curRow++);
	    	
		    // Get the Cell at index 2 from the above row
	    	writeCell(row,writeColumnIndex+0, targetInfoVO.getComponentKey());
	    	writeCell(row,writeColumnIndex+1, targetInfoVO.getStingCoverage());
	    	writeCell(row,writeColumnIndex+2, targetInfoVO.getStingLineCoverage());
	    	writeCell(row,writeColumnIndex+3, targetInfoVO.getStingBranchCoverage());
	    	writeCell(row,writeColumnIndex+4, targetInfoVO.getStingTests());
	    	writeCell(row,writeColumnIndex+5, targetInfoVO.getStingTestFails());
	    	writeCell(row,writeColumnIndex+6, targetInfoVO.getStingTestErrors());
	    	writeCell(row,writeColumnIndex+7, targetInfoVO.getLastAnalysisDate());
	    	modifyCount++;
	    }
	    
	    // Write the output to the file
	    FileOutputStream fileOut = new FileOutputStream(outputFilePath);
	    workbook.write(fileOut);
	    fileOut.close();

	    // Closing the workbook
	    workbook.close();
		
		return modifyCount;
	}
	
	private int writeCell(Row row, int columnIndex, String value) {
		Cell cell = row.getCell(columnIndex);

	    // Create the cell if it doesn't exist
	    if (cell == null) {
	        cell = row.createCell(columnIndex);
	    }
	    cell.setCellValue(value);
	    return 1;
	}
	
	public List<String> readXLSX(String filePath) throws SICServiceException {
		// 반환할 객체를 생성
		List<String> componentKeyList = new ArrayList<String>();

		FileInputStream fis = null;
		XSSFWorkbook workbook = null;

		try {
			fis = new FileInputStream(filePath);
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new XSSFWorkbook(fis);

			// 탐색에 사용할 Sheet, Row, Cell 객체
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;

			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 Sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex != 0) {
						// 현재 row 반환
						curRow = curSheet.getRow(rowIndex);

						String value;

						// row의 첫번째 cell값이 비어있지 않은 경우 만 cell탐색
						if (!"".equals(curRow.getCell(0).getStringCellValue())) {
							curCell = curRow.getCell(targetColumnIndex);
							componentKeyList.add(String.valueOf(curCell));
						}
					}
				}
			}
		} catch (FileNotFoundException e1) {
			throw new SICServiceException(e1);
		} catch (IOException e2) {
			throw new SICServiceException(e2);
		} finally {
			try {
				// 사용한 자원은 finally에서 해제
				if (workbook != null)
					workbook.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return componentKeyList;
	}
	
	public List<String> readXLS(String filePath) throws SICServiceException {
		// 반환할 객체를 생성
		List<String> componentKeyList = new ArrayList<String>();

		FileInputStream fis = null;
		HSSFWorkbook workbook = null;

		try {
			fis = new FileInputStream(filePath);
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new HSSFWorkbook(fis);

			// 탐색에 사용할 Sheet, Row, Cell 객체
			HSSFSheet curSheet;
			HSSFRow curRow;
			HSSFCell curCell;

			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 Sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex != 0) {
						// 현재 row 반환
						curRow = curSheet.getRow(rowIndex);

						String value;

						// row의 첫번째 cell값이 비어있지 않은 경우 만 cell탐색
						if (!"".equals(curRow.getCell(0).getStringCellValue())) {
							curCell = curRow.getCell(targetColumnIndex);
							componentKeyList.add(String.valueOf(curCell));
						}
					}
				}
			}
		} catch (FileNotFoundException e1) {
			throw new SICServiceException(e1);
		} catch (IOException e2) {
			throw new SICServiceException(e2);
		} finally {
			try {
				// 사용한 자원은 finally에서 해제
				if (workbook != null)
					workbook.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return componentKeyList;
	}
	
	@Deprecated
	public int writeXLSX(String filePath) throws SICServiceException {
		
		
		
		
		return -1;	
	}
}
