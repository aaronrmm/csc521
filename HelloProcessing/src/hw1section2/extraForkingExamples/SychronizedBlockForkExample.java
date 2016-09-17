package hw1section2.extraForkingExamples;

/*I was testing to see if a block of code in Thread A synchronized on an object (synchronized(monitor))
	would lock out a Thread B that accessed the object without a synchronization, allowing Thread A 
	priority over the object. Unfortuntely for me, that is not how it works. Thread B simply accesses
	the object, ignoring any locks by Thread A.
	
*/
public class SychronizedBlockForkExample implements Runnable {

	int i;
	boolean busy;
	SychronizedBlockForkExample other;
	static Object monitored = new Object() {
		@Override
		public String toString() {
			return "Monitored Object";
		}
	};

	public SychronizedBlockForkExample(int i, SychronizedBlockForkExample other) {
		this.i = i;
		if (i == 0) {
			busy = true;
		} else {
			this.other = other;
		}
	}

	public synchronized boolean isBusy() {
		return busy;
	}

	public void run() {
		if (i == 0) {
			while (true) {
				try {
					Thread.sleep(4000);
					synchronized (monitored) {
						System.out.println("thread 0 accessing " + monitored.toString());
						Thread.sleep(4000);
					}
				} catch (InterruptedException tie) {
					tie.printStackTrace();
				}
			}
		} else {
			while (true) {
				try {
					System.out.println("Thread 1 accessing " + monitored.toString());
					monitored = new Object();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		SychronizedBlockForkExample t1 = new SychronizedBlockForkExample(0, null);
		SychronizedBlockForkExample t2 = new SychronizedBlockForkExample(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}