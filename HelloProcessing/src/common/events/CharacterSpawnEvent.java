package common.events;

public class CharacterSpawnEvent extends AbstractEvent{
	
	private static ListenerRegistrar<CharacterSpawnEvent> registrar = new ListenerRegistrar<CharacterSpawnEvent>();
	
	public CharacterSpawnEvent(long clientId, long timestamp) {
		this.player = clientId;
		this.timestamp = timestamp;
	}

	public static void Register(GenericListener<CharacterSpawnEvent> listener) {
		registrar.Register(listener);
	}
	public long player;
	
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
	
	@Override
	public boolean getDebug(){
		return true;
	}
}
