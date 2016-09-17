package hw1section2.extraForkingExamples;



public class Confusion1 implements Runnable {

	int i;
	boolean busy;
	Confusion1 other;
	public static Object toy = new Object();


	public Confusion1(int i, Confusion1 other) {
		this.i = i;
		if(i==0) { busy = true; }
		else { this.other = other; }
	}


	public synchronized boolean isBusy() { return busy; } 
	public static synchronized void bed(int thread_number) { try {
		System.out.println("Thread"+thread_number+" sleeping...");
		Thread.sleep(1000);
		System.out.println("Thread"+thread_number+" awake.");
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} }


	public void run() {
		bed(i);
		synchronized(toy){
			System.out.println("Thread"+i+" playing with toy.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Thread"+i+" done playing with toy.");
	}

	public static void main(String[] args) {
		Confusion1 t1 = new Confusion1(0, null);
		Confusion1 t2 = new Confusion1(1, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
	}

}