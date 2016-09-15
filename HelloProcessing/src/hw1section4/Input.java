package hw1section4;

import java.io.Serializable;

public class Input implements Serializable{

	transient boolean isNew = true;
	
	public Input(){
		this.isNew = true;
	}
	public transient ClientHandler client = null;
	public transient Input nextInput = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8197637262856162292L;

}
