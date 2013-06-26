package demo.jdk7;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokedynamicDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static CallSite mybsm(MethodHandles.Lookup callerClass,
			String dynMethodName, MethodType dynMethodType) throws Throwable {

		MethodHandle mh = callerClass.findStatic(InvokedynamicDemo.class,
				"IntegerOps.adder", MethodType.methodType(Integer.class,
						Integer.class, Integer.class));

		if (!dynMethodType.equals(mh.type())) {
			mh = mh.asType(dynMethodType);
		}

		return new ConstantCallSite(mh);
	}
}

class IntegerOps {

	public static Integer adder(Integer x, Integer y) {
		return x + y;
	}
}