package games.spaceinvaders;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.RenderingEngine;
import common.events.CharacterCollisionEvent;
import common.events.CharacterSpawnEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;
import common.timelines.Timeline;
import game.Game;
import games.platforms.PlatformObjectFactory;
import games.platforms.PlayerObjectFactory;
import games.platforms.SpawnPointFactory;
import physics.PhysicsEngine;
import scripting.ScriptManager;

public class SpaceInvadersGameDescription implements GameDescription{

	private static final int INVADERS = 15;
	
	PlayerShipFactory playerF2;
	AlienShipFactory alienF;
	
	
	PlatformObjectFactory platformF;
	public int aliens_alive;

	@Deprecated
	@Override
	public GameObject spawnPlayer(int x, int y, long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE,
			PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		
		playerF2 = new PlayerShipFactory(physicsE, renderingE, eventE);
		alienF = new AlienShipFactory(physicsE, renderingE, eventE);
		this.platformF = platformF;
		
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

		ScriptManager.bindArgument("Game", this);
		ScriptManager.executeScript("initiate");
		
	}

	public void initiate(){
		aliens_alive = 0;
		for (int i=0; i< INVADERS/2; i++){
			alienF.create(i*35, 0, 0);
			aliens_alive ++;
		}
		for (int i=0; i< INVADERS/2-1; i++){
			alienF.create((int)((i+.5)*35), 23, 0);
			aliens_alive ++;
		}

		platformF.create(-5,100,5,300);//left boundary
		platformF.create(300,100,5,300);//right boundary
	}
	@Override
	public void update(ClientInputEvent p) {
		// TODO Auto-generated method stub
		
	}

}
