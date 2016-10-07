package hw2;

import common.GameDescription;
import common.RenderingEngine;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import physics.PhysicsEngine;

public class TestGameDescription implements GameDescription{

	@Override
	public void generateGame(RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF, PlatformObjectFactory platformF) {
		playerF.create(0, 0);
		platformF.create(0, 100, 200, 500);
	}

}
