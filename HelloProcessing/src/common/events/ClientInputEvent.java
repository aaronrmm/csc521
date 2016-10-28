package common.events;

import java.io.Serializable;

import networking.ClientHandler;

public class ClientInputEvent implements Serializable{

	transient boolean isNew = true;
	public Movement movement;
	
	public ClientInputEvent(){
		this.isNew = true;
	}
	public ClientInputEvent(Movement movement){
		this();
		this.movement = movement;
	}
	public transient ClientHandler client = null;
	public transient ClientInputEvent nextInput = null;
	
	public enum Movement{
		jump,
		left,
		right
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8197637262856162292L;

}
