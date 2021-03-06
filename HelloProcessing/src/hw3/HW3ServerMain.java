package hw3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import common.GameDescription;
import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Command;
import common.events.SceneChangeEvent;
import common.events.TimeUpdateEvent;
import game.Game;
import game.InputHandler;
import game.Scene;
import games.platforms.PlatformObjectFactory;
import games.platforms.PlatformsGameDescription;
import games.platforms.PlayerObjectFactory;
import games.platforms.SpawnPointFactory;
import networking.ServersideNetworking;
import scripting.ScriptManager;

public class HW3ServerMain {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(HW3ServerMain.class.getName());
	
	public static GameDescription gameD = new PlatformsGameDescription();
	
	public static void setGameDescription(GameDescription gameDescription){
		gameD = gameDescription;
	}
	
	public static void main(String[]args){
		
		//load logger config
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}

		Scene main_scene = new Scene();
		main_scene.id = 0;
		ReplayEngine replayE = new ReplayEngine(main_scene);
		
		@SuppressWarnings("unused")
		Game game = new Game(HW3ServerMain.class.getName(), main_scene, main_scene, replayE.getReplayScene());
		
		main_scene.input_handler = new InputHandler(){
			@Override
			public void keyPressed(char key) {
				System.out.println(key);
				ClientInputEvent input = new ClientInputEvent();
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
				if(key=='w')
					input.command = Command.up;
				if(key=='a')
					input.command = Command.left;
				if(key=='s')
					input.command = Command.down;
				if(key=='d')
					input.command = Command.right;
				if(key==' ')
					input.command = Command.jump;
				Game.eventE.queue(input);
			}
		};
		replayE.getReplayScene().input_handler = main_scene.input_handler;
		
		PlayerObjectFactory playerF = new PlayerObjectFactory(main_scene.physicsE,Game.renderingE,Game.eventE);
		PlatformObjectFactory platformF = new PlatformObjectFactory(main_scene.physicsE,Game.renderingE);
		SpawnPointFactory spawnF = new SpawnPointFactory(main_scene.physicsE);
		ServersideNetworking networking = new ServersideNetworking(Game.eventE,9596);
		SceneChangeEvent.registrar.Register(p->networking.update(p));
		CharacterSpawnEvent.registrar.Register(p->networking.update(p));
		CharacterSyncEvent.registrar.Register(p->networking.update(p));
		CharacterDeathEvent.registrar.Register(p->networking.update(p));
		//ClientInputEvent.Register(p->networking.update(p));
		ClientInputEvent.registrar.Register(p->replayE.update(p));
		gameD.generateGame(Game.eventE, Game.renderingE, main_scene.physicsE, playerF, platformF, spawnF);
		ClientInputEvent.registrar.Register(p->gameD.update(p));
		ClientInputEvent.registrar.Register(p->networking.update(p));
		TimeUpdateEvent.registrar.Register(p->networking.update(p));

		
		
		networking.start();
		long lastTick = 0;
		while(true){
			if (Game.eventtime.getTime()>-1){
				ScriptManager.run_scripts();
				Game.current_scene.physicsE.tick((int)(Game.eventtime.getTime()-lastTick));
				lastTick = Game.eventtime.getTime();
				Game.current_scene.generateGameObjectUpdates(Game.eventtime.getTime(), Game.eventtime.getTime()+1);
				Game.eventE.HandleNextEvents(500);
				
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Game.eventE.queue(new ClientInputEvent(-1L,Command.no_op));
				networking.update(new ClientInputEvent(-1L,Command.no_op));
			}
		}
	}
}
