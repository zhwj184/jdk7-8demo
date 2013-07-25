package security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class RSATest {

	public static final String ALGORITHM = "RSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 初始化甲方密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(512);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 甲方公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 甲方私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 加密<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey)
			throws Exception {

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		PrivateKey priKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(
				pkcs8KeySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(priKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, priKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] publicKey)
			throws Exception {

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(publicKey);
		PrivateKey pubKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(
				pkcs8KeySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(pubKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKeyKey(byte[] data, byte[] publicKey)
			throws Exception {

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey priKey = KeyFactory.getInstance(ALGORITHM).generatePublic(
				x509KeySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(priKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, priKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey)
			throws Exception {

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey pubKey = KeyFactory.getInstance(ALGORITHM).generatePublic(x509KeySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(pubKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);

		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return key.getEncoded();
	}

	public static void main(String[] args) throws Exception {

		Map<String, Object> aKeyMap = RSATest.initKey();
		byte[] publicKey = RSATest.getPublicKey(aKeyMap);
		byte[] privateKey = RSATest.getPrivateKey(aKeyMap);
		System.out.println("公钥:\r" + Base64.encode(publicKey));
		System.out.println("私钥:\r" + Base64.encode(privateKey));

		String aInput = "rsa data ";
		byte[] encode = RSATest.encryptByPrivateKey(aInput.getBytes(), privateKey);
		System.out.println("加密后：" + Base64.encode(encode));
		
		byte[] decode = RSATest.decryptByPublicKey(encode, publicKey);
		System.out.println("解密后：" + new String(decode));
		
		encode = RSATest.encryptByPublicKeyKey(aInput.getBytes(), publicKey);
		System.out.println("加密后：" + Base64.encode(encode));
		
		decode = RSATest.decryptByPrivateKey(encode, privateKey);
		System.out.println("解密后：" + new String(decode));

	}
}
