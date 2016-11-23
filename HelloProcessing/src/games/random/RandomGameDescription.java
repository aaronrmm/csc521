package games.random;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.RenderingEngine;
import common.ScriptComponent;
import common.events.ClientInputEvent;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import physics.PhysicsEngine;
import scripting.ScriptManager;

public class RandomGameDescription implements GameDescription{

	@Override
	public GameObject spawnPlayer(int x, int y, long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE,
			PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		GameObject platform = platformF.create(0, 0, 30, 5);
		platform.add(new ScriptComponent("update", platform, platform));
		ScriptManager.bindArgument("game_object", platform);
		
	}

	@Override
	public void update(ClientInputEvent p) {
		// TODO Auto-generated method stub
		
	}

}
