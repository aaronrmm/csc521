package hw2;

public class HW2Test {

	public static void main(String[]args){
		
		int clients = 1;
		int sleep = 1000;
		new Thread(new Runnable(){
			@Override
			public void run(){
				ServerMain.main(new String[]{""});
			}
		}).start();
		for(int i=0;i<clients;i++){
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(new Runnable(){
				@Override
				public void run(){
					ClientMain.main(new String[]{"200","0"});
				}
			}).start();
		}
	}
}
