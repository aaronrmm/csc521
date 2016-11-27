package games.snake;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.RenderingEngine;
import common.ScriptComponent;
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

public class SnakeGameDescription implements GameDescription{

	@Deprecated
	@Override
	public GameObject spawnPlayer(int x, int y, long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE,
			PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		

		SnakeHeadFactory snake_head_factory = new SnakeHeadFactory(physicsE, renderingE, eventE);
		SnakeSegmentFactory snake_segment_factory = new SnakeSegmentFactory(physicsE, renderingE, eventE);
		AppleFactory apple_factory = new AppleFactory(physicsE, renderingE, eventE);
		ScriptManager.bindArgument("snake_head_factory", snake_head_factory);
		ScriptManager.bindArgument("snake_segment_factory", snake_segment_factory);
		ScriptManager.bindArgument("apple_factory", apple_factory);
		Timeline snake_time = new Timeline(Game.eventtime, 1);
		ScriptManager.bindArgument("time", snake_time);
		ScriptManager.addScriptComponent(new ScriptComponent("update_snake", null, null));
		
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
		
	}

	@Override
	public void update(ClientInputEvent p) {
		// TODO Auto-generated method stub
		
	}

}
