package common.events;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListenerRegistrar <T extends AbstractEvent> {
	private static Logger logger = Logger.getLogger(ListenerRegistrar.class.getName());

	private LinkedList<GenericListener<T>> queue = new LinkedList<GenericListener<T>>();
	private LinkedList<GenericListener<T>> buffer = new LinkedList<GenericListener<T>>();
	private LinkedList<GenericListener<T>> removeBuffer = new LinkedList<GenericListener<T>>();

	public void Register(GenericListener<T> listener){
		buffer.add(listener);
	}

	public void UpdateListeners(T event){
		if(event.getDebug())
			logger.log(Level.SEVERE,"handling "+event.toString());
		for(GenericListener<T> listener:removeBuffer){
			queue.remove(listener);
		}
		for(GenericListener<T> listener:buffer){
			queue.add(listener);
		}
		buffer.clear();
		for(GenericListener<T> listener:queue){
			listener.update(event);
		}
	}

	public void Unregister(GenericListener<T> listener) {
		removeBuffer.add(listener);
	}
}
