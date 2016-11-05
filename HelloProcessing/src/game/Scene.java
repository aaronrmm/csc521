package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import common.EventManagementEngine;
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
	public Timeline timeline = new Timeline();
	public EventManagementEngine eventE = new EventManagementEngine(timeline);
	public ConcurrentHashMap<Long, RenderableComponent>renderableList = new ConcurrentHashMap<Long, RenderableComponent>();
	HashMap<ListenerRegistrar<AbstractEvent>, GenericListener<AbstractEvent>> listeners = new HashMap<ListenerRegistrar<AbstractEvent>, GenericListener<AbstractEvent>> ();
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	public InputHandler input_handler = new InputHandler(){
		@Override
		public void keyPressed(char key) {
		}};
	public PhysicsEngine physicsE;
	
	public Scene(){
		physicsE = new BasicPhysicsEngine(eventE);
	}
	
	public void generateGameObjectUpdates(long timestamp, long expiration) {
		for(GameObject gameObject: gameObjects){
			if(gameObject.alive)
				eventE.queue(new CharacterSyncEvent(gameObject, timestamp, expiration));
		}
	}

	public void addGameObject(GameObject gameObject){
		this.gameObjects.add(gameObject);
	}
}
