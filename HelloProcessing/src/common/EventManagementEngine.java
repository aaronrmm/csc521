package common;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;

import common.events.ClientInputEvent;

public class EventManagementEngine {

	ClientInputEvent queue = null;
	@SuppressWarnings("rawtypes")
	HashMap<Class, LinkedList<? extends Listener>> registeredListeners = new HashMap<Class, LinkedList<? extends Listener>>();
	public void queue(ClientInputEvent input) {
		if (queue == null)
			queue = input;
		else
			queue.nextInput = input;
	}
	public void register(InputListener playerInputComponent) {
		if(!registeredListeners.containsKey(InputListener.class))
				registeredListeners.put(InputListener.class, new LinkedList<InputListener>());
		@SuppressWarnings("unchecked")
		LinkedList<InputListener> list = (LinkedList<InputListener>)registeredListeners.get(InputListener.class);
		list.add(playerInputComponent);
	}
	
	public void HandleNextEvent(){
		if (queue!=null){
			ClientInputEvent input = queue;
			queue = input.nextInput;
			this.handle(input);
		}
	}
	@SuppressWarnings("unchecked")
	private void handle(ClientInputEvent input) {
		LinkedList<InputListener>listeners = (LinkedList<InputListener>)registeredListeners.get(InputListener.class);
		if(listeners!=null){
			try{
				for(InputListener listener: listeners){
					listener.update(input);
				}
			}catch(ConcurrentModificationException ex){
				ex.printStackTrace();
			}
		}
	}
	public void HandleNextEvents(int i) {
		while(queue!=null && i>0){
			i--;
			ClientInputEvent input = queue;
			queue = input.nextInput;
			this.handle(input);
			
		}
		
	}
	

}
