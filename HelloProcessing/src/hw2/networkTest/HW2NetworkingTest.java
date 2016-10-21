package hw2.networkTest;

public class HW2NetworkingTest {

	final static int CLIENT_COUNT = 1;
	public static void main(String[]args){
		new Thread(new Runnable(){
			@Override
			public void run(){
				Server.main(new String[]{CLIENT_COUNT+""});
			}
		}).start();
		for(int i=0;i<CLIENT_COUNT;i++){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(new Runnable(){
				@Override
				public void run(){
					Client.main(new String[]{"200","0"});
				}
			}).start();
		}
	}
}
