package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.RenderableComponent;
import common.events.AbstractEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;

public class ServersideNetworking implements GenericListener<AbstractEvent>{

	private static final Logger logger = Logger.getLogger(ServersideNetworking.class.getName());
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

	@Override
	public void update(AbstractEvent event) {
		logger.log(Level.FINER	, "Sending event "+event.toString());
		if(event instanceof CharacterSyncEvent){
			CharacterSyncEvent syncEvent = (CharacterSyncEvent)event;
			RenderableComponent renderable = (RenderableComponent) (syncEvent.getCharacter().getComponent(RenderableComponent.class.getName()));
			if(renderable==null)logger.severe("No renderable found in SyncEvent");
		}
		for(ClientHandler client: clients.values()){
			client.addUpdate(event);
		}
		
	}

}
