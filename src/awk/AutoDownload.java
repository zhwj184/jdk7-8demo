package awk;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutoDownload {
	public static void main(String args[]) throws IOException {
		System.out.println("Enter the url to download from:");
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		String site = input.readLine();
		System.out.println("Enter filename");
		BufferedReader input1 = new BufferedReader(new InputStreamReader(
				System.in));
		String filename = input.readLine();
		java.io.BufferedInputStream in = new java.io.BufferedInputStream(
				new java.net.URL(site).openStream());
		java.io.FileOutputStream fos = new java.io.FileOutputStream(filename);
		java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		byte[] data = new byte[1024];
		int i = 0;
		while ((i = in.read(data, 0, 1024)) >= 0) {
			bout.write(data, 0, i);
		}
		bout.close();
		in.close();
	}
}
