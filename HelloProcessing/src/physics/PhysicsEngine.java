package physics;

import common.TimingComponent;

public interface PhysicsEngine {

	void addObject(PhysicsComponent player);

	void tick(int delta);

	void registerTimer(TimingComponent timer);

}
