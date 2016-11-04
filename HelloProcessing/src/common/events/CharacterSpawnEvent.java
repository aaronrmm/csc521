package common.events;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CharacterSpawnEvent extends AbstractEvent{
	private static final Logger logger = Logger.getLogger(CharacterSpawnEvent.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ListenerRegistrar<CharacterSpawnEvent> registrar = new ListenerRegistrar<CharacterSpawnEvent>();
	
	public CharacterSpawnEvent(long clientId, long timestamp) {
		logger.log(Level.FINE, this.toString()+" constructed");
		
		this.player = clientId;
		this.timestamp = timestamp;
	}

	public static void Register(GenericListener<CharacterSpawnEvent> listener) {
		registrar.Register(listener);
	}
	public long player;
	
	@Override
	public void Handle() {
		logger.log(Level.FINE, this.toString()+" handled");
		registrar.UpdateListeners(this);
	}
	
	@Override
	public boolean getDebug(){
		return true;
	}

}
