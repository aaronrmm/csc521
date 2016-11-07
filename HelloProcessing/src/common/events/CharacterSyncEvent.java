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
		if(character==null)logger.log(Level.SEVERE, "CharacterSyncEvent constructed with null character");
		this.character = character;
		try{
			logger.log(Level.FINE, "CharacterSyncEvent for characterId:" + character.getId());
		}
		catch(Exception ex){
			logger.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
		}
		priority = -1;//will have priority -1 on server and 100 on client
	}

	public double getPriority(){
		return priority;
	}
	
	public static ListenerRegistrar<CharacterSyncEvent> registrar = new ListenerRegistrar<CharacterSyncEvent>();

	@Override
	public void Handle() {
		RenderableComponent renderable = character.renderingC;
		if (renderable==null){
			logger.log(Level.SEVERE,"RENDERABLE NULL");
		}
		else if (renderable.getGameObject()==null){
			logger.log(Level.SEVERE,"RENDERABLE.GAMEOBJECT == null");
		}
		registrar.UpdateListeners(this);
	}
}
