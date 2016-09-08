package physics;

import java.awt.Rectangle;
import java.util.ArrayList;

public class BasicPhysicsEngine {

	private ArrayList<PhysicsObject> pObjects = new ArrayList<PhysicsObject>();
	
	public void tick(int milliseconds){
		//apply each force to its object
		for(PhysicsObject pObject: pObjects){
			for(Vector2d force : pObject.constantForces){
				boolean collision_found = false;
				Rectangle path = pObject.getPath(force, milliseconds);
				for(PhysicsObject obstacle: pObjects)
					if( obstacle!=pObject)
						if (obstacle.intersects(path))
							collision_found = true;
				if(! collision_found)
					pObject.translate(force, milliseconds);
			}
			for(Vector2d force : pObject.impulseForces){
				pObject.translate(force, milliseconds);
			}
			pObject.impulseForces.clear();
		}
	}
	
	public void addObject(PhysicsObject pObject){
		pObjects.add(pObject);
	}
}
