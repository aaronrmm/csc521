package common.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.GameObject;
import common.RenderableComponent;

public class CharacterSyncEvent extends AbstractEvent{
	private static final Logger logger = Logger.getLogger(CharacterSyncEvent.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static double priority = 100; 

	private GameObject character;
	
	public GameObject getCharacter(){
		return character;
	}
	
	public CharacterSyncEvent(GameObject character, long timestamp, long expiration){
		this.character = character;
		priority = -1;//will have priority -1 on server and 100 on client
	}

	public double getPriority(){
		return priority;
	}
	
	private static ListenerRegistrar<CharacterSyncEvent> registrar = new ListenerRegistrar<CharacterSyncEvent>();

	public static void Register(GenericListener<CharacterSyncEvent> listener) {
		registrar.Register(listener);
	}
	@Override
	public void Handle() {
		RenderableComponent renderable = (RenderableComponent) (character.getComponent(RenderableComponent.class.getName()));
		if (renderable==null){
			logger.log(Level.SEVERE,"RENDERABLE NULL");
		}
		else if (renderable.getGameObject()==null){
			logger.log(Level.SEVERE,"RENDERABLE.GAMEOBJECT == null");
		}
		registrar.UpdateListeners(this);
	}
}
