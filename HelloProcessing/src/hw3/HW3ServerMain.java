package hw3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Command;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import game.Game;
import game.InputHandler;
import game.Scene;
import hw2.ReplayEngine;
import networking.ServersideNetworking;

public class HW3ServerMain {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(HW3ServerMain.class.getName());
	
	public static void main(String[]args){
		
		//load logger config
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}

		Scene main_scene = new Scene();
		main_scene.id = 0;
		
		@SuppressWarnings("unused")
		Game game = new Game(HW3ServerMain.class.getName(), main_scene, main_scene);
		
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
				Game.eventE.queue(input);
			}
		};
		
		PlayerObjectFactory playerF = new PlayerObjectFactory(main_scene.physicsE,Game.renderingE,Game.eventE);
		PlatformObjectFactory platformF = new PlatformObjectFactory(main_scene.physicsE,Game.renderingE);
		SpawnPointFactory spawnF = new SpawnPointFactory(main_scene.physicsE);
		ServersideNetworking networking = new ServersideNetworking(Game.eventE,9596);
		CharacterSpawnEvent.Register(p->networking.update(p));
		CharacterSyncEvent.Register(p->networking.update(p));
		CharacterDeathEvent.Register(p->networking.update(p));
		ReplayEngine replayE = new ReplayEngine();
		ClientInputEvent.Register(p->replayE.update(p));
		HW3TestGameDescription gameD = new HW3TestGameDescription(Game.eventE);
		gameD.generateGame(Game.eventE, Game.renderingE, main_scene.physicsE, playerF, platformF, spawnF);
		ClientInputEvent.Register(p->gameD.update(p));
		
		
		networking.start();
		long lastTick = 0;
		while(true){
			if (Game.eventtime.getTime() - lastTick >0){
				Game.current_scene.physicsE.tick((int)(Game.eventtime.getTime()-lastTick));
				lastTick = Game.eventtime.getTime();
				Game.current_scene.generateGameObjectUpdates(Game.eventtime.getTime(), Game.eventtime.getTime()+1);
				Game.eventE.HandleNextEvents(800);
				
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
