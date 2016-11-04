package common.events;

import java.io.Serializable;

public abstract class AbstractEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long timestamp;
	public long id;
	public static long id_gen = 0;
	
	@Override public String toString(){
		return super.toString()+"eventID:"+id;
	}
	
	public AbstractEvent(){
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
