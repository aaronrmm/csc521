package synchronization;

import java.util.HashMap;
import java.util.PriorityQueue;

import common.events.AbstractEvent;
import common.events.ClientInputEvent;
import common.events.EventPriorityComparator;
import common.events.GenericListener;

public class SyncEngine implements GenericListener<ClientInputEvent>{

	public SyncEngine(){
		ClientInputEvent.registrar.Register(this);
	}
	
	private EventPriorityComparator comparator = new EventPriorityComparator();

	private HashMap<Long, PriorityQueue<AbstractEvent>> queues = new HashMap<Long, PriorityQueue<AbstractEvent>>();
	@Override
	public void update(ClientInputEvent event) {
		if(!queues.containsKey(event.clientId))
			queues.put(event.clientId, new PriorityQueue<AbstractEvent>(comparator));
		queues.get(event.clientId).add(event);
	}
}
