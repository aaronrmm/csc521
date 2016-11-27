package games.spaceinvaders;

import java.util.logging.Logger;

import common.EventManagementEngine;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import common.ScriptComponent;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class AlienShipFactory {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AlienShipFactory.class.getName());
	
	private PhysicsEngine physics;
	private RenderingEngine renderer;
	
	public AlienShipFactory(PhysicsEngine physics, RenderingEngine rendering, EventManagementEngine eventE){
		this.physics = physics;
		this.renderer = rendering;
	}
	
	public GameObject create(int x, int y, long clientId){
		GameObject platform = new GameObject(null);
		platform.networked = true;
		platform.add(new ScriptComponent("alienupdate", platform, platform));
		platform.setProperty("origin", x);
		platform.setProperty("originY", y);
		platform.setProperty("tag", "alien");
		platform.alive = true;
		PhysicsComponent physicsComponent = new PhysicsComponent(platform, new Rectangle(x,y, 15, 15), false, physics);
		platform.add(physicsComponent);
		this.physics.addStaticObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(platform, physicsComponent, renderer);
		renderable.setColor(new int[]{255,0,255,255});
		this.renderer.addObject(renderable);
		platform.add(renderable);
		return platform;
	}
}
