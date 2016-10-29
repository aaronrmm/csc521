package physics;

import java.util.ArrayList;

import common.EventManagementEngine;
import common.TimingComponent;
import common.events.CharacterCollisionEvent;

public class BasicPhysicsEngine implements PhysicsEngine{

	private ArrayList<PhysicsComponent> pObjects = new ArrayList<PhysicsComponent>();
	private ArrayList<TimingComponent> timers = new ArrayList<TimingComponent>();
	final int MAX_SPEED=4;
	int time = 0;
	private EventManagementEngine eventE;
	
	public BasicPhysicsEngine(EventManagementEngine eventE) {
		this.eventE = eventE;
	}

	public void tick(int milliseconds){
		//apply each force to its object
		time+=milliseconds;
		for(TimingComponent timer : timers){
			timer.update(time);
		}
		
		for(PhysicsComponent pObject: pObjects){
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
			boolean path_blocked = false;
			Rectangle path = pObject.getPath(pObject.speed, milliseconds);
			for(PhysicsComponent obstacle: pObjects)
				if( obstacle!=pObject)
					if (obstacle.intersects(path)){
						eventE.queue(new CharacterCollisionEvent(pObject, obstacle));
						if(obstacle.blocksObject(pObject))
							path_blocked = true;
					}
			if(! path_blocked)
				pObject.translate(pObject.speed, milliseconds);
			else{
				pObject.speed.y=0;
				path_blocked=false;
				//try again just going horizontally
				path = pObject.getPath(pObject.speed, milliseconds);
				for(PhysicsComponent obstacle: pObjects)
					if( obstacle!=pObject)
						if (obstacle.intersects(path)){
							if(obstacle.blocksObject(pObject))
								path_blocked = true;
							obstacle.onCollision(pObject);
							pObject.onCollision(obstacle);
						}
				if(! path_blocked)
					pObject.translate(pObject.speed, milliseconds);
				else
					pObject.speed.x=0;
			}
		}
	}
	
	public void addDynamicObject(PhysicsComponent pObject, int x, int y){
		pObjects.add(pObject);
	}

	@Override
	public void registerTimer(TimingComponent timer) {
		timers.add(timer);
		
		
	}

	@Override
	public void addStaticObject(PhysicsComponent pObject, int x, int y) {
		pObjects.add(pObject);
		
	}
}
