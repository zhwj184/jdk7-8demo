package demo.jdk8;

class Utils {
	public static int compareByLength(String in, String out) {
		return in.length() - out.length();
	}
}

public class MyClass {
	public void doSomething() { 
        String[] args = new String[] {"microsoft","apple","linux","oracle"};
//        Arrays.sort(args, Utils::compareByLength); 
    }}