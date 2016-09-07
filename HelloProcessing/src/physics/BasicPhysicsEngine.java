package physics;

import java.util.ArrayList;

public class BasicPhysicsEngine {

	private ArrayList<PhysicsObject> pObjects = new ArrayList<PhysicsObject>();
	
	public void tick(int milliseconds){
		//apply each force to its object
		for(PhysicsObject pObject: pObjects){
			for(Vector2d force : pObject.constantForces){
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
