package physics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import common.AbstractComponent;
import common.events.CharacterCollisionEvent;
import common.events.GenericListener;

public class PhysicsComponent extends AbstractComponent implements GenericListener<CharacterCollisionEvent>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Vector2d> constantForces = new ArrayList<Vector2d>();
	ArrayList<Vector2d> impulseForces = new ArrayList<Vector2d>();
	Vector2d speed = new Vector2d(0,0);
	boolean isSolid = false;
	private Rectangle shape;
	public transient Body body;
	private transient PhysicsEngine physicsE;

	public PhysicsComponent(Rectangle player, PhysicsEngine physicsE){
		this(player, false, physicsE);
	}
	
	public PhysicsComponent(Rectangle shape, boolean isSolid,  PhysicsEngine physicsE){
		this.shape = shape;
		this.isSolid = isSolid;
		this.physicsE = physicsE;
	}

	public Rectangle getPath(Vector2d force, int milliseconds) {
		int newX = (int)(this.shape.getX()+force.x*milliseconds);
		int newY = (int)(this.shape.getY()+force.y*milliseconds);
		Rectangle path = new Rectangle(newX, newY, this.shape.width, this.shape.height);
		return path;
		
	}
	public void translate(Vector2d force, int milliseconds) {
		int newX = (int)(this.shape.getX()+force.x*milliseconds);
		int newY = (int)(this.shape.getY()+force.y*milliseconds);
		this.shape.setLocation(newX, newY);
	}

	public boolean intersects(Rectangle2D other){
		return shape.intersects(other);
	}
	
	public boolean intersects(PhysicsComponent other){
		return this.intersects(other.shape.getBounds2D());
	}
	

	public void addConstantForce(Vector2d vector){
		this.constantForces.add(vector);
		if(body!=null)body.applyForce(vector, body.getPosition());
	}
	public void addImpulseForce(Vector2d vector){
		this.impulseForces.add(vector);
		if(body!=null)body.applyLinearImpulse(vector, body.getPosition());
	}

	public int getX(){ return body==null?shape.x:(int)body.getPosition().x; }
	public int getY(){ return body==null?shape.y:(int)body.getPosition().y; }
	public int getWidth(){ return shape.width; }
	public int getHeight(){ return shape.height; }
	public Rectangle getRectangle(){return shape; }

	@Override
	public void destroy() {
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
}
