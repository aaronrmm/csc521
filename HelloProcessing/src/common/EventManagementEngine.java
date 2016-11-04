package common;

import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import common.events.AbstractEvent;
import common.events.EventPriorityComparator;
import common.timelines.Timeline;

public class EventManagementEngine {

	Timeline timeline;
	PriorityQueue<AbstractEvent> queue = new PriorityQueue<AbstractEvent>(new EventPriorityComparator());
	ConcurrentLinkedQueue<AbstractEvent> buffer = new ConcurrentLinkedQueue<AbstractEvent>();
	
	public EventManagementEngine(Timeline timeline){
		this.timeline = timeline;
	}
	
	public void queue(AbstractEvent event) {
		//event.timestamp = this.timeline.getTime();
		buffer.add(event);
	}
	
	public void HandleNextEvents(int i) {
		for(AbstractEvent e : buffer)
			queue.offer(e);
		buffer.clear();
		while(!queue.isEmpty() && i>0){
			i--;
			AbstractEvent e = queue.poll();
			if (e!=null){
			e.Handle();
			}
		}
	}
}
