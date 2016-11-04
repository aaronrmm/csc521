package common;

import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Vector2d;

public class OscillatingController extends AbstractComponent implements TimingComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PhysicsComponent physicsComponent;
	Vector2d left = new Vector2d(-1,0);
	Vector2d right = new Vector2d(1,0);
	
	public OscillatingController(GameObject gameObject, PhysicsComponent physicsComponent, PhysicsEngine physicsE){
		super(gameObject);
		this.physicsComponent = physicsComponent;
		physicsE.registerTimer(this);
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int timestamp) {
		if(timestamp%20>10){
			this.physicsComponent.addImpulseForce(left);
		}
		else
			this.physicsComponent.addImpulseForce(right);
		
	}
}
