package synchronization;

import java.util.HashMap;
import java.util.PriorityQueue;

import common.events.AbstractEvent;
import common.events.ClientInputEvent;
import common.events.EventPriorityComparator;
import common.events.GenericListener;
import common.timelines.Timeline;

public class SyncEngine extends Timeline implements GenericListener<ClientInputEvent>{

	public SyncEngine(Timeline anchor){
		
		ClientInputEvent.registrar.Register(this);
		this.anchor = anchor;

	}
	
	long timestamp=0;
	Timeline anchor;
	
	@Override
	public long getTime(){
		long minimum = this.anchor.getTime();
		for(PriorityQueue<AbstractEvent> queue: queues.values()){
			if(queue.peek().timestamp<minimum)
				minimum = queue.peek().timestamp;
		}
		timestamp = minimum;
		System.out.println(timestamp);
		return minimum;
	}
	
	private EventPriorityComparator comparator = new EventPriorityComparator();

	private HashMap<Long, PriorityQueue<AbstractEvent>> queues = new HashMap<Long, PriorityQueue<AbstractEvent>>();
	@Override
	public void update(ClientInputEvent event) {
		if(!queues.containsKey(event.clientId))
			queues.put(event.clientId, new PriorityQueue<AbstractEvent>(comparator));
		queues.get(event.clientId).add(event);
	}
	
	public Timeline gvt;
}
