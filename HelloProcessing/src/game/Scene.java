package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import common.GameObject;
import common.RenderableComponent;
import common.events.AbstractEvent;
import common.events.CharacterSyncEvent;
import common.events.GenericListener;
import common.events.ListenerRegistrar;
import common.timelines.Timeline;
import physics.BasicPhysicsEngine;
import physics.PhysicsEngine;

public class Scene {
	public long id;
	public Timeline timeline = new Timeline();
	public ConcurrentHashMap<Long, RenderableComponent>renderableList = new ConcurrentHashMap<Long, RenderableComponent>();
	HashMap<ListenerRegistrar<AbstractEvent>, GenericListener<AbstractEvent>> listeners = new HashMap<ListenerRegistrar<AbstractEvent>, GenericListener<AbstractEvent>> ();
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	public InputHandler input_handler = new InputHandler(){
		@Override
		public void keyPressed(char key) {
		}};
	public PhysicsEngine physicsE;
	
	public Scene(){
		physicsE = new BasicPhysicsEngine(Game.eventE);
	}
	
	public void generateGameObjectUpdates(long timestamp, long expiration) {
		for(RenderableComponent renderable: renderableList.values()){
			GameObject gameObject = renderable.getGameObject();
			if(gameObject.networked)
				Game.eventE.queue(new CharacterSyncEvent(gameObject, timestamp, expiration));
		}
	}

	public void addGameObject(GameObject gameObject){
		this.gameObjects.add(gameObject);
	}
}
