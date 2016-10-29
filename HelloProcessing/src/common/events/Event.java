package common.events;

public class Event extends AbstractEvent{

	private static ListenerRegistrar<Event> registrar = new ListenerRegistrar<Event>();
	
	public static void Register(GenericListener<Event> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
}
