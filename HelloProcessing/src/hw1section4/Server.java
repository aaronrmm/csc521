package hw1section4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import physics.Rectangle;
import processing.core.PApplet;

public class Server extends PApplet {

	public static void main(String[] args) {
		PApplet.main(Server.class.getName());
	}

	final static int PORT = 9500;

	int previous_time = 0;
	// concurrent collections
	static ConcurrentHashMap<Socket, ClientHandler> clients = new ConcurrentHashMap<Socket, ClientHandler>();
	// system engines
	GameEngine gameE = new GameEngine();
	ServerSocket ss;

	public void settings() {// runs first
		size(200, 200);

	}

	public void setup() {
		this.frame.setLocation(200, 200);
		gameE.initializeLevel(this.height, this.width, 20);
		try {
			ss = new ServerSocket(PORT);
			Thread clientHandler = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							Socket s = ss.accept();
							clients.put(s, new ClientHandler(s));
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										clients.get(s).addNewInput(new Input());
										ObjectInputStream ois = new ObjectInputStream(
												s.getInputStream());
										while (true) {
											clients.get(s).addNewInput(
													(Input) ois.readObject());
										}

									} catch (IOException e) {
										e.printStackTrace();
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
								}
							}).start();
						}
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

	public void draw() {
		// act on the new inputs from each client
		for (ClientHandler client : clients.values()) {
			Input input = client.getNewInputs();
			while (input != null) {
				gameE.processInput(input);
				input = input.nextInput;
			}
		}
		int current_time = this.millis();
		int delta = this.millis() - previous_time;
		gameE.tick(delta / 10);// todo justify slowing this down
		previous_time = current_time;
		draw(gameE);
		for (ClientHandler client : clients.values()) {
			new Thread(new Runnable(){
				@Override
				public void run(){
					client.update(gameE.getWorldView(client));
				}
			}).start();
		}
	}

	private void draw(GameEngine gameE2) {
		this.fill(0);
		this.rect(0, 0, this.width, this.height);
		this.fill(255);
		for(Rectangle rect : gameE2.getDrawables())
			this.rect(rect.x, rect.y, rect.width, rect.height);
			
		
	}

	public void keyPressed() {
		if(key==ESC)
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
