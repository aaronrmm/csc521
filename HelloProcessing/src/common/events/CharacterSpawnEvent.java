package common.events;

public class CharacterSpawnEvent extends AbstractEvent{
	
	private static ListenerRegistrar<CharacterSpawnEvent> registrar = new ListenerRegistrar<CharacterSpawnEvent>();
	
	public static void Register(GenericListener<CharacterSpawnEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
}
