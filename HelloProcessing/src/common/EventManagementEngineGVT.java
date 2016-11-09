package common;

import java.util.HashMap;
import java.util.PriorityQueue;

import common.events.AbstractEvent;
import common.events.EventPriorityComparator;
import common.timelines.Timeline;
import game.Game;

public class EventManagementEngineGVT extends EventManagementEngine{

	PriorityQueue<AbstractEvent> queue = new PriorityQueue<AbstractEvent>(new EventPriorityComparator());
	
	public EventManagementEngineGVT(Timeline anchor){
		super(anchor);
		Game.eventtime = new Timeline(){
			@Override
			public long getTime(){
				long minimum = anchor.getTime();
				for(PriorityQueue<AbstractEvent> queue: buffers.values()){
					if(queue.peek()==null){
						return timestamp;
						
					}
					if(queue.peek().timestamp<minimum)
						minimum = timestamp>queue.peek().timestamp?timestamp:queue.peek().timestamp;
				}
				timestamp = minimum;
				return minimum;
			}
		};
		this.anchor = anchor;
	}
	
	
	long timestamp=0;
	Timeline anchor;
	
	
	private EventPriorityComparator comparator = new EventPriorityComparator();

	private HashMap<Long, PriorityQueue<AbstractEvent>> buffers = new HashMap<Long, PriorityQueue<AbstractEvent>>();
	
	public void queue(AbstractEvent event) {
		if(!buffers.containsKey(event.clientId))
			buffers.put(event.clientId, new PriorityQueue<AbstractEvent>(comparator));
		buffers.get(event.clientId).add(event);
	}
	
	public void HandleNextEvents(int i) {
		System.out.println("TIME: "+timestamp);
		for(Long bufferId: buffers.keySet()){
			PriorityQueue<AbstractEvent>buffer = buffers.get(bufferId);
			System.out.print(bufferId+":");
			if(buffer.peek()==null)
				System.out.print(buffer.peek());
			else
				System.out.print(buffer.peek().timestamp);
			while(buffer.peek()!=null && buffer.peek().timestamp<=Game.eventtime.getTime()){
				AbstractEvent next = buffer.poll();
				queue.offer(next);
				
			}
			System.out.println();
		}
		System.out.println();
		while(!queue.isEmpty() && i>0){
			i--;
			AbstractEvent e = queue.peek();
			if(e.timestamp>Game.eventtime.getTime())
				break;
			e = queue.poll();
			if (e!=null){
				e.Handle();
			}
		}
	}
}
