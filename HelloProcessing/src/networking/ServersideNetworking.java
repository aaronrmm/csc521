package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import common.EventManagementEngine;
import common.events.ClientInputEvent;
import physics.Rectangle;

public class ServersideNetworking {

	private int port = 9596;
	private ServerSocket ss;
	private EventManagementEngine eventE;
	private static ConcurrentHashMap<Socket, ClientHandler> clients = new ConcurrentHashMap<Socket, ClientHandler>();
	
	public ServersideNetworking(EventManagementEngine eventE, int port){
		this.port = port;
		this.eventE = eventE;
	}
	
	public void start() {
		try {
			ss = new ServerSocket(port);
			new Thread(new Runnable() {
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
										clients.get(s).addNewInput(new ClientInputEvent());
										ObjectInputStream ois = new ObjectInputStream(
												s.getInputStream());
										while (true) {
											ClientInputEvent input = (ClientInputEvent)ois.readObject();
											input.client = clients.get(s);
											eventE.queue(input);
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
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateClients(List<Rectangle>updates) {
		for(ClientHandler client: clients.values()){
			for(Rectangle rectangle : updates){
				client.addUpdate(rectangle);
			}
		}
		
	}

}
