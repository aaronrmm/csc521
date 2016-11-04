package common.events;

public interface GenericListener<T extends AbstractEvent>{
	
	public void update(T event);
}
