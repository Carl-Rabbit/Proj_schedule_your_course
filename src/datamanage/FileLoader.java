package datamanage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileLoader {
	public static void main(String[] args){
		// inner test
		FileLoader fl = new FileLoader();
		if (fl.open()) {
			fl.loadFile();
		}
	}

	public String filename = "test.xlsx";
	public XSSFWorkbook file;

	public FileLoader() { }

	public FileLoader(String filename) {
		this.filename = filename;
	}

	public boolean open(){
		try {
			InputStream is = new FileInputStream(filename);
			file = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int loadFile() {
		// Get each sheet
		for (int numSheet = 0; numSheet < file.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = file.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Get each row
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {

					for (Cell c : xssfRow) {
						System.out.print(getValue((XSSFCell) c) + "  ");
					}
					System.out.println();

				}
			}
		}

		return 0;
	}

	// covert the form of data
	protected static String getValue(XSSFCell xssfCell) {

		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}
}
