package physics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import common.Component;

public class PhysicsComponent implements Component {

	ArrayList<Vector2d> constantForces = new ArrayList<Vector2d>();
	ArrayList<Vector2d> impulseForces = new ArrayList<Vector2d>();
	Vector2d speed = new Vector2d(0,0);
	private Rectangle shape;

	public PhysicsComponent(Rectangle player){
		this.shape = player;
	}

	public Rectangle getPath(Vector2d force, int milliseconds) {
		int newX = (int)this.shape.getX()+force.x*milliseconds;
		int newY = (int)this.shape.getY()+force.y*milliseconds;
		Rectangle path = new Rectangle(newX, newY, this.shape.width, this.shape.height);
		return path;
		
	}
	public void translate(Vector2d force, int milliseconds) {
		int newX = (int)this.shape.getX()+force.x*milliseconds;
		int newY = (int)this.shape.getY()+force.y*milliseconds;
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
	}
	public void addImpulseForce(Vector2d vector){
		this.impulseForces.add(vector);
	}

	public int getX(){ return shape.x; }
	public int getY(){ return shape.y; }
	public int getWidth(){ return shape.width; }
	public int getHeight(){ return shape.height; }
	public Rectangle getRectangle(){return shape; }

	@Override
	public void destroy() {
		
	}
}
