package hw1section2;



public class DeadlockingForkExample implements Runnable {

	int i;
	boolean busy;
	DeadlockingForkExample other;


	public DeadlockingForkExample(int i, DeadlockingForkExample other) {
		this.i = i;
		if(i==0) { busy = true; }
		else { this.other = other; }
	}


	public synchronized boolean isBusy() { return busy; }


	public void run() {
		if(i==0) {
			try {
				Thread.sleep(4000);
				synchronized(this) {
				}
				Thread.sleep(4000);
				synchronized(this) {
					busy = false;
				}
			}
			catch(InterruptedException tie) { tie.printStackTrace(); }
		}
		else {
			while(other.isBusy()) {
				System.out.println("Waiting!");
				try { synchronized(other) { other.wait(); } }
				catch(InterruptedException tie) { tie.printStackTrace(); }
			}
			System.out.println("Finished!");
		}
	}

	public static void main(String[] args) {
		DeadlockingForkExample t1 = new DeadlockingForkExample(0, null);
		DeadlockingForkExample t2 = new DeadlockingForkExample(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}