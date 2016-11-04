package common;

import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Movement;
import common.events.GenericListener;
import physics.PhysicsComponent;
import physics.Vector2d;

public class PlayerInputComponent extends AbstractComponent implements GenericListener<ClientInputEvent>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static int PLAYER_SPEED = 2;
	final static int JUMP_SPEED = 30;
	public long clientId;
	public static transient EventManagementEngine EventE;
	@Override
	public void destroy() {
		clientId=-1;
		ClientInputEvent.Unregister(this);
		
	}
	
	public PlayerInputComponent(EventManagementEngine eventE, long clientId){
		ClientInputEvent.Register(this);
		this.clientId = clientId;
		EventE = eventE;
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
