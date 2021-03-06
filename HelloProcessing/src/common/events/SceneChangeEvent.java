package common.events;

public class SceneChangeEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static ListenerRegistrar<SceneChangeEvent> registrar = new ListenerRegistrar<SceneChangeEvent>();
	

	public long scene_id;
	
	public SceneChangeEvent(long scene_id){
		this.scene_id = scene_id;
	}
	
	@Override
	public void Handle() {
		registrar.UpdateListeners(this);
	}
}
