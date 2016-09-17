package hw1section2.extraForkingExamples;



public class SimplifiedForkExample implements Runnable {

	int i;
	boolean busy;
	SimplifiedForkExample other;
	public static Object monitored = new Object();


	public SimplifiedForkExample(int i, SimplifiedForkExample other) {
		this.i = i;
		if(i==0) { busy = true; }
		else { this.other = other; }
	}


	public synchronized boolean isBusy() { return busy; } 


	public void run() {
		if(i==0) {
			try {
				Thread.sleep(2000);
				synchronized(monitored) {
					monitored.notify();
				}
				Thread.sleep(2000);
				synchronized(monitored) {
					monitored.notify();
					Thread.sleep(2000);
					busy = false;
				}
			}
			catch(InterruptedException tie) { tie.printStackTrace(); }
		}
		else {
			while(other.busy) {
				System.out.print("Waiting...");
				synchronized(monitored) { 
					System.out.println("now"); 
					try {
						monitored.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.print("done..."); }
				System.out.println("now" );
			}
			System.out.println("Finished!");
		}
	}

	public static void main(String[] args) {
		SimplifiedForkExample t1 = new SimplifiedForkExample(0, null);
		SimplifiedForkExample t2 = new SimplifiedForkExample(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}