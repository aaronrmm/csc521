package hw2;

import common.EventManagementEngine;
import common.GameDescription;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
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
		
		networking.start();
		while(true){
			physicsE.tick(1);
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
