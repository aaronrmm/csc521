package games.spaceinvaders;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.PlayerInputComponent;
import common.RenderableComponent;
import common.RenderingEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;

public class PlayerObjectFactory {
	private static final Logger logger = Logger.getLogger(PlayerObjectFactory.class.getName());
	
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	private EventManagementEngine eventE;
	
	public PlayerObjectFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE){
		this.physics = physics;
		this.renderer = rendering;
		this.eventE = eventE;
	}
	
	public GameObject create(int x, int y, long clientId){
		logger.log(Level.INFO,"creating new player"+clientId);
		GameObject player = new GameObject(EntityClass.PLAYER);
		player.networked = true;
		PlayerInputComponent controller = new PlayerInputComponent(player, this.eventE, clientId);
		player.add(controller);
		PhysicsComponent physicsComponent = physics.createPhysicsComponent(player, x, y, 100, 10, true);
		player.add(physicsComponent);
		this.physics.addDynamicObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(player, physicsComponent, renderer);
		this.renderer.addObject(renderable);
		player.add(renderable);
		player.alive = true;
		return player;
	}
}
