package games.platforms;

import common.EntityClass;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import game.Game;
import game.Scene;
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
		return create(x,y,width,height,this.physics,this.renderer);
	}
	
	public GameObject create(int x, int y, int width, int height, Scene scene){
		return create(x,y,width,height,scene.physicsE,Game.renderingE);
	}
	
	public GameObject create(int x, int y, int width, int height, PhysicsEngine physics, RenderingEngine renderer){
		GameObject object = new GameObject(EntityClass.BARRIER);
		PhysicsComponent physicsComponent = new PhysicsComponent(object, new Rectangle(x,y, width, height), true, physics);
		object.add(physicsComponent);
		this.physics.addStaticObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(object, physicsComponent, renderer);
		this.renderer.addObject(renderable);
		object.add(renderable);
		return object;
		
	} 
}
