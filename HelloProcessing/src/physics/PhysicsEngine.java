package physics;

public interface PhysicsEngine {

	void addObject(PhysicsComponent player);

	void tick(int delta);

}
