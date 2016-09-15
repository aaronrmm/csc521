package hw1section4;

import java.net.Socket;

public class ClientHandler {
	
	@SuppressWarnings("unused")
	private Socket socket;
	private Input first_unread_input;
	private Input last_unread_input;
	public ClientHandler(Socket s) {
		this.socket = s;
	}
	public void addNewInput(Input input){
		input.client = this;
		if(this.last_unread_input==null){
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
	
	public void update() {

		
	}

}
