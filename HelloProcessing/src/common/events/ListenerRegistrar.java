package common.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListenerRegistrar <T extends AbstractEvent> {
	private static Logger logger = Logger.getLogger(ListenerRegistrar.class.getName());

	private HashMap<Long, LinkedList<GenericListener<T>>> queue = new HashMap<Long, LinkedList<GenericListener<T>>>();
	private HashMap<Long, LinkedList<GenericListener<T>>> buffer = new HashMap<Long, LinkedList<GenericListener<T>>>();
	private HashMap<Long, LinkedList<GenericListener<T>>> removeBuffer = new HashMap<Long, LinkedList<GenericListener<T>>>();

	public ListenerRegistrar(){
		this.queue.put(-1L, new LinkedList<GenericListener<T>>());
		this.buffer.put(-1L, new LinkedList<GenericListener<T>>());
		this.removeBuffer.put(-1L, new LinkedList<GenericListener<T>>());
	}
	public void Register(GenericListener<T> listener){
		this.Register(-1L, listener);
	}
	
	public void Register(Long sceneId, GenericListener<T> listener){
		Long scene_id = (sceneId==null)? -1L : sceneId;
		if (!buffer.containsKey(scene_id))
			buffer.put(scene_id, new LinkedList<GenericListener<T>>());
		buffer.get(scene_id).add(listener);
	}

	public void UpdateListeners(T event){
		if(event.sceneId > -1){
			if(!this.queue.containsKey(event.sceneId))
			this.queue.put(event.sceneId, new LinkedList<GenericListener<T>>());
			if(!this.buffer.containsKey(event.sceneId))
			this.buffer.put(event.sceneId, new LinkedList<GenericListener<T>>());
			if(!this.removeBuffer.containsKey(event.sceneId))
			this.removeBuffer.put(event.sceneId, new LinkedList<GenericListener<T>>());
			this.UpdateListeners(event.sceneId, event);
		}
		this.UpdateListeners(-1L, event);
	}
	private void UpdateListeners(Long sceneId, T event){
		if(event.getDebug())
			logger.log(Level.FINEST,"handling "+event.toString());
		for(GenericListener<T> listener:removeBuffer.get(sceneId)){
			queue.remove(listener);
		}
		removeBuffer.get(sceneId).clear();
		for(GenericListener<T> listener:buffer.get(sceneId)){
			queue.get(sceneId).add(listener);
		}
		buffer.get(sceneId).clear();
		for(GenericListener<T> listener:queue.get(sceneId)){
			listener.update(event);
		}
	}

	public void Unregister(Long sceneId, GenericListener<T> listener) {
		if(removeBuffer.containsKey(sceneId))
			removeBuffer.get(sceneId).add(listener);
	}
	public void Unregister(GenericListener<T> listener) {
		removeBuffer.get(-1L).add(listener);
		
	}
}
