package hw1section2;



public class OriginalForkExample implements Runnable {

	int i;
	boolean busy;
	OriginalForkExample other;


	public OriginalForkExample(int i, OriginalForkExample other) {
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
					notify();
				}
				Thread.sleep(4000);
				synchronized(this) {
					busy = false;
					notify();
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
		OriginalForkExample t1 = new OriginalForkExample(0, null);
		OriginalForkExample t2 = new OriginalForkExample(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}