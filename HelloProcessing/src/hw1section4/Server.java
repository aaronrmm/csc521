package hw1section4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import physics.BasicPhysicsEngine;
import processing.core.PApplet;

public class Server extends PApplet{

	final static int PORT = 9500;
	

	int previous_time = 0;
	//concurrent collections
	static ConcurrentHashMap<Socket, ClientHandler> clients = new ConcurrentHashMap<Socket, ClientHandler>();
	static ConcurrentHashMap<Socket, Object>inputs = new ConcurrentHashMap<Socket, Object>();
	//system engines
	BasicPhysicsEngine physics = new BasicPhysicsEngine();
	
	public void setup(){
		try {
			ServerSocket ss = new ServerSocket(PORT);
			Thread clientHandler = new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						Socket s = ss.accept();
						clients.put(s, new ClientHandler(s));
						new Thread(new Runnable(){
							@Override
							public void run() {
								while(true){
									try {
										ObjectInputStream oos = new ObjectInputStream(s.getInputStream());
										inputs.put(s, oos.readObject());
										
									} catch (IOException e) {
										e.printStackTrace();
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
								}
							}}).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			clientHandler.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(){
		for(Object input: inputs.values()){
			processInput((Input) input);
		}
		
		int current_time = this.millis();
		int delta = this.millis()-previous_time;
		physics.tick(delta/10);//todo justify slowing this down
		previous_time=current_time;
		for(ClientHandler client: clients.values()){
			client.update();
		}
	}
	
	private void processInput(Input input){
		//act on the new inputs from each client
		for (ClientHandler client: clients.values()){
			client.getNewInputs();
		}
	}
		
}
