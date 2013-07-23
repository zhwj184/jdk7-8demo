package security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class CipherTest {

	public static void main(String[] args) throws Exception, Exception {
//		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//参数格式：算法/工作模式/填充模式，不同的算法有不同的工作模式
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		SecretKey sk = kg.generateKey();
		
		Cipher cipher = Cipher.getInstance("DES");
		
		//加密
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		byte[] input =cipher.doFinal("DES data".getBytes()); //得到加密后的数据
		
		//解密
		cipher.init(Cipher.DECRYPT_MODE, sk);
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//输出解密后的数据
		
		//使用SealedObject来加密可序列化的java对象和解密
		Cipher cipher1 = Cipher.getInstance("DES");
		cipher1.init(Cipher.ENCRYPT_MODE, sk);
		SealedObject so = new SealedObject("DES data", cipher1);
		Cipher cipher2 = Cipher.getInstance(sk.getAlgorithm());
		cipher2.init(Cipher.DECRYPT_MODE, sk);
		String sooutput = (String) so.getObject(cipher2);
		System.out.println(sooutput);
		
	}
}
