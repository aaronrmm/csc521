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
				if(key=='t')
					input.command = Command.play_replay;
				if(key=='y')
					input.command = Command.stop_replay;
				Game.eventE.queue(input);
			}
			
		};
		replayE.getReplayScene().input_handler = main_scene.input_handler;
		
		ClientsideNetworking networking = new ClientsideNetworking(Game.eventE);
		ClientInputEvent.registrar.Register(networking);
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
			if (Game.eventtime.getTime() - lastTick >0){
				try{
				Game.current_scene.physicsE.tick((int)(Game.eventtime.getTime()-lastTick));
				lastTick = Game.eventtime.getTime();
				Game.eventE.HandleNextEvents(800);
				logger.log(Level.FINEST, "client tick");
				Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
