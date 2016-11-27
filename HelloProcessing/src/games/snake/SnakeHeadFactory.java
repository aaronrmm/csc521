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

public class SnakeHeadFactory extends PlayerObjectFactory{

	PhysicsEngine physE;
	RenderingEngine renderE;
	
	public SnakeHeadFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE) {
		super(physics, rendering, eventE);
		this.physE = physics;
		this.renderE = rendering;
	}
	
	@Override
	public GameObject create(int x, int y, long clientId){
		GameObject head = new GameObject(EntityClass.PLAYER);
		PhysicsComponent physics = new PhysicsComponent(head, new Rectangle(x,y, 30, 30), false, physE);
		head.add(physics);
		this.physE.addDynamicObject(physics, x, y);
		RenderableComponent renderable = new RenderableComponent(head, physics, renderE, new int[]{0,255,0,255});
		head.add(renderable);
		this.renderE.addObject(renderable);
		head.alive = true;
		head.networked = true;
		return head;
		
	}

	public GameObject create(int x, int y, int width, int height, long clientId){
		GameObject head = new GameObject(EntityClass.PLAYER);
		PhysicsComponent physics = new PhysicsComponent(head, new Rectangle(x,y, width, height), false, physE);
		head.add(physics);
		this.physE.addDynamicObject(physics, x, y);
		RenderableComponent renderable = new RenderableComponent(head, physics, renderE, new int[]{0,255,0,255});
		head.add(renderable);
		this.renderE.addObject(renderable);
		head.alive = true;
		head.networked = true;
		return head;
	}
}
