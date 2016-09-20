package hw1section2;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThirdThreadForkExample implements Runnable {

	int i;
	ThirdThreadForkExample other;


	public ThirdThreadForkExample(int i, ThirdThreadForkExample other) {
		this.i = i;
		if(i==0) { this.busy.set(true); }
		else { this.other = other; }
	}


	public AtomicBoolean busy = new AtomicBoolean(false);


	public void run() {
		if(i==0) {
			while(true){
				try{
					Thread.sleep(4000);
					synchronized(this) {
						notify();
					}
				}
				catch(InterruptedException tie) { tie.printStackTrace(); }
			}
		}
		if(i==1) {
			while(other.busy.get()==true) {
				System.out.println("Waiting!");
				try { synchronized(other) { other.wait(); } }
				catch(InterruptedException tie) { tie.printStackTrace(); }
			}
			System.out.println("Finished!");
		}
		else{
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			other.busy.set(false);
		}
	}

	public static void main(String[] args) {
		ThirdThreadForkExample t1 = new ThirdThreadForkExample(0, null);
		ThirdThreadForkExample t2 = new ThirdThreadForkExample(1, t1);
		ThirdThreadForkExample t3 = new ThirdThreadForkExample(2, t1);
		(new Thread(t2)).start();
		(new Thread(t1)).start();
		(new Thread(t3)).start();
	}

}