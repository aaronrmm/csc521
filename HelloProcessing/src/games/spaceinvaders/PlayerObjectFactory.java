package games.spaceinvaders;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;

public class PlayerObjectFactory {
	private static final Logger logger = Logger.getLogger(PlayerObjectFactory.class.getName());
	
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	
	public PlayerObjectFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE){
		this.physics = physics;
		this.renderer = rendering;
	}
	
	public GameObject create(int x, int y, long clientId){
		logger.log(Level.INFO,"creating new player"+clientId);
		GameObject player = new GameObject(EntityClass.PLAYER);
		player.setProperty("tag", "player");
		player.networked = true;
		PhysicsComponent physicsComponent = physics.createPhysicsComponent(player, x, y, 20, 20, true);
		physicsComponent.setMaxSpeed(3);
		physicsComponent.setFriction(.02f);
		player.add(physicsComponent);
		this.physics.addDynamicObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(player, physicsComponent, renderer);
		this.renderer.addObject(renderable);
		player.add(renderable);
		player.alive = true;
		return player;
	}
}
