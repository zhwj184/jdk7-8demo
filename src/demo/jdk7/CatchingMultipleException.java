package demo.jdk7;

import java.io.IOException;
import java.sql.SQLException;

public class CatchingMultipleException {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			testthrows();
		} catch (IOException | SQLException ex) {
			throw ex;
		}

	}

	public static void testthrows() throws IOException, SQLException {

	}

	static class FirstException extends Exception {
	}

	static class SecondException extends Exception {
	}

	public void rethrowException(String exceptionName) throws Exception {
		try {
			if (exceptionName.equals("First")) {
				throw new FirstException();
			} else {
				throw new SecondException();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void jdk7rethrowException(String exceptionName)
			throws FirstException, SecondException {
		try {
			if (exceptionName.equals("First")) {
				throw new FirstException();
			} else {
				throw new SecondException();
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
