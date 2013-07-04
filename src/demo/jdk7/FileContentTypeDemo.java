package demo.jdk7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContentTypeDemo {

    public static void main(String[] args) {
        
        printContentType("E:\\tmp\\jetty.docx");
        printContentType("E:\\tmp\\sitemap.xml");
        printContentType("E:\\tmp\\1.png");
        printContentType("E:\\tmp\\FIGURES.ppt");
 
    }
 
    private static void printContentType(String pathToFile) {
         
        Path path = Paths.get(pathToFile);
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
      
            e.printStackTrace();
        }
        System.out.println("File content type is : " + contentType);
         
    }
}
