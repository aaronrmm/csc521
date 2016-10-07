package common.factories;

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
	
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	private EventManagementEngine eventE;
	
	public PlayerObjectFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE){
		this.physics = physics;
		this.renderer = rendering;
		this.eventE = eventE;
	}
	
	public GameObject create(int x, int y){
		GameObject player = new GameObject(EntityClass.PLAYER);
		PlayerInputComponent controller = new PlayerInputComponent(this.eventE);
		player.add(controller, PlayerInputComponent.class.getName());
		PhysicsComponent physicsComponent = new PhysicsComponent(new Rectangle(x,y,10,10), true);
		player.add(physicsComponent, PhysicsComponent.class.getName());
		physicsComponent.addConstantForce(new Vector2d(0,1));
		this.physics.addObject(physicsComponent);
		RenderableComponent renderable = new RenderableComponent(physicsComponent);
		this.renderer.addObject(renderable);
		return player;
	}
}
