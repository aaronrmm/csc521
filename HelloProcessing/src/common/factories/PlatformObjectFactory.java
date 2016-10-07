package common.factories;

import common.EntityClass;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class PlatformObjectFactory {
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	
	public PlatformObjectFactory(PhysicsEngine physics, RenderingEngine rendering){
		this.physics = physics;
		this.renderer = rendering;
	}
	
	public GameObject create(int x, int y, int width, int height){
		GameObject object = new GameObject(EntityClass.BARRIER);
		PhysicsComponent physicsComponent = new PhysicsComponent(new Rectangle(x,y, width, height), true);
		object.add(physicsComponent, PhysicsComponent.class.getName());
		this.physics.addObject(physicsComponent);
		RenderableComponent renderable = new RenderableComponent(physicsComponent);
		this.renderer.addObject(renderable);
		return object;
	}
}
