package hw2.networkTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import common.RenderableComponent;
import common.RenderingEngine;
import hw1section5.Input;
import physics.Rectangle;
import processing.core.PApplet;

public class Server extends PApplet implements RenderingEngine{

	final static boolean DEBUG = false;
	static int MINIMUM_CLIENTS = 2;
	final static int NUMBER_OF_OBSTACLES = 2;
	static boolean timer_started = false;
	static int connectedClients = 0;
	public final static int WIDTH = 210;
	public final static int HEIGHT = 300;
	
	
	public static void main(String[] args) {
		MINIMUM_CLIENTS = Integer.getInteger(args[0],1);
		System.out.println("Waiting for " + MINIMUM_CLIENTS);
		if (DEBUG)
			PApplet.main(Server.class.getName());
		else{
			Server server = new Server();
			gameE = new GameEngine();
			gameE.initializeLevel(WIDTH, HEIGHT, NUMBER_OF_OBSTACLES);
			network();
			while(true){
				server.draw();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	final static int PORT = 9500;

	int previous_time = 0;
	// concurrent collections
	static ConcurrentHashMap<Socket, ClientHandler> clients = new ConcurrentHashMap<Socket, ClientHandler>();
	// system engines
	static GameEngine gameE = null;
	static ServerSocket ss;

	public void settings() {// runs first
		size(WIDTH, HEIGHT);

	}

	public static void network(){
		try {
			ss = new ServerSocket(PORT);
			Thread clientHandler = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (connectedClients< MINIMUM_CLIENTS) {
							Socket s = ss.accept();
							clients.put(s, new ClientHandler(s));
							connectedClients++;
							new Thread(new Runnable() {
								@Override
								public void run() {
									while(timer_started==false){
										try {
											Thread.sleep(10);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
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
						System.out.println(connectedClients+ " clients have connected. Start time:");
						System.out.println(System.nanoTime());
						for(ClientHandler handler : clients.values()){
							handler.startTime = System.nanoTime();
						}
						timer_started = true;
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
	
	public void setup() {
		gameE = new GameEngine();
		gameE.initializeLevel(this.height, this.width, NUMBER_OF_OBSTACLES);
		network();
	}

	//Main Game Loop
	public void draw() {
		if(DEBUG)
			this.frame.setLocation(0, 200);
		// act on the new inputs from each client
		for (ClientHandler client : clients.values()) {
			Input input = client.getNewInputs();
			if(DEBUG)
				while (input != null) {
					gameE.processInput(input);
					input = input.nextInput;
				}
		}
		
		if(DEBUG){
			//run game for some time
			int current_time = this.millis();
			int delta = this.millis() - previous_time;
			gameE.tick(delta / 10);// todo justify slowing this down
			previous_time = current_time;
			
			//draw game
			draw(gameE);
		}
		
		//send game info to each client
		for (ClientHandler client : clients.values()) {
			for(Rectangle rect : gameE.getRenderableList()){
				client.addUpdate(rect);
			}
		}
	}

	private void draw(GameEngine gameEngine) {
		this.fill(0);
		this.rect(0, 0, this.width, this.height);
		this.fill(255);
		for(Rectangle rect : gameEngine.getRenderableList())
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

	@Override
	public void addObject(RenderableComponent renderable) {
		gameE.addRenderableComponent(renderable);
	}
}
