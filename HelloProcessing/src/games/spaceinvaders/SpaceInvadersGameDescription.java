package games.spaceinvaders;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.RenderingEngine;
import common.ScriptComponent;
import common.events.CharacterCollisionEvent;
import common.events.CharacterSpawnEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import common.timelines.Timeline;
import game.Game;
import physics.PhysicsEngine;
import scripting.ScriptManager;

public class SpaceInvadersGameDescription implements GameDescription{

	private static final int INVADERS = 1;

	@Override
	public GameObject spawnPlayer(int x, int y, long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE,
			PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		games.spaceinvaders.PlayerObjectFactory playerF2 = new games.spaceinvaders.PlayerObjectFactory(physicsE, renderingE, eventE);
		for (int i=0; i< INVADERS; i++){
			GameObject platform = platformF.create(i*35, 0, 3, 20);
			platform.networked = true;
			platform.add(new ScriptComponent("alienupdate", platform));
			platform.setProperty("origin", i*35);
		}
		BulletFactory bulletF = new BulletFactory(physicsE, renderingE);
		ScriptManager.bindArgument("bullet_factory", bulletF);
		Timeline invader_time = new Timeline(Game.eventtime, 1);
		ScriptManager.bindArgument("time", invader_time);
		ScriptManager.bindArgument("player_object_factory", playerF2);
		
		CharacterCollisionEvent.registrar.Register(new GenericListener<CharacterCollisionEvent>(){
			@Override
			public void update(CharacterCollisionEvent event) {
				ScriptManager.executeScript("on_collision", event);
			}
		});
		
		ClientInputEvent.registrar.Register(new GenericListener<ClientInputEvent>(){
			@Override
			public void update(ClientInputEvent event) {
				ScriptManager.executeScript("on_input", event);
			}
		});
		
		CharacterSpawnEvent.registrar.Register(new GenericListener<CharacterSpawnEvent>(){

			@Override
			public void update(CharacterSpawnEvent event) {
				ScriptManager.executeScript("on_spawn", event);
			}
			
		});
		
		ScriptManager.executeScript("initiate");
	}

	@Override
	public void update(ClientInputEvent p) {
		// TODO Auto-generated method stub
		
	}

}
