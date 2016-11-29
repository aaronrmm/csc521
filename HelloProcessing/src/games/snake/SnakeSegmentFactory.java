package games.snake;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import common.ScriptComponent;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class SnakeSegmentFactory {

	PhysicsEngine physE;
	RenderingEngine renderE;
	
	public SnakeSegmentFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE) {
		this.physE = physics;
		this.renderE = rendering;
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
