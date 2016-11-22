package games.spaceinvaders;

import org.jbox2d.common.Vec2;

import common.EntityClass;
import common.GameObject;
import common.RenderingEngine;
import common.events.AbstractEvent;
import common.factories.PlatformObjectFactory;
import game.Game;
import physics.PhysicsEngine;
import scripting.ScriptManager;

public class BulletFactory extends PlatformObjectFactory{

	public BulletFactory(PhysicsEngine physics, RenderingEngine rendering) {
		super(physics, rendering);
	}

	public void SpawnBullet(int x, int y, float velocityX, float velocityY, String tag){
		GameObject go = super.create(x, y, 5, 5);
		go.entityClass = EntityClass.BULLET;
		go.networked = true;
		go.physicsC.addConstantForce(new Vec2(velocityX, velocityY));
		ScriptManager.bindArgument(tag, go);
		AbstractEvent deathTimer = new AbstractEvent(1000){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void Handle() {
				go.destroy();
			}};
		Game.eventE.queue(deathTimer);
	}
}
