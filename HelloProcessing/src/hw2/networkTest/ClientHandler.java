package hw2.networkTest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import hw1section5.Input;
import hw1section5.UpdatedRectangle;
import physics.Rectangle;

public class ClientHandler extends hw1section5.ClientHandler{

	private static final boolean DEBUG = false;
	private static final long MINIMUM_MESSAGES_RECEIVED = 1000;
	long messages_received = 0;
	boolean new_message = false;
	@SuppressWarnings("unused")
	private Socket socket;
	private ObjectOutputStream oos;
	private Input first_unread_input;
	private Input last_unread_input;
	private HashMap<Long, UpdatedRectangle> updates = new HashMap<Long, UpdatedRectangle>();
	protected long startTime;

	public ClientHandler(Socket s) {
		super(null);
		this.socket = s;
		try {
			this.oos = new ObjectOutputStream(s.getOutputStream());
			new Thread(new Runnable() {
				@Override
				public void run() {
					clientUpdateLoop();
				}
			}).start();
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
		this.new_message = true;
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
		try {
			updates.put(rectangle.id, new UpdatedRectangle(rectangle));
		} catch (ConcurrentModificationException ex) {
			ex.printStackTrace();
		}
	}

	private void clientUpdateLoop() {
		while (true) {
			if (new_message) {
				if(DEBUG)System.out.println("HAS NEW MESSAGE " + System.currentTimeMillis());
				try {
					messages_received++;
					if (messages_received >= MINIMUM_MESSAGES_RECEIVED) {
						System.out.println("Client recieved " + messages_received + " messages.");
						long time_delta = System.nanoTime() - startTime;
						System.out.println(time_delta);
						System.out.println(MINIMUM_MESSAGES_RECEIVED+" took "+time_delta+" nanoseconds, or "+time_delta/1000000+"."+time_delta%1000000+" milliseconds at "+time_delta/MINIMUM_MESSAGES_RECEIVED+" nanoseconds per client update iteration");
						break;
					}
					first_unread_input = null;
					for (UpdatedRectangle rect : updates.values()) {
						rect.isNew = false;
						oos.writeObject(rect.rectangle);
					}
					if (DEBUG) {
						oos.reset();
						Thread.sleep(10);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConcurrentModificationException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
