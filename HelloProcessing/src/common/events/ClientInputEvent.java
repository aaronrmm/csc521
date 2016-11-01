package common.events;

import java.io.Serializable;
import java.util.logging.Logger;

import networking.ClientHandler;

public class ClientInputEvent extends AbstractEvent implements Serializable{
	transient final Logger logger = Logger.getLogger(ClientInputEvent.class.getName());

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
	
	@Override
	public double getPriority(){
		return 2;
	}
	
	private static ListenerRegistrar<ClientInputEvent> registrar = new ListenerRegistrar<ClientInputEvent>();
	
	public static void Register(GenericListener<ClientInputEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
		//logger.debug(this.timestamp);
	}
	public static void Unregister(GenericListener<ClientInputEvent> listener) {
		registrar.Unregister(listener);
	}
}
