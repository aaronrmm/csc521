package games.platforms;

import common.EntityClass;
import common.GameObject;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class SpawnPointFactory {
	
	private PhysicsEngine physicsE;

	public SpawnPointFactory(PhysicsEngine physicsE){
		this.physicsE = physicsE;
	}

	public GameObject create(int x, int y, int width, int height) {
		GameObject spawn = new GameObject(EntityClass.SPAWNPOINT);
		Rectangle spawnRect = new Rectangle(x,y,width,height);
		PhysicsComponent spawnPhysics = new PhysicsComponent(spawn, spawnRect, false, physicsE);
		spawn.add(spawnPhysics);
		physicsE.addStaticObject(spawnPhysics, x, y);
		return spawn;
	}
	
}
