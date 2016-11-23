package games.spaceinvaders;

import org.jbox2d.common.Vec2;

import common.EntityClass;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import common.events.AbstractEvent;
import common.events.CharacterDeathEvent;
import common.factories.GameObjectFactory;
import game.Game;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;
import scripting.ScriptManager;

public class BulletFactory extends GameObjectFactory{

	private PhysicsEngine physics;
	private RenderingEngine renderer;

	public BulletFactory(PhysicsEngine physics, RenderingEngine rendering) {
		this.physics = physics;
		this.renderer = rendering;
	}

	public void SpawnBullet(int x, int y, float velocityX, float velocityY, String tag, int[]color){
		System.out.println(velocityX+","+velocityY);
		GameObject go = new GameObject(EntityClass.BULLET);
		go.setProperty("tag", tag);
		PhysicsComponent physicsComponent = new PhysicsComponent(go, new Rectangle(x,y, 5, 5), false, physics);
		go.add(physicsComponent);
		this.physics.addStaticObject(physicsComponent, x, y);
		RenderableComponent renderable = new RenderableComponent(go, physicsComponent, renderer, color);
		this.renderer.addObject(renderable);
		go.add(renderable);
		go.networked = true;
		go.physicsC.addImpulseForce(new Vec2(velocityX, velocityY));
		ScriptManager.bindArgument(tag, go);
		AbstractEvent deathTimer = new AbstractEvent(1000){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void Handle() {
				go.destroy();
				Game.eventE.queue(new CharacterDeathEvent(go));
			}};
		Game.eventE.queue(deathTimer);
	}
}
