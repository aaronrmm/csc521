package hw1section2;

/*I found a way to ensure Thread 0 locks out Thread 1, but Thread 1
* never locks out Thread 0. Thread 0 creates a new object
	
*/
public class SychronizedBlockForkExample2 implements Runnable {

	int i;
	boolean busy;
	SychronizedBlockForkExample2 other;
	static Object monitored = new Object() {
		@Override
		public String toString() {
			return "Monitored Object";
		}
	};

	public SychronizedBlockForkExample2(int i, SychronizedBlockForkExample2 other) {
		this.i = i;
		if (i == 0) {
			busy = true;
		} else {
			this.other = other;
		}
	}

	public void run() {
		if (i == 0) {
			while (true) {
				try {
					monitored = new Object();
					synchronized (monitored) {
						System.out.println("thread 0 accessing " + monitored.toString());
						Thread.sleep(500);
					}
				} catch (InterruptedException tie) {
					tie.printStackTrace();
				}
			}
		} else {
			while (true) {
				try {
					Object lock = monitored;
					synchronized(lock){
					System.out.println("Thread 1 accessing " + monitored.toString());
					monitored = new Object();
					Thread.sleep(4000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		SychronizedBlockForkExample2 t1 = new SychronizedBlockForkExample2(0, null);
		SychronizedBlockForkExample2 t2 = new SychronizedBlockForkExample2(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}