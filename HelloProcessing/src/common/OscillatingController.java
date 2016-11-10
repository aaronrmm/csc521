package common;

import org.jbox2d.common.Vec2;

import physics.PhysicsComponent;
import physics.PhysicsEngine;

public class OscillatingController extends AbstractComponent implements TimingComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient PhysicsComponent physicsComponent;
	transient Vec2 left = new Vec2(-.1f,0);
	transient Vec2 right = new Vec2(.1f,0);
	
	final int frequency = 30;
	final int pause = 10;
	int startingX;
	int seed;
	
	public OscillatingController(GameObject gameObject, PhysicsComponent physicsComponent, PhysicsEngine physicsE, int startingX, int seed){
		super(gameObject);
		this.physicsComponent = physicsComponent;
		physicsE.registerTimer(this);
		this.startingX = startingX;
		this.seed = seed;
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int timestamp) {
		this.physicsComponent.getRectangle().x = startingX+Math.abs((timestamp*2+seed)%300-150);
		/*
		if(timestamp%frequency< frequency/2-pause){
			//this.physicsComponent.clearForces();
			this.physicsComponent.addConstantForce(left);
			return;
		}
		if(timestamp%frequency>frequency/2+pause){
			//this.physicsComponent.clearForces();
			this.physicsComponent.addConstantForce(right);
			return;
		}
		this.physicsComponent.clearForces();
			*/
		
	}
}
