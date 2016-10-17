package common;

import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Vector2d;

public class OscillatingController extends AbstractComponent implements TimingComponent{

	GameObject gameObject;
	PhysicsComponent physicsComponent;
	Vector2d left = new Vector2d(-1,0);
	Vector2d right = new Vector2d(1,0);
	
	public OscillatingController(PhysicsComponent physicsComponent, PhysicsEngine physicsE){
		this.physicsComponent = physicsComponent;
		physicsE.registerTimer(this);
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int timestamp) {
		if(timestamp%100>50){
			this.physicsComponent.addImpulseForce(left);
		}
		else
			this.physicsComponent.addImpulseForce(right);
		
	}
}
