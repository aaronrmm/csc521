package common.events;

public class ErrorEvent extends AbstractEvent{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorEvent(String message){
		
	}
	
	@Override
	public double getPriority(){
		return 0;
	}
	
	@Override
	public void Handle() {
	}

}
