package hw2;

import common.EventManagementEngine;
import common.GameDescription;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import common.timelines.Timeline;
import physics.BasicPhysicsEngine;
import physics.PhysicsEngine;

public class ServerMain {

	public static void main(String[]args){
		EventManagementEngine eventE = new EventManagementEngine();
		ProcessingRenderingEngine renderingE = new ProcessingRenderingEngine();
		renderingE.initialize(eventE);
		PhysicsEngine physicsE = new BasicPhysicsEngine();
		PlayerObjectFactory playerF = new PlayerObjectFactory(physicsE, renderingE, eventE);
		PlatformObjectFactory platformF = new PlatformObjectFactory(physicsE, renderingE);
		SpawnPointFactory spawnF = new SpawnPointFactory(physicsE);
		GameDescription game = new TestGameDescription(eventE);
		game.generateGame(renderingE, physicsE, playerF, platformF, spawnF);
		ServersideNetworking networking = new ServersideNetworking(eventE,9596);
		Timeline timeline = new Timeline(null, 10);
		networking.start();
		long lastTick = 0;
		while(true){
			physicsE.tick((int)(timeline.getTime()-lastTick));
			lastTick = timeline.getTime();
			eventE.HandleNextEvents(8);
			networking.updateClients(renderingE.getRectangles());
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
