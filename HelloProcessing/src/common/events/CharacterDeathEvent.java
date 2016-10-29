package common.events;

public class CharacterDeathEvent extends AbstractEvent{

	private static ListenerRegistrar<CharacterDeathEvent> registrar = new ListenerRegistrar<CharacterDeathEvent>();
	
	public static void Register(GenericListener<CharacterDeathEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
}
