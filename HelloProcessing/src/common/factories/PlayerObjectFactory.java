package common.factories;

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
import physics.Rectangle;
import physics.Vector2d;

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
		PlayerInputComponent controller = new PlayerInputComponent(this.eventE, clientId);
		player.add(controller, PlayerInputComponent.class.getName());
		PhysicsComponent physicsComponent = new PhysicsComponent(new Rectangle(x,y,10,10), true, physics);
		player.add(physicsComponent, PhysicsComponent.class.getName());
		physicsComponent.addConstantForce(new Vector2d(0,1));
		this.physics.addDynamicObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(physicsComponent, renderer);
		this.renderer.addObject(renderable);
		player.add(renderable, RenderableComponent.class.getName());
		player.alive = true;
		return player;
	}
}
