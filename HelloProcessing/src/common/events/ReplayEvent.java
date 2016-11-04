package common.events;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReplayEvent extends ClientInputEvent{
	private static final Logger logger = Logger.getLogger(ReplayEvent.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReplayCommand command;
	
	public ReplayEvent(ReplayCommand command){
		System.out.println("Replay created");
		logger.log(Level.FINE,"Replay commmand: "+this.toString());
		this.command = command;
	}
	
	public enum ReplayCommand{
		RECORD,
		PLAY,
		STOP
	}
}
