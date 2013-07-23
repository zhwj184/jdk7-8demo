package security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		digest("MD2");
		digest("MD5");
		digest("SHA-1");
		digest("SHA-256");
		digest("SHA-384");
		digest("SHA-512");
	}

	private static void digest(String agorithm) throws NoSuchAlgorithmException {
		System.out.println(agorithm);
		MessageDigest md = MessageDigest.getInstance(agorithm);//
		md.update(new String("test md5").getBytes());
		byte[] out = md.digest();
		System.out.println(out.length);
		for(byte b : out){
			System.out.print(b);
		}
		System.out.println();
		System.out.println(md.getAlgorithm());
		System.out.println(md.getProvider().getName());
	}
	
	private static void digestStream(String agorithm) throws NoSuchAlgorithmException, Exception {
		System.out.println(agorithm);
		MessageDigest md = MessageDigest.getInstance(agorithm);//
		byte[] bytes = new String(agorithm).getBytes();
		DigestInputStream input = new DigestInputStream(new ByteArrayInputStream(bytes), md);
		input.read(bytes, 0,  bytes.length);
		byte[] outbytes = input.getMessageDigest().digest();
		input.close();
	}
}
