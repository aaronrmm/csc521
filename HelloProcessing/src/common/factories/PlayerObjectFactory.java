package common.factories;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jbox2d.common.Vec2;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.PlayerInputComponent;
import common.RenderableComponent;
import common.RenderingEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

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
		logger.log(Level.SEVERE,"creating new player"+clientId);
		GameObject player = new GameObject(EntityClass.PLAYER);
		PlayerInputComponent controller = new PlayerInputComponent(player, this.eventE, clientId);
		player.add(controller);
		PhysicsComponent physicsComponent = new PhysicsComponent(player, new Rectangle(x,y,10,10), true, physics);
		player.add(physicsComponent);
		physicsComponent.addConstantForce(new Vec2(0,1));
		this.physics.addDynamicObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(player, physicsComponent, renderer);
		this.renderer.addObject(renderable);
		player.add(renderable);
		player.alive = true;
		return player;
	}
}
