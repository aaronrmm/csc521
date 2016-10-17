package hw1section5;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import physics.Rectangle;

public class ClientHandler {

	@SuppressWarnings("unused")
	private Socket socket;
	private ObjectOutputStream oos;
	private Input first_unread_input;
	private Input last_unread_input;
	private HashMap<Long, UpdatedRectangle> updates = new HashMap<Long, UpdatedRectangle>();
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

	public void addNewInput(Input input) {
		input.client = this;
		if (this.first_unread_input == null) {
			this.first_unread_input = input;
			this.last_unread_input = input;
		} else {
			this.last_unread_input.nextInput = input;
			this.last_unread_input = input;
		}
	}

	public Input getNewInputs() {
		Input newInput = first_unread_input;
		first_unread_input = null;
		return newInput;
	}

	public void addUpdate(Rectangle rectangle) {
		try{
			updates.put(rectangle.id, new UpdatedRectangle(rectangle));
		}catch(ConcurrentModificationException ex){
			ex.printStackTrace();
		}
	}

	private void clientUpdateLoop() {
		while (true) {
			try {
				for (UpdatedRectangle rect : updates.values()) {
					rect.isNew = false;
					oos.writeObject(rect.rectangle);
				}
				oos.reset();
				Thread.sleep(10);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
