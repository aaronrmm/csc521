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
		world = new World(null, false);
	}
	@Override
	public void addObject(PhysicsComponent player) {
		BodyDef bodyD = new BodyDef();
		bodyD.userData = player;
		bodyD.position = new Vec2(player.getX(), player.getY());
		Body body = this.world.createBody(bodyD);
		objectMap.put(player, body);
		
	}

	@Override
	public void tick(int delta) {
		world.step((float)delta, 1, 1);
		
	}
	@Override
	public void registerTimer(TimingComponent timer) {
		// TODO Auto-generated method stub
		
	}

}
