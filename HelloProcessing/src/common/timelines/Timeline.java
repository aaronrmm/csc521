package common.timelines;

public class Timeline {
	
	int ticksize = 1;
	
	long getTime(){
		if(anchor == null)
			return System.nanoTime();
		else
			return (anchor.getTime() - origin)/ticksize;
	}
	
	Timeline anchor;
	long origin = 0;
	
	public Timeline(Timeline anchor, int ticksize){
		this.ticksize = ticksize;
	}
	
}
