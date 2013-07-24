package security;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.UrlBase64;

public class BouncyCastleTest {

	public static void main(String[] args) {
		
		//base64±àÂë
		String str = "base 64";
		byte[] data = Base64.encode(str.getBytes());
		System.out.println("encoded:" + new String(data));
		System.out.println("decoded:" + new String(Base64.decode(data)));
		
		//url base64±àÂë
		data = UrlBase64.encode(str.getBytes());
		System.out.println("urlencoded:" + new String(data));
		System.out.println("urldecoded:" + new String(UrlBase64.decode(data)));
		
		//Hex±àÂë
		data = Hex.encode(str.getBytes());
		System.out.println("urlencoded:" + new String(data));
		System.out.println("urldecoded:" + new String(Hex.decode(data)));
	}

}
