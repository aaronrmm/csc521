package physics;
import java.util.ArrayList;

public class PhysicsObject {

	ArrayList<Vector2d> constantForces = new ArrayList<Vector2d>();
	int x;
	int y;
	
	public void translate(Vector2d force, int milliseconds) {
		x+=force.x*milliseconds;
		y+=force.y*milliseconds;
	}
}
