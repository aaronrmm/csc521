package common;

import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import physics.PhysicsEngine;

public interface GameDescription {

	void generateGame(RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF, PlatformObjectFactory platformF);

	GameObject spawnPlayer(int x, int y, long clientId);

}
