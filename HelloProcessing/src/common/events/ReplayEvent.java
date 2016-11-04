package common.events;

public class ReplayEvent extends ClientInputEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReplayCommand command;
	
	public ReplayEvent(ReplayCommand command){
		this.command = command;
	}
	
	public enum ReplayCommand{
		RECORD,
		PLAY,
		STOP
	}
}
