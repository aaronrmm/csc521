package games.spaceinvaders;

import org.jbox2d.common.Vec2;

import common.EntityClass;
import common.GameObject;
import common.RenderingEngine;
import common.factories.PlatformObjectFactory;
import physics.PhysicsEngine;
import scripting.ScriptManager;

public class BulletFactory extends PlatformObjectFactory{

	public BulletFactory(PhysicsEngine physics, RenderingEngine rendering) {
		super(physics, rendering);
	}

	public void SpawnBullet(int x, int y, float velocityX, float velocityY, String tag){
		GameObject go = super.create(x, y, 5, 5);
		go.entityClass = EntityClass.BULLET;
		go.physicsC.addConstantForce(new Vec2(velocityX, velocityY));
		ScriptManager.bindArgument(tag, go);
	}
}
