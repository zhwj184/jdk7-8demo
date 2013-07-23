package security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * Hmac算法摘要处理示例
 * @author weijian.zhongwj
 *
 */
public class HmacTest {

	public static void main(String[] args) throws Exception {
		
		macTest("HmacMD5");
		macTest("HmacSHA1");
		macTest("HmacSHA256");
		macTest("HmacSHA384");
		macTest("HmacSHA512");
	}

	private static void macTest(String agorithm) throws NoSuchAlgorithmException,
			InvalidKeyException {
		byte[] input = "MAC Test".getBytes();
		KeyGenerator kg = KeyGenerator.getInstance(agorithm);
		SecretKey sk = kg.generateKey();
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		byte[] out = mac.doFinal();//获得经过消息验证后的信息
		
		System.out.println(out.length);
		for(byte b : out){
			System.out.print(b);
		}
		System.out.println();
		System.out.println(mac.getAlgorithm());
		System.out.println(mac.getProvider().getName());
	}

}
