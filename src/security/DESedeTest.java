package security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class DESedeTest {

	public static void main(String[] args) throws Exception {
		
		KeyGenerator kg = KeyGenerator.getInstance("DESede");
		kg.init(112);//java6支持168,112
		SecretKey sk = kg.generateKey();
		byte[] key = sk.getEncoded();
		
		System.out.println("secretKey:" + new String(Base64.encode(sk.getEncoded())));
		
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//参数格式：算法/工作模式/填充模式，不同的算法有不同的工作模式
		//加密
		cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
		byte[] input =cipher.doFinal("DES data".getBytes()); //得到加密后的数据
		
		//解密
		cipher.init(Cipher.DECRYPT_MODE, toKey(key));
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//输出解密后的数据
	}
	
	private static Key toKey(byte[] key) throws Exception{
		DESedeKeySpec desKey = new DESedeKeySpec(key);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
		return skf.generateSecret(desKey);
	}

}
