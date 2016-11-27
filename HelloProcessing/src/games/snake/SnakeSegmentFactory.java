package games.snake;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import common.ScriptComponent;
import games.platforms.PlayerObjectFactory;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class SnakeSegmentFactory extends PlayerObjectFactory{

	PhysicsEngine physE;
	RenderingEngine renderE;
	
	public SnakeSegmentFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE) {
		super(physics, rendering, eventE);
		this.physE = physics;
		this.renderE = rendering;
	}
	
	@Override
	public GameObject create(int x, int y, long clientId){
		GameObject segment = new GameObject(EntityClass.PLAYER);
		PhysicsComponent physics = new PhysicsComponent(segment, new Rectangle(x,y, 30, 30), false, physE);
		segment.add(physics);
		this.physE.addDynamicObject(physics, x, y);
		RenderableComponent renderable = new RenderableComponent(segment, physics, renderE, new int[]{100,255,0,255});
		segment.add(renderable);
		this.renderE.addObject(renderable);
		segment.alive = true;
		segment.networked = true;
		return segment;
		
	}
	public GameObject create(int x, int y, int width, int height, long clientId){
		GameObject segment = new GameObject(EntityClass.PLAYER);
		PhysicsComponent physics = new PhysicsComponent(segment, new Rectangle(x,y, width, height), false, physE);
		segment.add(physics);
		this.physE.addDynamicObject(physics, x, y);
		RenderableComponent renderable = new RenderableComponent(segment, physics, renderE, new int[]{100,255,0,255});
		segment.add(renderable);
		this.renderE.addObject(renderable);
		segment.alive = true;
		segment.networked = true;
		segment.add(new ScriptComponent("update_segment", segment, segment));
		return segment;
		
	}

}
