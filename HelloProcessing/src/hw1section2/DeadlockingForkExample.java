package hw1section2;

public class DeadlockingForkExample implements Runnable {

	int i;
	DeadlockingForkExample other;
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();

	public DeadlockingForkExample(int i) {
		this.i = i;
	}

	public void run() {
		try {
			if (i == 0) {
				System.out.println("Thread 1 waiting to access lock 1");
				synchronized (lock1) {
					System.out.println("Thread 1 accessing lock 1");
					Thread.sleep(2000);
					System.out.println("Thread 1 waiting to access lock 2");
					
					/*This thread deadlocks here because it wants to access lock2, but thread 2 is busy with it until
					* thread 2 can access lock 1, which this thread is busy with.
					*/
					synchronized (lock2) {
						System.out.println("Thread 1 accessing lock 2");
					}
				}
			} else {
				System.out.println("Thread 2 waiting to access lock 2");
				synchronized (lock2) {
					System.out.println("Thread 2 accessing lock 2");
					Thread.sleep(2000);
					System.out.println("Thread 2 waiting to access lock 1");
					synchronized (lock1) {
						System.out.println("Thread 2 accessing lock 1");
					}
				}
			}
			System.out.println("Finished!");
		} catch (InterruptedException tie) {
			tie.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DeadlockingForkExample t1 = new DeadlockingForkExample(0);
		DeadlockingForkExample t2 = new DeadlockingForkExample(1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}