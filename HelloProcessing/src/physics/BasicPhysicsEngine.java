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
				pObject.speed = pObject.speed.add(force.mul(milliseconds));
			}
			for(Vec2 force : pObject.impulseForces){
				pObject.speed = pObject.speed.add(force);
			}
			if(pObject.friction>0&&pObject.speed.length()>=pObject.friction){
				pObject.speed = pObject.speed.sub(pObject.speed.mul(pObject.friction/pObject.speed.length()));
			}
			pObject.impulseForces.clear();
			//limit speed to MAX_SPEED
			//if(pObject.speed.length() > pObject.max_speed) pObject.speed = pObject.speed.mul(pObject.max_speed/pObject.speed.length());
			if(pObject.speed.x>pObject.max_speed) pObject.speed.x = pObject.max_speed;
			if(pObject.speed.x<-pObject.max_speed) pObject.speed.x = -pObject.max_speed;
			if(pObject.speed.y>pObject.max_speed) pObject.speed.y = pObject.max_speed;
			if(pObject.speed.y<-pObject.max_speed) pObject.speed.y = -pObject.max_speed;
			//divide up path
			float segment_length = 2f;
			Vec2 trajectory = pObject.speed.mul(milliseconds);
			while(trajectory.length()>.1){
				Vec2 path_segment = (trajectory.length()>segment_length)? trajectory.mul(segment_length/trajectory.length()): trajectory.clone();
				trajectory = trajectory.sub(path_segment);
				boolean path_blocked = false;
				Rectangle path = pObject.getPath(path_segment, 1);
				ArrayList<PhysicsComponent> collidedObstacles = new ArrayList<PhysicsComponent>();
				for(PhysicsComponent obstacle: pObjects.values())
					if( obstacle!=pObject)
						if (obstacle.intersects(path)){
							collidedObstacles.add(obstacle);
							if(obstacle.blocksObject(pObject) && pObject.isSolid)
								path_blocked = true;
						}
				if(! path_blocked)
					pObject.translate(path_segment, 1);
				else{
					pObject.speed.y=0;
					path_blocked=false;
					//try again just going horizontally
					path = pObject.getPath(path_segment, 1);
					for(PhysicsComponent obstacle: pObjects.values())
						if( obstacle!=pObject)
							if (obstacle.intersects(path)){
								if(!collidedObstacles.contains(obstacle))
									collidedObstacles.add(obstacle);
								if(obstacle.blocksObject(pObject) && pObject.isSolid)
									path_blocked = true;
							}
					if(! path_blocked)
						pObject.translate(path_segment, 1);
				}
				for(PhysicsComponent obstacle:collidedObstacles){
					eventE.queue(new CharacterCollisionEvent(pObject,obstacle));
				}
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
	public PhysicsComponent createPhysicsComponent(GameObject player, int x, int y, int width, int height, boolean isDynamic, boolean isSolid) {
		PhysicsComponent physicsComponent = new PhysicsComponent(player, new Rectangle(x,y, width, height), isSolid, this);
		if (isDynamic) this.addDynamicObject(physicsComponent, x, y);
		else this.addStaticObject(physicsComponent, x, y);
		return physicsComponent;
	}
}
