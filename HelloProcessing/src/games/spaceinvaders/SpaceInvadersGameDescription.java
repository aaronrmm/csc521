package games.spaceinvaders;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.RenderingEngine;
import common.ScriptComponent;
import common.events.ClientInputEvent;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import common.timelines.Timeline;
import game.Game;
import physics.PhysicsEngine;
import scripting.ScriptManager;

public class SpaceInvadersGameDescription implements GameDescription{

	private static final int INVADERS = 10;

	@Override
	public GameObject spawnPlayer(int x, int y, long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE,
			PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		for (int i=0; i< INVADERS; i++){
			GameObject platform = platformF.create(i*35, 0, 25, 20);
			platform.add(new ScriptComponent("alienupdate", platform));
			platform.setProperty("origin", i*35);
		}
		BulletFactory bulletF = new BulletFactory(physicsE, renderingE);
		ScriptManager.bindArgument("bullet_factory", bulletF);
		Timeline invader_time = new Timeline(Game.eventtime, 1);
		ScriptManager.bindArgument("time", invader_time);
		
	}

	@Override
	public void update(ClientInputEvent p) {
		// TODO Auto-generated method stub
		
	}

}
