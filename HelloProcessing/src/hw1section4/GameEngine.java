package hw1section4;

import java.awt.Rectangle;
import java.util.HashMap;

import physics.BasicPhysicsEngine;
import physics.PhysicsObject;
import physics.Vector2d;

public class GameEngine {
	final int PLAYER_SPEED = 1;
	final int JUMP_SPEED = 20;
	
	HashMap<ClientHandler, PhysicsObject> players = new HashMap<ClientHandler, PhysicsObject>();
	
	private static BasicPhysicsEngine physics = new BasicPhysicsEngine();
	
	public void processInput(Input input){
		PhysicsObject player = players.get(input.client);
		if(player == null){
			player = new PhysicsObject(new Rectangle());
			players.put(input.client, player);
		}
		player.addImpulseForce(new Vector2d(0, -JUMP_SPEED));
		
	}

	public void tick(int delta) {
		physics.tick(delta);
	}
}
