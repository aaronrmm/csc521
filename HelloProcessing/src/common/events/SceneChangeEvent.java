package common.events;

public class SceneChangeEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private static ListenerRegistrar<SceneChangeEvent> registrar = new ListenerRegistrar<SceneChangeEvent>();
	
	public static void Register(GenericListener<SceneChangeEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
	
	public static void Unregister(GenericListener<SceneChangeEvent> listener) {
		registrar.Unregister(listener);
	}
}
