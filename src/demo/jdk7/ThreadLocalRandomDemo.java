package demo.jdk7;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int MAX = 100000;
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

		long start = System.nanoTime();
		for (int i = 0; i < MAX; i++) {
			threadLocalRandom.nextDouble();
		}
		long end = System.nanoTime() - start;
		System.out.println("use time1 : " + end);

		long start2 = System.nanoTime();
		for (int i = 0; i < MAX; i++) {
			Math.random();
		}
		long end2 = System.nanoTime() - start2;
		System.out.println("use time2 : " + end2);

	}

}
