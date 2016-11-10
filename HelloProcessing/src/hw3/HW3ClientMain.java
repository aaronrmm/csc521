package hw3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import common.RenderableComponent;
import common.events.CharacterDeathEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Command;
import common.events.GenericListener;
import common.events.SceneChangeEvent;
import common.events.TimeUpdateEvent;
import game.Game;
import game.InputHandler;
import game.Scene;
import hw2.ReplayEngine;
import networking.ClientsideNetworking;

public class HW3ClientMain {

	private static final Logger logger = Logger.getLogger(HW3ClientMain.class.getName());
	
	public static void main(String[]args){
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}

		Scene main_scene = new Scene();
		main_scene.id = 0;
		ReplayEngine replayE = new ReplayEngine(main_scene);
		
		@SuppressWarnings("unused")
		Game game = new Game(HW3ClientMain.class.getName(), main_scene, main_scene, replayE.getReplayScene());

		ClientsideNetworking networking = new ClientsideNetworking(Game.eventE);
		
		main_scene.input_handler = new InputHandler(){


			@Override
			public void keyPressed(char key) {
				ClientInputEvent input = new ClientInputEvent();
				if(key=='a')
					input.command = Command.left;
				if(key=='d')
					input.command = Command.right;
				if(key==' ')
					input.command = Command.jump;
				if(key=='r')
					input.command = Command.record_replay;
				if(key=='g')
					input.command = Command.play_replay;
				if(key=='c')
					input.command = Command.stop_replay;
				if(key=='f')
					input.command = Command.speed_up_replay;
				if(key=='h')
					input.command = Command.slow_replay;
				networking.update(input);
			}
			
		};
		replayE.getReplayScene().input_handler = main_scene.input_handler;
		TimeUpdateEvent.registrar.Register(new GenericListener<TimeUpdateEvent>(){

			@Override
			public void update(TimeUpdateEvent event) {
				Game.eventtime.SetTime(event.time);
			}
			
		});
		SceneChangeEvent.registrar.Register(new GenericListener<SceneChangeEvent>(){

			@Override
			public void update(SceneChangeEvent event) {
				Game.current_scene.renderableList.clear();
			}
			
		});
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
				if(!Game.getScene(event.sceneId).renderableList.containsValue(renderable)){
					logger.info("New renderable+"+renderable.id+" for gameObject"+event.getCharacter().getId()+" added to scene"+Game.current_scene.id);
				}
				Game.getScene(event.sceneId).renderableList.put(event.getCharacter().getId(), renderable);
			}
		});
		CharacterDeathEvent.registrar.Register(new GenericListener<CharacterDeathEvent>(){
			@Override
			public void update(CharacterDeathEvent event){
				if(event.character!=null)
					Game.getScene(event.sceneId).renderableList.remove(event.character.getId());
			}
		});
		networking.start();
		long lastTick = 0;

		while(true){
			if (Game.eventtime.getTime() >-1){
				try{
				Game.current_scene.physicsE.tick((int)(Game.eventtime.getTime()-lastTick));
				lastTick = Game.eventtime.getTime();
				Game.eventE.HandleNextEvents(800);
				logger.log(Level.FINEST, "client tick");
				Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Game.eventE.queue(new ClientInputEvent(-1L, Command.no_op));
				networking.update(new ClientInputEvent(-1L, Command.no_op));
			}
		}
		
	}
}
