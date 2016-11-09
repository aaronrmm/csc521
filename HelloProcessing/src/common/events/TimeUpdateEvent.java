package common.events;

public class TimeUpdateEvent extends AbstractEvent{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long clientToUpdate;
	public long time;

	public TimeUpdateEvent(long time, long clientId) {
		this.timestamp = 0;
		this.clientToUpdate = clientId;
		this.time = time;
		
	}

	public static ListenerRegistrar<TimeUpdateEvent> registrar = new ListenerRegistrar<TimeUpdateEvent>();
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
		
	}

}
