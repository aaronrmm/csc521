package common.factories;

import common.EntityClass;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class PlayerObjectFactory {
	
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	
	public PlayerObjectFactory(PhysicsEngine physics, RenderingEngine rendering){
		this.physics = physics;
		this.renderer = rendering;
	}
	
	public GameObject create(int x, int y){
		GameObject player = new GameObject(EntityClass.PLAYER);
		PhysicsComponent physicsComponent = new PhysicsComponent(new Rectangle(x,y,20,10), true);
		this.physics.addObject(physicsComponent);
		RenderableComponent renderable = new RenderableComponent(physicsComponent);
		this.renderer.addObject(renderable);
		player.add(physicsComponent, PhysicsComponent.class.getName());
		return player;
	}
}
