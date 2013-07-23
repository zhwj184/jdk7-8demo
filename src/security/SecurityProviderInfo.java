package security;

import java.security.Provider;
import java.security.Security;
import java.util.Map;

/**
 * 打印当前系统的所有安全提供者
 * @author weijian.zhongwj
 *
 */
public class SecurityProviderInfo {

	public static void main(String[] args) {
		
		for(Provider p: Security.getProviders()){
			System.out.println(p);
			
			for(Map.Entry<Object,Object> entry: p.entrySet()){
				
				System.out.println("\t" + entry.getKey());
			}
		}
	}

}
