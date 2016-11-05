package hw2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.GameDescription;
import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import common.timelines.Timeline;
import networking.ServersideNetworking;
import physics.BasicPhysicsEngine;
import physics.PhysicsEngine;

public class ServerMain {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(ServerMain.class.getName());
	
	public static void main(String[]args){
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		Timeline timeline = new Timeline(null, 100);
		EventManagementEngine eventE = new EventManagementEngine(timeline);
		ProcessingRenderingEngine renderingE = new ProcessingRenderingEngine();
		ProcessingRenderingEngine.Name = "Server";
		renderingE.initialize(eventE);
		PhysicsEngine physicsE = new BasicPhysicsEngine(eventE);
		PlayerObjectFactory playerF = new PlayerObjectFactory(physicsE, renderingE, eventE);
		PlatformObjectFactory platformF = new PlatformObjectFactory(physicsE, renderingE);
		SpawnPointFactory spawnF = new SpawnPointFactory(physicsE);
		GameDescription game = new TestGameDescription(eventE);
		game.generateGame(eventE, renderingE, physicsE, playerF, platformF, spawnF);
		ServersideNetworking networking = new ServersideNetworking(eventE,9596);
		CharacterSpawnEvent.Register(p->networking.update(p));
		CharacterSyncEvent.Register(p->networking.update(p));
		CharacterDeathEvent.Register(p->networking.update(p));
		ReplayEngine replayE = new ReplayEngine();
		ClientInputEvent.Register(p->replayE.update(p));
		
		
		networking.start();
		long lastTick = 0;
		while(true){
			if (timeline.getTime() - lastTick >0){
				physicsE.tick((int)(timeline.getTime()-lastTick));
				lastTick = timeline.getTime();
				game.generateGameObjectUpdates(timeline.getTime(), timeline.getTime()+1);
				eventE.HandleNextEvents(800);
				
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
