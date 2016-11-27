package io.github.kazuki_aruga.findata_import;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 財務諸表と研究開発費データを更新する。
 *
 */
public class Main {

	private static final Log log = LogFactory.getLog(Main.class);

	public static void main(String[] args)
			throws SQLException, EncryptedDocumentException, InvalidFormatException, IOException {

		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/text-analyzer?autoReconnect=true&useSSL=false", "text-analyzer",
				"text-analyzer");
				Workbook book = WorkbookFactory.create(ClassLoader.getSystemResourceAsStream("findata.xlsx"))) {

			final Sheet finSheet = book.getSheet("財務諸表");
			for (int i = finSheet.getFirstRowNum() + 1, last = finSheet.getLastRowNum() + 1; i < last; i++) {

				final Row row = finSheet.getRow(i);

				final FinData fin = new FinData();
				fin.setCompCode(Integer.toString(getIntValue(row.getCell(0))));
				fin.setYear(getYear(row.getCell(2)));
				fin.setSales(getIntValue(row.getCell(3)));
				fin.setAssets(getIntValue(row.getCell(4)));
				fin.setDebt(getIntValue(row.getCell(5)));
				fin.setEbitda(getIntValue(row.getCell(6)));

				if (!updateReportFin(conn, fin)) {

					log.warn(
							"レコードが存在しないため、財務諸表データを更新できません： comp_code=" + fin.getCompCode() + ", year=" + fin.getYear());
				}
			}

			final Sheet rdSheet = book.getSheet("研究開発費");
			for (int i = rdSheet.getFirstRowNum() + 1, last = rdSheet.getLastRowNum() + 1; i < last; i++) {

				final Row row = rdSheet.getRow(i);

				final RdData rd = new RdData();
				rd.setCompCode(Integer.toString(getIntValue(row.getCell(0))));
				rd.setYear(getYear(row.getCell(2)));
				rd.setRd(getIntValue(row.getCell(3)));
				rd.setNi(getIntValue(row.getCell(4)));

				if (!updateReportRd(conn, rd)) {

					log.warn("レコードが存在しないため、研究開発費データを更新できません： comp_code=" + rd.getCompCode() + ", year=" + rd.getYear());
				}
			}
		}
	}

	private static int getIntValue(Cell cell) {

		if (cell == null) {

			return 0;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {

			return 0;
		}

		return (int) cell.getNumericCellValue();
	}

	private static int getYear(Cell cell) {

		return Integer.parseInt(cell.getStringCellValue().substring(0, 4));
	}

	private static boolean updateReportFin(Connection conn, FinData fin) throws SQLException {

		try (PreparedStatement ps = conn.prepareStatement(
				"update report set sales = ?, asset = ?, debt = ?, ebitda = ? where comp_code = ? and year = ?")) {

			ps.setInt(1, fin.getSales());
			ps.setInt(2, fin.getAssets());
			ps.setInt(3, fin.getDebt());
			ps.setInt(4, fin.getEbitda());
			ps.setString(5, fin.getCompCode());
			ps.setInt(6, fin.getYear());

			return ps.executeUpdate() == 1;
		}
	}

	private static boolean updateReportRd(Connection conn, RdData rd) throws SQLException {

		try (PreparedStatement ps = conn
				.prepareStatement("update report set rd = ?, ni = ? where comp_code = ? and year = ?")) {

			ps.setInt(1, rd.getRd());
			ps.setInt(2, rd.getNi());
			ps.setString(3, rd.getCompCode());
			ps.setInt(4, rd.getYear());

			return ps.executeUpdate() == 1;
		}
	}

}
