package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.events.AbstractEvent;
import common.events.ClientInputEvent;
import common.events.ErrorEvent;
import common.events.GenericListener;

public class ClientsideNetworking implements GenericListener<ClientInputEvent>{

	private static final Logger logger = Logger.getLogger(ServersideNetworking.class.getName());
	final static int PORT = 9596;
	final static String HOST = "127.0.0.1";
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ConcurrentLinkedQueue<ClientInputEvent> inputBuffer = new ConcurrentLinkedQueue<ClientInputEvent>();
	private EventManagementEngine eventE;
	
	public ClientsideNetworking(EventManagementEngine eventE){
		this.eventE = eventE;
	}
	public void start(){
		try {
			socket = new Socket(HOST,PORT);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(new ClientInputEvent());
			new Thread(new Runnable(){
				@Override
				public void run(){
					while(true){
						try {
							AbstractEvent event = (AbstractEvent)ois.readObject();
							eventE.queue(event);
							logger.log(Level.SEVERE, "client received "+event.toString());
						} catch (ClassNotFoundException | IOException e) {
							eventE.queue(new ErrorEvent(e.getMessage()));
							e.printStackTrace();
						}
					}
				}
			}).start();
			new Thread(new Runnable(){
				@Override
				public void run(){
					InputSendingLoop();
				}
			}).start();
		} catch (UnknownHostException e) {
			eventE.queue(new ErrorEvent(e.getMessage()));
			e.printStackTrace();
		} catch (IOException e) {
			eventE.queue(new ErrorEvent(e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void InputSendingLoop(){
		while(true){
			try {
				while(true){
					ClientInputEvent nextInput = inputBuffer.peek();
					if(nextInput!=null){
							oos.writeObject(nextInput);
							inputBuffer.remove();
					}else break;
				}Thread.sleep(100);//Temporarily used to reduce processing load for multiple clients.
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(ClientInputEvent event) {
		inputBuffer.add(event);
	}
}
