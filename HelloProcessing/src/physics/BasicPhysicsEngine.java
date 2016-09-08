package physics;

import java.awt.Rectangle;
import java.util.ArrayList;

public class BasicPhysicsEngine {

	private ArrayList<PhysicsObject> pObjects = new ArrayList<PhysicsObject>();
	final int MAX_SPEED=4;
	
	public void tick(int milliseconds){
		//apply each force to its object
		for(PhysicsObject pObject: pObjects){
			for(Vector2d force : pObject.constantForces){
				pObject.speed.add(force);
			}
			for(Vector2d force : pObject.impulseForces){
				pObject.speed.add(force);
			}
			pObject.impulseForces.clear();
			//limit speed to MAX_SPEED
			if(pObject.speed.x>MAX_SPEED)pObject.speed.x=MAX_SPEED;
			if(pObject.speed.x<-MAX_SPEED)pObject.speed.x=-MAX_SPEED;
			if(pObject.speed.y>MAX_SPEED)pObject.speed.y=MAX_SPEED;
			if(pObject.speed.y<-MAX_SPEED)pObject.speed.y=-MAX_SPEED;
			boolean collision_found = false;
			Rectangle path = pObject.getPath(pObject.speed, milliseconds);
			for(PhysicsObject obstacle: pObjects)
				if( obstacle!=pObject)
					if (obstacle.intersects(path)){
						collision_found = true;
						pObject.speed.x=0;
						pObject.speed.y=0;
					}
			if(! collision_found)
				pObject.translate(pObject.speed, milliseconds);
		}
	}
	
	public void addObject(PhysicsObject pObject){
		pObjects.add(pObject);
	}
}
