package security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PBETest {

	public static void main(String[] args) throws Exception {
		
		//初始化salt
		SecureRandom sr = new SecureRandom();
		byte[] salt = sr.generateSeed(8);
		
		PBEParameterSpec kps = new PBEParameterSpec(salt, 100);
		Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");//参数格式：算法/工作模式/填充模式，不同的算法有不同的工作模式
		//加密
		cipher.init(Cipher.ENCRYPT_MODE, toKey("jklsfjkdsf"), kps);
		byte[] input =cipher.doFinal("PBEWITHMD5andDES data".getBytes()); //得到加密后的数据
		
		//解密
		cipher.init(Cipher.DECRYPT_MODE, toKey("jklsfjkdsf"), kps);
		byte[] output = cipher.doFinal(input);
		System.out.println(new String(output));//输出解密后的数据
	}
	
	private static Key toKey(String password) throws Exception{
		PBEKeySpec sk = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
		SecretKey key = skf.generateSecret(sk);
		return key;
	}

}
