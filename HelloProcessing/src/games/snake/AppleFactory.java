package games.snake;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import games.platforms.PlayerObjectFactory;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class AppleFactory extends PlayerObjectFactory{

	PhysicsEngine physE;
	RenderingEngine renderE;
	
	public AppleFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE) {
		super(physics, rendering, eventE);
		this.physE = physics;
		this.renderE = rendering;
	}
	
	@Override
	public GameObject create(int x, int y, long clientId){
		GameObject apple = new GameObject(EntityClass.BULLET);
		PhysicsComponent physics = new PhysicsComponent(apple, new Rectangle(x,y, 15, 15), false, physE);
		apple.add(physics);
		this.physE.addStaticObject(physics, x, y);
		RenderableComponent renderable = new RenderableComponent(apple, physics, renderE, new int[]{255,100,0,255});
		apple.add(renderable);
		this.renderE.addObject(renderable);
		apple.alive = true;
		apple.networked = true;
		return apple;
		
	}

	public GameObject create(int x, int y, int width, int height, long clientId){
		GameObject apple = new GameObject(EntityClass.BULLET);
		PhysicsComponent physics = new PhysicsComponent(apple, new Rectangle(x,y, width, height), false, physE);
		apple.add(physics);
		this.physE.addStaticObject(physics, x, y);
		RenderableComponent renderable = new RenderableComponent(apple, physics, renderE, new int[]{255,20,20,255});
		apple.add(renderable);
		this.renderE.addObject(renderable);
		apple.alive = true;
		apple.networked = true;
		return apple;
	}
}
