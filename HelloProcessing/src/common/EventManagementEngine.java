package common;

import java.util.HashMap;
import java.util.LinkedList;

import hw1section5.Input;

public class EventManagementEngine {

	Input queue = null;
	@SuppressWarnings("rawtypes")
	HashMap<Class, LinkedList<? extends Listener>> registeredListeners = new HashMap<Class, LinkedList<? extends Listener>>();
	public void queue(Input input) {
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
			Input input = queue;
			queue = input.nextInput;
			this.handle(input);
		}
	}
	@SuppressWarnings("unchecked")
	private void handle(Input input) {
		LinkedList<InputListener>listeners = (LinkedList<InputListener>)registeredListeners.get(InputListener.class);
		for(InputListener listener : listeners)
			listener.update(input);
	}
	public void HandleNextEvents(int i) {
		while(queue!=null && i>0){
			i--;
			Input input = queue;
			queue = input.nextInput;
			this.handle(input);
			
		}
		
	}
	

}
