package common.events;

import common.GameObject;

public class CharacterDeathEvent extends AbstractEvent{

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
