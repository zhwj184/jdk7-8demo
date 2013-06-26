package demo.jdk7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Any object that implements java.lang.AutoCloseable, which includes all objects which implement java.io.Closeable, can be used as a resource.
public class TryWithResources {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void writeToFileZipFileContents(String zipFileName,
			String outputFileName) throws java.io.IOException {

		java.nio.charset.Charset charset = java.nio.charset.Charset
				.forName("US-ASCII");
		java.nio.file.Path outputFilePath = java.nio.file.Paths
				.get(outputFileName);

		// Open zip file and create output file with try-with-resources
		// statement

		try (java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName);
				java.io.BufferedWriter writer = java.nio.file.Files
						.newBufferedWriter(outputFilePath, charset)) {

			// Enumerate each entry

			for (java.util.Enumeration entries = zf.entries(); entries
					.hasMoreElements();) {

				// Get the entry name and write it to the output file

				String newLine = System.getProperty("line.separator");
				String zipEntryName = ((java.util.zip.ZipEntry) entries
						.nextElement()).getName() + newLine;
				writer.write(zipEntryName, 0, zipEntryName.length());
			}
		}
	}

	public static void viewTable(Connection con) throws SQLException {

		String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";

		try (Statement stmt = con.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String coffeeName = rs.getString("COF_NAME");
				int supplierID = rs.getInt("SUP_ID");
				float price = rs.getFloat("PRICE");
				int sales = rs.getInt("SALES");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + ", " + supplierID + ", "
						+ price + ", " + sales + ", " + total);
			}

		} catch (SQLException e) {
			
		}
	}

}
