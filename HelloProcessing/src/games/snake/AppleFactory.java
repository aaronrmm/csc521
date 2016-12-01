package games.snake;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class AppleFactory {

	PhysicsEngine physE;
	RenderingEngine renderE;
	
	public AppleFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE) {
		this.physE = physics;
		this.renderE = rendering;
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
