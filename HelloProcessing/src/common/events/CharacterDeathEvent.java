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
	private static ListenerRegistrar<CharacterDeathEvent> registrar = new ListenerRegistrar<CharacterDeathEvent>();

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
		logger.log(Level.SEVERE, this.getClass().getName() +" constructed for character "+character.getId());
		this.character = character;
	}
	public static void Register(GenericListener<CharacterDeathEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
}
