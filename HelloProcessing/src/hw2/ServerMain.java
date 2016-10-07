package hw2;

import common.EventManagementEngine;
import common.GameDescription;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
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
		GameDescription game = new TestGameDescription();
		game.generateGame(renderingE, physicsE, playerF, platformF);
		
		while(true){
			physicsE.tick(1);
			eventE.HandleNextEvents(8);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
