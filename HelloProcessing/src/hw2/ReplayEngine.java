package hw2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.events.AbstractEvent;
import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;

public class ReplayEngine implements GenericListener<ClientInputEvent>{
	private static final Logger logger = Logger.getLogger(ReplayEngine.class.getName());
	private boolean is_recording = false;
	
	public ReplayEngine(){
		CharacterSyncEvent.Register(r->this.record(r));
		CharacterDeathEvent.Register(r->this.record(r));
		CharacterSpawnEvent.Register(r->this.record(r));
	}
	@Override
	public void update(ClientInputEvent event) {
		switch(event.command){
		case record_replay: is_recording = true;
		break;
		case play_replay: is_recording = false;
		break;
		case stop_replay: is_recording = false;
		break;
		default:
		break;
		}
	}
	
	private void record(AbstractEvent r) {
		if(is_recording){
			recording.add(r);
			System.out.println(this.getClass().getName()+" recorded event "+r);
			logger.log(Level.FINEST, this.getClass().getName()+" recorded event "+r);
		}
		
	}

	private Queue<AbstractEvent> recording = new LinkedList<AbstractEvent>();

}
