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

public class PlayerShipFactory {
	private static final Logger logger = Logger.getLogger(PlayerShipFactory.class.getName());
	
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	
	public PlayerShipFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE){
		this.physics = physics;
		this.renderer = rendering;
	}
	
	public GameObject create(int x, int y, long clientId){
		logger.log(Level.INFO,"creating new player"+clientId);
		GameObject player = new GameObject(EntityClass.PLAYER);
		player.setProperty("tag", "player");
		player.networked = true;
		PhysicsComponent physicsComponent = physics.createPhysicsComponent(player, x, y, 20, 20, true, true);
		physicsComponent.setMaxSpeed(3);
		physicsComponent.setFriction(.02f);
		player.add(physicsComponent);
		this.physics.addDynamicObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(player, physicsComponent, renderer);
		renderable.setColor(new int[]{0,255,0,255});
		this.renderer.addObject(renderable);
		player.add(renderable);
		player.alive = true;
		return player;
	}
}
