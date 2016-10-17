package common;

import hw1section5.Input;
import hw1section5.Input.Movement;
import physics.PhysicsComponent;
import physics.Vector2d;

public class PlayerInputComponent extends AbstractComponent implements InputListener{

	final static int PLAYER_SPEED = 2;
	final static int JUMP_SPEED = 3;
	private long clientId;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public PlayerInputComponent(EventManagementEngine eventE, long clientId){
		this.listen(eventE, Input.class);
		this.clientId = clientId;
	}

	private void listen(EventManagementEngine eventE, Class<Input> class1) {
		eventE.register(this);
	}

	@Override
	public void update(Input input) {
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
