package hw1section5;

import java.io.Serializable;

import networking.ClientHandler;

public class Input implements Serializable{

	transient boolean isNew = true;
	public Movement movement;
	
	public Input(){
		this.isNew = true;
	}
	public Input(Movement movement){
		this();
		this.movement = movement;
	}
	public transient ClientHandler client = null;
	public transient Input nextInput = null;
	
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
