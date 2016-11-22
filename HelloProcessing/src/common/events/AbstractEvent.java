package common.events;

import java.io.Serializable;

import game.Game;

public abstract class AbstractEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long timestamp;
	public long id;
	public long clientId;
	public long sceneId;
	public static long id_gen = 0;
	
	@Override public String toString(){
		return super.toString()+"eventID:"+id;
	}
	
	public AbstractEvent(){
		this.sceneId = Game.current_scene.id;
		this.id = id_gen++;
		this.timestamp = Game.eventtime.getTime()+1;
	}
	
	public AbstractEvent(int delay) {
		this();
		this.timestamp+=delay;
	}

	public abstract void Handle();
	
	public double getPriority(){
			return 0;
	}
	
	public boolean getDebug(){
		return false;
	}
}
