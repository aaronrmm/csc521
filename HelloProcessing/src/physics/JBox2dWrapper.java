package physics;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import common.TimingComponent;


public class JBox2dWrapper implements PhysicsEngine{

	private World world;
	private HashMap<PhysicsComponent, Body> objectMap = new HashMap<PhysicsComponent,Body>();
	
	public JBox2dWrapper(){
	    world = new World(new Vec2(0.0f, -0.01f), true);
	}
	@Override
	public void addStaticObject(PhysicsComponent physicsComponent, int x, int y) {
		BodyDef bodyD = new BodyDef();
		bodyD.userData = physicsComponent;
		bodyD.position = new Vec2(x, y);
		Body body = this.world.createBody(bodyD);
		physicsComponent.body = body;
		objectMap.put(physicsComponent, body);
	}

	@Override
	public void addDynamicObject(PhysicsComponent physicsComponent, int x, int y) {
		BodyDef bodyD = new BodyDef();
		bodyD.userData = physicsComponent;
		bodyD.position = new Vec2(x, y);
		Body body = this.world.createBody(bodyD);
		physicsComponent.body = body;
		objectMap.put(physicsComponent, body);
	}
	

	@Override
	public void tick(int delta) {
		world.step((float)delta, 1, 1);
		for (Body body : objectMap.values()){
			System.out.print("("+body.getPosition().x + ", "+body.getPosition().y+")");
		}
		System.out.println("");
		
	}
	@Override
	public void registerTimer(TimingComponent timer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void remove(PhysicsComponent physicsComponent) {
		this.world.destroyBody(physicsComponent.body);
	}

}
