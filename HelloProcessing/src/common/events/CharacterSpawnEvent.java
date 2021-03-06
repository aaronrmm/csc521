package common.events;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CharacterSpawnEvent extends AbstractEvent{
	private static final Logger logger = Logger.getLogger(CharacterSpawnEvent.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static ListenerRegistrar<CharacterSpawnEvent> registrar = new ListenerRegistrar<CharacterSpawnEvent>();
	
	public CharacterSpawnEvent(long clientId, long timestamp) {
		logger.log(Level.FINEST, this.toString()+" constructed");
		
		this.player = clientId;
		this.timestamp = timestamp;
	}

	public long player;
	
	@Override
	public void Handle() {
		logger.log(Level.FINEST, this.toString()+" handled");
		registrar.UpdateListeners(this);
	}
	
	@Override
	public boolean getDebug(){
		return true;
	}

}
