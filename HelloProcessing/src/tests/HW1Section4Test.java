package tests;

import hw1section4.Client;
import hw1section4.Server;

public class HW1Section4Test {

	public static void main(String[]args){
		new Thread(new Runnable(){
			@Override
			public void run(){
				Server.main(new String[]{""});
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			@Override
			public void run(){
				Client.main(new String[]{"200","0"});
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			@Override
			public void run(){
				Client.main(new String[]{"400","0"});
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			@Override
			public void run(){
				Client.main(new String[]{"600","0"});
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			@Override
			public void run(){
				Client.main(new String[]{"0","200"});
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			@Override
			public void run(){
				Client.main(new String[]{"200","200"});
			}
		}).start();
	}
}
