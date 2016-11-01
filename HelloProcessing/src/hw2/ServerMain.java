package hw2;

import java.util.logging.Logger;

import common.EventManagementEngine;
import common.GameDescription;
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
		Timeline timeline = new Timeline(null, 10);
		EventManagementEngine eventE = new EventManagementEngine(timeline);
		ProcessingRenderingEngine renderingE = new ProcessingRenderingEngine();
		renderingE.initialize(eventE);
		PhysicsEngine physicsE = new BasicPhysicsEngine(eventE);
		PlayerObjectFactory playerF = new PlayerObjectFactory(physicsE, renderingE, eventE);
		PlatformObjectFactory platformF = new PlatformObjectFactory(physicsE, renderingE);
		SpawnPointFactory spawnF = new SpawnPointFactory(physicsE);
		GameDescription game = new TestGameDescription(eventE);
		game.generateGame(eventE, renderingE, physicsE, playerF, platformF, spawnF);
		ServersideNetworking networking = new ServersideNetworking(eventE,9596);
		networking.start();
		long lastTick = 0;
		while(true){
			physicsE.tick((int)(timeline.getTime()-lastTick));
			lastTick = timeline.getTime();
			eventE.HandleNextEvents(800);
			eventE.flushBuffer();
			networking.updateClients(renderingE.getRectangles());
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
