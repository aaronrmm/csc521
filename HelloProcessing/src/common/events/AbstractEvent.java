package common.events;

import java.io.Serializable;

import game.Game;
import game.Scene;

public abstract class AbstractEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long timestamp;
	public long id;
	public long clientId;
	public static long id_gen = 0;
	public transient Scene scene;
	
	@Override public String toString(){
		return super.toString()+"eventID:"+id;
	}
	
	public AbstractEvent(){
		this.scene = Game.current_scene;
		this.id = id_gen++;
	}
	
	public abstract void Handle();
	
	public double getPriority(){
			return 0;
	}
	
	public boolean getDebug(){
		return false;
	}
}
