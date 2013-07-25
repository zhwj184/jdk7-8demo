package security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class DHTest {

	public static final String ALGORITHM = "DH";

	/**
	 * DH加密下需要一种对称加密算法对数据加密，这里我们使用AES，也可以使用其他对称加密算法。
	 */
	public static final String SECRET_ALGORITHM = "AES";
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";

	/**
	 * 初始化甲方密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(512);//DH密钥长度

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 甲方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// 甲方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 初始化乙方密钥
	 * 
	 * @param key
	 *            甲方公钥
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(byte[] key) throws Exception {

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 由甲方公钥构建乙方密钥
		DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();

		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(ALGORITHM);
		keyPairGenerator.initialize(dhParamSpec);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 乙方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// 乙方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

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
	public static byte[] encrypt(byte[] data, 
			byte[] privateKey) throws Exception {

		
		// 生成本地密钥
		SecretKey secretKey = new SecretKeySpec(privateKey, SECRET_ALGORITHM);

		// 数据加密
		Cipher cipher = Cipher.getInstance(SECRET_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,byte[] privateKey) throws Exception {


		// 生成本地密钥
		SecretKey secretKey = new SecretKeySpec(privateKey, SECRET_ALGORITHM);

		// 数据解密
		Cipher cipher = Cipher.getInstance(SECRET_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * 构建本地密钥
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSecretKey(byte[]publicKey, byte[]privateKey) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);   
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);   
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);    
  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);   
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);   
  
        KeyAgreement keyAgree = KeyAgreement.getInstance(ALGORITHM);   
        keyAgree.init(priKey);   
        keyAgree.doPhase(pubKey, true);   
  
        // 生成本地密钥   
        SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM);   
        return secretKey.getEncoded();
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
		 // 生成甲方密钥对儿   
        Map<String, Object> aKeyMap = DHTest.initKey();   
        byte[]  aPublicKey = DHTest.getPublicKey(aKeyMap);   
        byte[]  aPrivateKey = DHTest.getPrivateKey(aKeyMap);   
  
        // 由甲方公钥产生本地密钥对儿   
        Map<String, Object> bKeyMap = DHTest.initKey(aPublicKey);   
        byte[]  bPublicKey = DHTest.getPublicKey(bKeyMap);   
        byte[]  bPrivateKey = DHTest.getPrivateKey(bKeyMap);   
           
        String aInput = "abc ";     
  
        byte[] aCode = DHTest.getSecretKey(bPublicKey, aPrivateKey);   
  
        byte[] bCode = DHTest.getSecretKey(aPublicKey, bPrivateKey);   
  
        System.out.println("甲方本地密钥:\r" + Base64.encode(aCode));   
        System.out.println("乙方本地密钥:\r" + Base64.encode(bCode));   
           
        // 由甲方公钥，乙方私钥解密   
        byte[] aout = DHTest.encrypt(aInput.getBytes(), aCode);   
        System.out.println(Base64.encode(aout));
  
        byte[] bDecode = DHTest.decrypt(aout, bCode);     
        System.out.println("解密: " + Base64.encode(bDecode));   
  
  
        // 由乙方公钥，甲方私钥解密   
        String bInput = "abc ";  
        byte[] bout = DHTest.encrypt(bInput.getBytes(), bCode);   
        System.out.println(Base64.encode(bout));
  
        byte[] aDecode = DHTest.decrypt(bout, aCode);     
        System.out.println("解密: " + Base64.encode(aDecode));   
	}
}
