package demo.jdk7;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchFilesInDirectory {
	public static void main(String[] args) {

		// 1. Create a path to the directory
		// where search for the files is to be done.
		Path dirPath = Paths.get("E:\\tmp");

		DirectoryStream<Path> dirStream = null;

		try {
			// 2. Create a DirectoryStream instance using
			// Files.newDirectoryStream(). The instance
			// is created by passing the Path of the directory
			// to be searched and the type of files as a filter
			// here we are using filter to search all pdf files
			// in the directory
			dirStream = Files.newDirectoryStream(dirPath, "*.docx");

			// 3. Looping each file and printing the name
			// on the console
			for (Path entry : dirStream) {

				System.out.println(entry.getFileName());

			}
		} catch (IOException e) {

			System.out.println(e.getMessage());

		} finally {
			// 4. Closing the DirectoryStream
			try {

				dirStream.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}
