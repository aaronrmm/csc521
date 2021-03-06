package common;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import common.events.AbstractEvent;
import common.events.EventPriorityComparator;
import common.events.TimeUpdateEvent;
import common.timelines.Timeline;
import game.Game;

public class EventManagementEngineGVT extends EventManagementEngine{
	private static final Logger logger = Logger.getLogger(EventManagementEngineGVT.class.getName());

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
			
			@Override
			public void SetTime(long time){
				timestamp = time;
			}
		};
		this.anchor = anchor;
	}
	
	
	long timestamp=0;
	Timeline anchor;
	
	
	private EventPriorityComparator comparator = new EventPriorityComparator();

	private HashMap<Long, PriorityQueue<AbstractEvent>> buffers = new HashMap<Long, PriorityQueue<AbstractEvent>>();
	
	public void queue(AbstractEvent event) {
		if(!buffers.containsKey(event.clientId)){
			buffers.put(event.clientId, new PriorityQueue<AbstractEvent>(comparator));
			queue(new TimeUpdateEvent(Game.eventtime.getTime(), event.clientId));
		}
		buffers.get(event.clientId).add(event);
	}
	
	public void HandleNextEvents(int i) {
		logger.finest("TIME: "+timestamp);
		for(Long bufferId: buffers.keySet()){
			PriorityQueue<AbstractEvent>buffer = buffers.get(bufferId);
			logger.finest(bufferId+":");
			if(buffer.peek()==null)
				logger.finest(buffer.peek()+"");
			else
				logger.finest(buffer.peek().timestamp+"");
			while(buffer.peek()!=null && buffer.peek().timestamp<=Game.eventtime.getTime()){
				AbstractEvent next = buffer.poll();
				queue.offer(next);
				
			}
		}
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
