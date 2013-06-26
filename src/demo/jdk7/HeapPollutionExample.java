package demo.jdk7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ArrayBuilder {

	public static <T> void addToList(List<T> listArg, T... elements) {
		for (T x : elements) {
			listArg.add(x);
		}
	}

	@SuppressWarnings({ "unchecked", "varargs" })
	public static <T> void addToList2(List<T> listArg, T... elements) {
		for (T x : elements) {
			listArg.add(x);
		}
	}

	@SafeVarargs
	public static <T> void addToList3(List<T> listArg, T... elements) {
		for (T x : elements) {
			listArg.add(x);
		}
	}

	public static void faultyMethod(List<String>... l) {
		Object[] objectArray = l; // Valid
		objectArray[0] = Arrays.asList(new Integer(42));
		String s = l[0].get(0); // ClassCastException thrown here
	}

}

public class HeapPollutionExample {

	// ...

	public static void main(String[] args) {

		List<String> stringListA = new ArrayList<String>();
		List<String> stringListB = new ArrayList<String>();

		ArrayBuilder.addToList(stringListA, "Seven", "Eight", "Nine");
		ArrayBuilder.addToList(stringListA, "Ten", "Eleven", "Twelve");
		List<List<String>> listOfStringLists = new ArrayList<List<String>>();
		ArrayBuilder.addToList(listOfStringLists, stringListA, stringListB);

		// ...

		ArrayBuilder.addToList(listOfStringLists, stringListA, stringListB);
		ArrayBuilder.addToList2(listOfStringLists, stringListA, stringListB);
		ArrayBuilder.addToList3(listOfStringLists, stringListA, stringListB);

		// ...
		
		 ArrayBuilder.faultyMethod(Arrays.asList("Hello!"), Arrays.asList("World!"));

	}
}