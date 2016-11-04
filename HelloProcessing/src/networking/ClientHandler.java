package networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.RenderableComponent;
import common.events.AbstractEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;

public class ClientHandler {

	private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
	@SuppressWarnings("unused")
	private Socket socket;
	private ObjectOutputStream oos;
	private ClientInputEvent first_unread_input;
	private ClientInputEvent last_unread_input;
	private ConcurrentLinkedQueue<AbstractEvent> updateQueue = new ConcurrentLinkedQueue<AbstractEvent>();
	public static long nextId;
	private long clientId;

	public ClientHandler(Socket s) {
		this.socket = s;
		this.clientId = nextId++;
		try {
			this.oos = new ObjectOutputStream(s.getOutputStream());
			new Thread(new Runnable(){
				@Override
				public void run() {
					clientUpdateLoop();
				}}).start();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void addNewInput(ClientInputEvent input) {
		input.client = this;
		if (this.first_unread_input == null) {
			this.first_unread_input = input;
			this.last_unread_input = input;
		} else {
			this.last_unread_input.nextInput = input;
			this.last_unread_input = input;
		}
	}

	public ClientInputEvent getNewInputs() {
		ClientInputEvent newInput = first_unread_input;
		first_unread_input = null;
		return newInput;
	}

	public void addUpdate(AbstractEvent event) {
		try{
			updateQueue.add(event);
		}catch(ConcurrentModificationException ex){
			ex.printStackTrace();
		}
	}

	private void clientUpdateLoop() {
		while (true) {
			try {
				for (AbstractEvent event : updateQueue) {
					oos.writeObject(event);

					if(event instanceof CharacterSyncEvent){
						CharacterSyncEvent syncEvent = (CharacterSyncEvent)event;
						logger.log(Level.SEVERE,"Sending syncEvent for character "+syncEvent.getCharacter().getId()+" with "+syncEvent.getCharacter().getComponentSize()+" components.");
						RenderableComponent renderable = (RenderableComponent) (syncEvent.getCharacter().getComponent(RenderableComponent.class.getName()));
						if(renderable==null)logger.severe("No renderable found in SyncEvent");
					}
				}
				updateQueue.clear();
				oos.reset();
				Thread.sleep(10);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ConcurrentModificationException e){
				e.printStackTrace();
			}
		}
	}

	public long getClientId() {
		return clientId;
	}

}
