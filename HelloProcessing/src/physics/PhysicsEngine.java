package physics;

public interface PhysicsEngine {

	void addObject(PhysicsObject player);

	void tick(int delta);

}
