package physics;

import common.GameObject;
import common.TimingComponent;

public interface PhysicsEngine {

	void tick(int delta);

	void registerTimer(TimingComponent timer);

	void addStaticObject(PhysicsComponent physicsComponent, int x, int y);

	void addDynamicObject(PhysicsComponent physicsComponent, int x, int y);

	void remove(PhysicsComponent physicsComponent);

	PhysicsComponent createPhysicsComponent(GameObject player, int x, int y, int width, int height, boolean isDynamic);

}
