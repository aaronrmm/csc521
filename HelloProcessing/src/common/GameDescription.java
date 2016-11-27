package common;

import common.events.ClientInputEvent;
import games.platforms.PlatformObjectFactory;
import games.platforms.PlayerObjectFactory;
import games.platforms.SpawnPointFactory;
import physics.PhysicsEngine;

public interface GameDescription {

	@Deprecated
	GameObject spawnPlayer(int x, int y, long clientId);

	void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF,
			PlatformObjectFactory platformF, SpawnPointFactory spawnF);

	void update(ClientInputEvent p);
}
