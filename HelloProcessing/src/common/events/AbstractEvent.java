package common.events;

public abstract class AbstractEvent{

	public long timestamp;
	
	public abstract void Handle();
	
	public double getPriority(){
			return 0;
	}
	
	public boolean getDebug(){
		return false;
	}
}
