package common.timelines;

public class Timeline {
	
	int ticksize = 1;
	
	long getTime(){
		return System.nanoTime();
	}
	
	Timeline anchor;
	long origin = 0;
	
	public Timeline(Timeline anchor, int ticksize){
		this.ticksize = ticksize;
	}
	
}
