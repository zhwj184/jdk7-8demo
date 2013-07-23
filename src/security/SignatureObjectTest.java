package security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignedObject;

public class SignatureObjectTest {
	public static void main(String[] args) throws Exception {
		// 签名过程
		byte[] data = "Data signature".getBytes();
		KeyPairGenerator kg = KeyPairGenerator.getInstance("DSA");
		kg.initialize(1024);// 初始化dsa加密位数为1204
		KeyPair kp = kg.genKeyPair();// 生成公钥私钥密码对
		Signature sign = Signature.getInstance("DSA");
		SignedObject so = new SignedObject(data, kp.getPrivate(), sign);//data是任何继承seriable的对象即可。
		byte[] outputData = so.getSignature();//获得前面值
		
		//验证过程
		boolean status = so.verify(kp.getPublic(), sign);
		System.out.println(status);
	}
}
