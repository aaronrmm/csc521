package common;

import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import physics.PhysicsEngine;

public interface GameDescription {

	GameObject spawnPlayer(int x, int y, long clientId);

	void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF,
			PlatformObjectFactory platformF, SpawnPointFactory spawnF);

	void generateGameObjectUpdates(long timestamp, long expiration);
}
