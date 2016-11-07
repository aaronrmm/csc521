package common;

import org.jbox2d.common.Vec2;

import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Command;
import common.events.GenericListener;
import physics.PhysicsComponent;

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
		ClientInputEvent.registrar.Unregister(this);
		
	}
	
	public PlayerInputComponent(GameObject gameObject, EventManagementEngine eventE, long clientId){
		super(gameObject);
		ClientInputEvent.registrar.Register(this);
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
		if(input.command==Command.right)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class)).addImpulseForce(new Vec2(PLAYER_SPEED,0));
		if(input.command==Command.left)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class)).addImpulseForce(new Vec2(-PLAYER_SPEED,0));
		if(input.command==Command.jump)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class)).addImpulseForce(new Vec2(0,-JUMP_SPEED));
		
		
	}
}
