package common.events;

public interface GenericListener<T extends AbstractEvent>{
	
	void update(T event);
}
