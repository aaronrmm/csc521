package common;

import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import physics.PhysicsEngine;

public interface GameDescription {

	GameObject spawnPlayer(int x, int y, long clientId);

	void generateGame(RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF,
			PlatformObjectFactory platformF, SpawnPointFactory spawnF);

}
