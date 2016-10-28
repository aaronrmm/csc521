package common;

import common.events.ClientInputEvent;

public interface InputListener extends Listener{

	void update(ClientInputEvent input);

}
