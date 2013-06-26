package puzzle;

public class PrivateFieldMatter {
	public static void main(String[] args) {
		System.out.println(new Derived().name);
	}
}

class Base {
	public String name = "aaa";
}

class Derived extends Base {
//	private String name = "bbb";
}
