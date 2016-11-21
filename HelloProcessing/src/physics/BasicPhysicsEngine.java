package physics;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.jbox2d.common.Vec2;

import common.EventManagementEngine;
import common.GameObject;
import common.TimingComponent;
import common.events.CharacterCollisionEvent;

public class BasicPhysicsEngine implements PhysicsEngine{

	private ConcurrentHashMap<Long, PhysicsComponent> pObjects = new ConcurrentHashMap<Long, PhysicsComponent> ();
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
		
		for(PhysicsComponent pObject: pObjects.values()){
			for(Vec2 force : pObject.constantForces){
				pObject.speed = pObject.speed.add(force);
			}
			for(Vec2 force : pObject.impulseForces){
				pObject.speed = pObject.speed.add(force);
			}
			pObject.impulseForces.clear();
			//limit speed to MAX_SPEED
			if(pObject.speed.x>MAX_SPEED)pObject.speed.x=MAX_SPEED;
			if(pObject.speed.x<-MAX_SPEED)pObject.speed.x=-MAX_SPEED;
			if(pObject.speed.y>MAX_SPEED)pObject.speed.y=MAX_SPEED;
			if(pObject.speed.y<-MAX_SPEED)pObject.speed.y=-MAX_SPEED;
			boolean path_blocked = false;
			Rectangle path = pObject.getPath(pObject.speed, milliseconds);
			ArrayList<PhysicsComponent> collidedObstacles = new ArrayList<PhysicsComponent>();
			for(PhysicsComponent obstacle: pObjects.values())
				if( obstacle!=pObject)
					if (obstacle.intersects(path)){
						collidedObstacles.add(obstacle);
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
				for(PhysicsComponent obstacle: pObjects.values())
					if( obstacle!=pObject)
						if (obstacle.intersects(path)){
							if(!collidedObstacles.contains(obstacle))
								collidedObstacles.add(obstacle);
							if(obstacle.blocksObject(pObject))
								path_blocked = true;
						}
				if(! path_blocked)
					pObject.translate(pObject.speed, milliseconds);
				else
					pObject.speed.x=0;
			}
			for(PhysicsComponent obstacle:collidedObstacles){
				eventE.queue(new CharacterCollisionEvent(pObject,obstacle));
			}
		}
	}
	
	public void addDynamicObject(PhysicsComponent pObject, int x, int y){
		pObjects.put(pObject.getGameObject().getId(), pObject);
	}

	@Override
	public void registerTimer(TimingComponent timer) {
		timers.add(timer);
		
		
	}

	@Override
	public void addStaticObject(PhysicsComponent pObject, int x, int y) {
		pObjects.put(pObject.getGameObject().getId(), pObject);
		
	}

	@Override
	public void remove(PhysicsComponent physicsComponent) {
		this.pObjects.remove(physicsComponent.getGameObject().getId());
	}
	

	@Override
	public PhysicsComponent createPhysicsComponent(GameObject player, int x, int y, int width, int height, boolean isDynamic) {
		PhysicsComponent physicsComponent = new PhysicsComponent(player, new Rectangle(x,y, width, height), false, this);
		if (isDynamic) this.addDynamicObject(physicsComponent, x, y);
		else this.addStaticObject(physicsComponent, x, y);
		return physicsComponent;
	}
}
