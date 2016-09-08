package physics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class PhysicsObject {

	ArrayList<Vector2d> constantForces = new ArrayList<Vector2d>();
	ArrayList<Vector2d> impulseForces = new ArrayList<Vector2d>();
	private Rectangle shape;

	public PhysicsObject(Rectangle player){
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
	
	public boolean intersects(PhysicsObject other){
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
	
}
