package hw1section4;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import physics.Rectangle;

public class ClientHandler {
	
	@SuppressWarnings("unused")
	private Socket socket;
	private ObjectOutputStream oos;
	private Input first_unread_input;
	private Input last_unread_input;
	public ClientHandler(Socket s) {
		this.socket = s;
		try {
			this.oos = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void addNewInput(Input input){
		input.client = this;
		if(this.first_unread_input==null){
			this.first_unread_input = input;
			this.last_unread_input = input;
		}
		else{
			this.last_unread_input.nextInput = input;
			this.last_unread_input = input;
		}
	}
	
	public Input getNewInputs() {
		Input newInput = first_unread_input;
		first_unread_input = null;
		return newInput;
	}
	
	public void update(ArrayList<Rectangle> arrayList) {
		try {
			for(Rectangle rect : arrayList)
				oos.writeObject(rect);
				oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
