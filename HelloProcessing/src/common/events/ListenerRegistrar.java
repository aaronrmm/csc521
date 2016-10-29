package common.events;

import java.util.LinkedList;

public class ListenerRegistrar <T extends AbstractEvent> {

	private LinkedList<GenericListener<T>> queue = new LinkedList<GenericListener<T>>();
	private LinkedList<GenericListener<T>> buffer = new LinkedList<GenericListener<T>>();

	public void Register(GenericListener<T> listener){
		buffer.add(listener);
	}

	public void UpdateListeners(T event){
		for(GenericListener<T> listener:buffer){
			queue.add(listener);
		}
		for(GenericListener<T> listener:queue){
			listener.update(event);
		}
	}
}
