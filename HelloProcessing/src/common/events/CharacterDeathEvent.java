package common.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.GameObject;

public class CharacterDeathEvent extends AbstractEvent{
	private static final Logger logger = Logger.getLogger(CharacterDeathEvent.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ListenerRegistrar<CharacterDeathEvent> registrar = new ListenerRegistrar<CharacterDeathEvent>();

	@Override
	public double getPriority(){
			return 10;
	}
	
	@Override
	public boolean getDebug(){
		return true;
	}
	
	public GameObject character;
	
	public CharacterDeathEvent(GameObject character) {
		logger.log(Level.FINEST, this.getClass().getName() +" constructed for character "+character.getId());
		this.character = character;
	}

	public CharacterDeathEvent(GameObject go, int delay) {
		this(go);
		super.timestamp+=delay;
	}

	@Override
	public void Handle() {
		logger.log(Level.FINEST, this.getClass().getName() +" handled for character "+character.getId());
		registrar.UpdateListeners(this);
		character.destroy();
	}
}
