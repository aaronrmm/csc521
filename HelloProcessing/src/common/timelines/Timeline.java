package common.timelines;

public class Timeline {
	
	static final long REALTIME_CONVERSION_RATE = 1000000;//converts to milliseconds
	protected double ticksize = 1;
	
	public long getTime(){
		if(anchor == null)
			return (int)((System.nanoTime()/REALTIME_CONVERSION_RATE-origin)/ticksize);
		else
			return (int)((anchor.getTime() - origin)/ticksize);
	}
	
	Timeline anchor;
	protected long origin = 0;
	
	public Timeline(Timeline anchor, double ticksize){
		this.anchor = anchor;
		this.ticksize = ticksize;
		if(anchor!=null)
			this.origin = anchor.getTime();
		else
			this.origin = System.nanoTime()/REALTIME_CONVERSION_RATE;
	}

	public Timeline() {
		this(null, 1);	
	}
	
	public void SetTime(long time) {
		this.origin = this.getTime()-time;
	}
}
