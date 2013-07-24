package security;

import java.util.zip.CRC32;

public class CRC32Test {

	public static void main(String[] args) {
		
		String str ="crc32 test";
		
		CRC32 crc32 =new CRC32();
		crc32.update(str.getBytes());
		String hex = Long.toHexString(crc32.getValue());
		System.out.println(hex);
	}

}
