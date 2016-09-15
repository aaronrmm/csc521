package hw1section4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PApplet;

public class Server extends PApplet{

	final static int PORT = 9500;
	

	int previous_time = 0;
	//concurrent collections
	static ConcurrentHashMap<Socket, ClientHandler> clients = new ConcurrentHashMap<Socket, ClientHandler>();
	//system engines
	GameEngine gameE = new GameEngine();
	ServerSocket ss;
	
	public void setup(){
		try {
			ss = new ServerSocket(PORT);
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
										clients.get(s).addNewInput((Input) oos.readObject());
										
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
		//act on the new inputs from each client
		for (ClientHandler client: clients.values()){
			Input input = client.getNewInputs();
			while(input != null){
				gameE.processInput(input);
				input = input.nextInput;
			}
		}
		int current_time = this.millis();
		int delta = this.millis()-previous_time;
		gameE.tick(delta/10);//todo justify slowing this down
		previous_time=current_time;
		
		for(ClientHandler client: clients.values()){
			client.update();
		}
	}
		
	public void keyPressed() {
		System.out.println(key);
		if(key=='x')
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
