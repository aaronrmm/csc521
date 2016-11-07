package hw2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.RenderableComponent;
import common.events.AbstractEvent;
import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;
import common.events.SceneChangeEvent;
import game.Game;
import game.Scene;

public class ReplayEngine implements GenericListener<ClientInputEvent>{
	private static final Logger logger = Logger.getLogger(ReplayEngine.class.getName());
	private boolean is_recording = false;
	private static Scene replayScene;
	private static final long REPLAY_SCENE_ID = 574958;
	private long return_scene_id;
	private Queue<byte[]> recording = new LinkedList<byte[]>();
	private long record_start_time;
	private ByteArrayOutputStream baos;
	private ObjectOutputStream oos;
	private ByteArrayInputStream bis;
	private ObjectInputStream ois;
			
	public ReplayEngine(Scene return_scene){
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			this.return_scene_id = return_scene.id;
			CharacterSyncEvent.registrar.Register(r->this.record(r));
			CharacterDeathEvent.registrar.Register(r->this.record(r));
			CharacterSpawnEvent.registrar.Register(r->this.record(r));
		} catch (IOException e) {
			e.printStackTrace();
		}
		replayScene = new Scene(){
			@Override
			public void generateGameObjectUpdates(long timestamp, long expiration) {}
		};
		replayScene.id = REPLAY_SCENE_ID;
	}
	
	public Scene getReplayScene(){
		return replayScene;
	}
	
	@Override
	public void update(ClientInputEvent event) {
		switch (event.command) {
		case record_replay:
			is_recording = true;
			record_start_time = Game.eventtime.getTime();
			break;
		case play_replay:
			is_recording = false;
			play();
			break;
		case stop_replay:
			is_recording = false;
			stop();
			break;
		default:
			break;
		}
	}
	
	private void record(AbstractEvent r) {
		if(is_recording){
			try {
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(r);
				recording.add(baos.toByteArray());
				System.out.println(this.getClass().getName()+" recorded event "+r);
				logger.log(Level.FINEST, this.getClass().getName()+" recorded event "+r);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void play(){
		CharacterSyncEvent.registrar.Register(new GenericListener<CharacterSyncEvent>(){

			@Override
			public void update(CharacterSyncEvent event) {
				if(event.getCharacter() == null)
					logger.log(Level.SEVERE, "Character SyncEvent has null character");
				RenderableComponent renderable = event.getCharacter().renderingC;
				if(renderable == null)
					logger.log(Level.SEVERE, "Character SyncEvent has no renderable component "+event.getCharacter().entityClass+event.getCharacter().getId() );
				if(renderable.getGameObject() == null){
					logger.log(Level.SEVERE, "Character SyncEvent has renderable with null character");
					renderable.setGameObject( event.getCharacter());
				}
				Game.getScene(event.sceneId).renderableList.put(event.getCharacter().getId(), renderable);
				System.out.println("new X = "+renderable.getX());
			}
		});
		long play_start_time = Game.eventtime.getTime();
		System.out.println("starting playing replay at "+play_start_time);
		Game.eventE.queue(new SceneChangeEvent(REPLAY_SCENE_ID));
		for(byte[] r : recording){
			bis = new ByteArrayInputStream(r);
			try {
				ois = new ObjectInputStream(bis);
				AbstractEvent event = (AbstractEvent)ois.readObject();
				event.sceneId = REPLAY_SCENE_ID;
				event.timestamp = play_start_time+event.timestamp-record_start_time;
				Game.eventE.queue(event);
				System.out.println("queuing event at timestamp "+event.timestamp);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		recording.clear();
		
	}

	private void stop(){
		Game.eventE.queue(new SceneChangeEvent(this.return_scene_id));
	}

	
}
