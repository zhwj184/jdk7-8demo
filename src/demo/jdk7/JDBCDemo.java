package demo.jdk7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.sun.rowset.JdbcRowSetImpl;

public class JDBCDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		}
	}

	public void testJdbcRowSet(String username, String password)
			throws SQLException {

		RowSetFactory myRowSetFactory = null;
		JdbcRowSet jdbcRs = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {

			myRowSetFactory = RowSetProvider.newFactory();
			jdbcRs = myRowSetFactory.createJdbcRowSet();

			jdbcRs.setUrl("jdbc:myDriver:myAttribute");
			jdbcRs.setUsername(username);
			jdbcRs.setPassword(password);

			jdbcRs.setCommand("select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES");
			jdbcRs.execute();

			// ...
		} catch (Exception e) {

		}
	}

	public static void jdbc() throws SQLException {
		JdbcRowSetImpl jrs = new JdbcRowSetImpl();
		jrs.setCommand("SELECT * FROM TITLES WHERE TYPE = ?");
		jrs.setUrl("jdbc:myDriver:myAttribute");
		jrs.setUsername("cervantes");
		jrs.setPassword("sancho");
		jrs.setString(1, "BIOGRAPHY");
		jrs.execute();
	}

}
