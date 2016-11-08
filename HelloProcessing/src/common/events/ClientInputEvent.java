package common.events;

import java.io.Serializable;
import java.util.logging.Logger;

import networking.ClientHandler;

public class ClientInputEvent extends AbstractEvent implements Serializable{
	static final Logger logger = Logger.getLogger(ClientInputEvent.class.getName());

	transient boolean isNew = true;
	public Command command = Command.no_op;
	
	public ClientInputEvent(){
		this.isNew = true;
	}
	public ClientInputEvent(Command movement){
		this();
		this.command = movement;
	}
	public transient ClientHandler client = null;
	public transient ClientInputEvent nextInput = null;
	
	public enum Command{
		no_op,
		jump,
		left,
		right, 
		record_replay, 
		play_replay, 
		stop_replay,
		slow_replay,
		speed_up_replay
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8197637262856162292L;
	
	@Override
	public double getPriority(){
		return 2;
	}
	
	public static ListenerRegistrar<ClientInputEvent> registrar = new ListenerRegistrar<ClientInputEvent>();
	
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
		logger.finest("Handling "+this.toString());
	}
}
