package io.github.kazuki_aruga.findata_import;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Hello world!
 *
 */
public class Main {

	public static void main(String[] args)
			throws SQLException, EncryptedDocumentException, InvalidFormatException, IOException {

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/text-analyzer?autoReconnect=true&useSSL=false", "text-analyzer",
				"text-analyzer");
				Workbook book = WorkbookFactory.create(ClassLoader.getSystemResourceAsStream("findata.xlsx"))) {

			for (Row row : book.getSheet("財務諸表")) {

			}
		}
	}

}
