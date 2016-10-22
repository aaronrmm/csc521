package common.timelines;

public class Timeline {
	
	static final long REALTIME_CONVERSION_RATE = 1000000;//converts to milliseconds
	int ticksize = 1;
	
	public long getTime(){
		if(anchor == null)
			return (System.nanoTime()/REALTIME_CONVERSION_RATE-origin)/ticksize;
		else
			return (anchor.getTime() - origin)/ticksize;
	}
	
	Timeline anchor;
	long origin = 0;
	
	public Timeline(Timeline anchor, int ticksize){
		this.ticksize = ticksize;
		if(anchor!=null)
			this.origin = anchor.getTime();
		else
			this.origin = System.nanoTime()/REALTIME_CONVERSION_RATE;
	}
	
}
