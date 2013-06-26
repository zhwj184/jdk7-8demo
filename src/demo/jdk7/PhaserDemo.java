package demo.jdk7;

import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	void runTasks(List<Runnable> tasks) {
		final Phaser phaser = new Phaser(1); // "1" to register self
		// create and start threads
		for (final Runnable task : tasks) {
			phaser.register();
			new Thread() {
				public void run() {
					phaser.arriveAndAwaitAdvance(); // await all creation
					task.run();
				}
			}.start();
		}

		// allow threads to start and deregister self
		phaser.arriveAndDeregister();
	}

}
