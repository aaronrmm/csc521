package hw2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.events.AbstractEvent;
import common.events.Event;
import common.events.GenericListener;
import common.events.ReplayEvent;

public class ReplayEngine implements GenericListener<ReplayEvent>{
	private static final Logger logger = Logger.getLogger(ReplayEngine.class.getName());
	private boolean is_recording = false;
	
	public ReplayEngine(){
		Event.Register(r->this.record(r));
	}
	@Override
	public void update(ReplayEvent event) {
		switch(event.command){
		case RECORD: is_recording = true;
		break;
		case PLAY: is_recording = false;
		break;
		case STOP: is_recording = false;
		}
	}
	
	private void record(Event r) {
		if(is_recording){
			recording.add(r);
			logger.log(Level.FINE, this.getClass().getName()+" recorded event "+r);
		}
		
	}

	private Queue<AbstractEvent> recording = new LinkedList<AbstractEvent>();

}
