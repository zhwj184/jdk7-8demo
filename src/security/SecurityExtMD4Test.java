package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通过在java.security添加provider配置和ext添加jar包支持支持
 * @author weijian.zhongwj
 *
 */
public class SecurityExtMD4Test {

	public static void main(String[] args) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("MD4");
		md.update("md4".getBytes());
		byte[] out = md.digest();
		System.out.println(out);
	}

}
