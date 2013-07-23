package security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * 使用DSA进行数字签名的过程
 * @author weijian.zhongwj
 *
 */
public class SignatureTest {

	public static void main(String[] args) throws Exception {
		
		//签名过程
		byte[] data = "Data signature".getBytes();
		KeyPairGenerator kg = KeyPairGenerator.getInstance("DSA");
		kg.initialize(1024);//初始化dsa加密位数为1204
		KeyPair kp = kg.genKeyPair();//生成公钥私钥密码对
		Signature sign = Signature.getInstance("DSA");
		sign.initSign(kp.getPrivate());//初始化用于签名的signature对象
		sign.update(data);//更新数据
		byte[] signData = sign.sign();//获取经过签名的数据
		
		//公钥用于验证过程
		sign.initVerify(kp.getPublic());
		sign.update(data);
		boolean status = sign.verify(signData);//验证签名的数据是否合法，获得验证结果
		System.out.println(status);
	}

}
