public class JavaDisplayThread extends Thread {

	public static void main(String[] args) {
		JavaDisplayThread t1 = new JavaDisplayThread();
		t1.setName("thread1");
		t1.start();
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		for (int i = 0; i < noThreads; i++)
			System.out.println("Thread No:" + i + " = "
					+ lstThreads[i].getName());
	}
}
