package common;

import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Movement;
import physics.PhysicsComponent;
import physics.Vector2d;

public class PlayerInputComponent extends AbstractComponent implements InputListener{

	final static int PLAYER_SPEED = 2;
	final static int JUMP_SPEED = 30;
	private long clientId;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public PlayerInputComponent(EventManagementEngine eventE, long clientId){
		this.listen(eventE, ClientInputEvent.class);
		this.clientId = clientId;
	}

	private void listen(EventManagementEngine eventE, Class<ClientInputEvent> class1) {
		eventE.register(this);
	}

	@Override
	public void update(ClientInputEvent input) {
		if(input.client==null)
			return;
		if(this.clientId!=input.client.getClientId())
			return;
		GameObject player = this.getGameObject();
		if(input.movement==Movement.right)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(PLAYER_SPEED,0));
		if(input.movement==Movement.left)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(-PLAYER_SPEED,0));
		if(input.movement==Movement.jump)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(0,-JUMP_SPEED));
		
		
	}
}
