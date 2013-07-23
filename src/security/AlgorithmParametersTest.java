package security;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

public class AlgorithmParametersTest {

	public static void main(String[] args) throws Exception {
		//ÊµÀý»¯DESËã·¨
		AlgorithmParameters ap = AlgorithmParameters.getInstance("DES");
		ap.init(new BigInteger("123277867834232343").toByteArray());
		byte[] b = ap.getEncoded();
		System.out.println(new BigInteger(b).toString());
	}
}
