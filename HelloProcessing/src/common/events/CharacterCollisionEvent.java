package common.events;

import physics.PhysicsComponent;

public class CharacterCollisionEvent extends AbstractEvent{

	public PhysicsComponent object1;
	public PhysicsComponent object2;
	public CharacterCollisionEvent(PhysicsComponent object1, PhysicsComponent object2) {
		this.object1 = object1;
		this.object2 = object2;
	}

	private static ListenerRegistrar<CharacterCollisionEvent> registrar = new ListenerRegistrar<CharacterCollisionEvent>();
	
	public static void Register(GenericListener<CharacterCollisionEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}

}
