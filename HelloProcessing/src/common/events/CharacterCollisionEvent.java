package common.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import physics.PhysicsComponent;

public class CharacterCollisionEvent extends AbstractEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CharacterCollisionEvent.class.getName());
	public PhysicsComponent object1;
	public PhysicsComponent object2;
	public CharacterCollisionEvent(PhysicsComponent object1, PhysicsComponent object2) {
		this.object1 = object1;
		this.object2 = object2;
		logger.log(Level.FINEST, object1.getGameObject().entityClass +"/"+ object2.getGameObject().entityClass);
	}

	public static ListenerRegistrar<CharacterCollisionEvent> registrar = new ListenerRegistrar<CharacterCollisionEvent>();

	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}

}
