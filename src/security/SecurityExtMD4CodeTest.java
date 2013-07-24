package security;

import java.security.MessageDigest;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 通过在工程里添加bcprov-jdk16-144.jar，然后通过Security.addProvider方法添加java6的扩展算法支持示例
 * @author weijian.zhongwj
 *
 */
public class SecurityExtMD4CodeTest {

	public static void main(String[] args) throws Exception {
//		Security.addProvider(new BouncyCastleProvider());
		MessageDigest md = MessageDigest.getInstance("SHA-224");
		md.update("sha-224".getBytes());
		byte[] out = md.digest();
		System.out.println(out);
	}

}
