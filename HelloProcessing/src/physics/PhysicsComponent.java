package physics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import common.AbstractComponent;
import common.GameObject;
import common.events.CharacterCollisionEvent;
import common.events.GenericListener;

public class PhysicsComponent extends AbstractComponent implements GenericListener<CharacterCollisionEvent>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Vec2> constantForces = new ArrayList<Vec2>();
	ArrayList<Vec2> impulseForces = new ArrayList<Vec2>();
	Vec2 speed = new Vec2(0,0);
	boolean isSolid = false;
	private Rectangle shape;
	public transient Body body;
	private transient PhysicsEngine physicsE;

	public PhysicsComponent(GameObject gameObject, Rectangle player, PhysicsEngine physicsE){
		this(gameObject, player, false, physicsE);
	}
	
	public PhysicsComponent(GameObject gameObject, Rectangle shape, boolean isSolid,  PhysicsEngine physicsE){
		super(gameObject);
		this.shape = shape;
		this.isSolid = isSolid;
		this.physicsE = physicsE;
	}

	public Rectangle getPath(Vec2 force, int milliseconds) {
		int newX = (int)(this.shape.getX()+force.x*milliseconds);
		int newY = (int)(this.shape.getY()+force.y*milliseconds);
		Rectangle path = new Rectangle(newX, newY, this.shape.width, this.shape.height);
		return path;
		
	}
	public void translate(Vec2 force, int milliseconds) {
		int newX = (int)(this.shape.getX()+force.x*milliseconds);
		int newY = (int)(this.shape.getY()+force.y*milliseconds);
		this.shape.setLocation(newX, newY);
	}
	
	public void translate(int x, int y){
		this.shape.setLocation(x,y);
	}

	public boolean intersects(Rectangle2D other){
		return shape.intersects(other);
	}
	
	public boolean intersects(PhysicsComponent other){
		return this.intersects(other.shape.getBounds2D());
	}
	

	public void addConstantForce(Vec2 vector){
		this.constantForces.add(vector);
		if(body!=null)body.applyForce(vector, body.getPosition());
	}
	public void addImpulseForce(Vec2 vector){
		this.impulseForces.add(vector);
		if(body!=null)body.applyLinearImpulse(vector, body.getPosition(), true);
	}

	public void addForceLeftRight(float force){
		this.addImpulseForce(new Vec2(force, 0));
	}
	
	public void addForceUpDown(float force){
		this.addImpulseForce(new Vec2(0, force));
	}

	public int getX(){ return body==null?shape.x:(int)body.getPosition().x; }
	public int getY(){ return body==null?shape.y:(int)body.getPosition().y; }
	public int getWidth(){ return shape.width; }
	public int getHeight(){ return shape.height; }
	public Rectangle getRectangle(){return shape; }

	@Override
	public void destroy() {
		if(physicsE!=null)
			this.physicsE.remove(this);
	}

	public boolean blocksObject(PhysicsComponent pObject) {
		if (this.isSolid) return true;
		return false;
	}


	@Override
	public void update(CharacterCollisionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void clearForces() {
		this.impulseForces.clear();
		this.constantForces.clear();
		
	}
}
