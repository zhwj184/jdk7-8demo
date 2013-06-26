import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Java7NewFeature {

	public static void main(String[] args) throws IOException {

		// 更佳的整数串
		// 二进制串
		int mask = 0b101010101010;
		// 使用下划线确保清晰易懂
		int mask1 = 0b1010_1010_1010;
		long big = 9_223_783_036_967_937L;
		System.out.println(mask);
		System.out.println(mask1);
		System.out.println(big);

		// 字符串 Switch 语句
		monthNameToDays("May", 11);

		// 简化泛型
		// 使用泛型前
		List strList = new ArrayList();
		List<String> strList1 = new ArrayList<String>();
		List<String> strList2 = new ArrayList<String>();
		List<Map<String, List<String>>> strList3 = new ArrayList<Map<String, List<String>>>();
		// 尖括号运算符
		// 编译器使用尖括号 (<>) 推断类型
		List<String> strList4 = new ArrayList<>();
		List<Map<String, List<String>>> strList5 = new ArrayList<>();

		// 复制文件
		String src = "";
		String dest = "";
		InputStream in = new FileInputStream(src);
		try {
			OutputStream out = new FileOutputStream(dest);
			try {
				byte[] buf = new byte[8192];
				int n;
				while ((n = in.read(buf)) >= 0)
					out.write(buf, 0, n); // 可能抛出异常
			} finally {
				out.close(); // 可能抛出异常
			}
		} finally {
			in.close(); // 可能抛出异常
		}

		// Try-with-resources 语句
		try (InputStream in1 = new FileInputStream(src);
				OutputStream out1 = new FileOutputStream(dest)) {
			byte[] buf = new byte[8192];
			int n;
			while ((n = in1.read(buf)) >= 0)
				out1.write(buf, 0, n);
		}

		// 信息更丰富的回溯追踪
		// §  java.io.IOException
		// §  at Suppress.write(Suppress.java:19)
		// §  at Suppress.main(Suppress.java:8)
		// §  Suppressed: java.io.IOException
		// §  at Suppress.close(Suppress.java:24)
		// §  at Suppress.main(Suppress.java:9)
		// §  Suppressed: java.io.IOException
		// §  at Suppress.close(Suppress.java:24)
		// §  at Suppress.main(Suppress.java:9)

		// 大量异常
//		try {
//			throw new ClassNotFoundException("aaa");
//		} catch (ClassNotFoundException cnfe) {
//			cnfe.printStackTrace();
//		} catch (InstantiationException ie) {
//			ie.printStackTrace();
//		} catch (NoSuchMethodException nsme) {
//			nsme.printStackTrace();
//		} catch (InvocationTargetException ite) {
//			ite.printStackTrace();
//		}
		//多重捕获
//		try { 
//			 ... 
//			} catch (ClassCastException e) { 
//			  doSomethingClever(e); 
//			  throw e; 
//			} catch(InstantiationException | 
//			   NoSuchMethodException | 
//			   InvocationTargetException e) { 
//			 log(e); 
//			 throw e; 
//			} 

	}

	// 字符串 Switch 语句
	// 如今，case 标签包括整数常量和枚举常量
	// 字符串也是常量（不可变）
	public static void monthNameToDays(String s, int year) {
		switch (s) {
		case "April":
		case "June":
		case "September":
		case "November":
			System.out.println(30);
			break;
		case "January":
		case "March":
		case "May":
		case "July":
		case "August":
		case "December":
			System.out.println(31);
			break;
		case "February":
			System.out.println(28);
			break;
		default:
			System.out.println(30);
			break;
		}
	}
}
