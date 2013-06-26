package puzzle;

/**
 * All I GET is static
 * @author weijian.zhongwj
 *
 */
public class AllStatic {
	public static void main(String[] args) {
		Dog dog = new Dog();
		Dog myDog = new MyDog();
		dog.bark();
		myDog.bark();
	}
}

class Dog{
	public static void bark(){
		System.out.println("woof  ");
	}
}
class MyDog extends Dog{
	public static void bark(){
	}
}