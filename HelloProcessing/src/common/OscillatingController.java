package common;

import physics.PhysicsComponent;
import physics.Vector2d;

public class OscillatingController implements TimingComponent{

	PhysicsComponent physicsComponent;
	Vector2d left = new Vector2d(-1,0);
	Vector2d right = new Vector2d(1,0);
	
	public OscillatingController(PhysicsComponent physicsComponent){
		this.physicsComponent = physicsComponent;
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
