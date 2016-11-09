package common.events;

import java.util.Comparator;

public class EventPriorityComparator implements Comparator<AbstractEvent> {

	@Override
	public int compare(AbstractEvent e1, AbstractEvent e2) {
		if(e1==null)
			return 1;
		if(e2==null)
			return -1;
		if(e1.timestamp>e2.timestamp)
			return 1;
		if(e2.timestamp>e1.timestamp)
			return -1;
		if(e1.getPriority() > e2.getPriority())
			return 1;
		if(e2.getPriority() > e1.getPriority())
			return -1;
		return 0;
	}

}
