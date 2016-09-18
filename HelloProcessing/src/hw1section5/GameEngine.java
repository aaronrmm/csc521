package hw1section5;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import hw1section5.Input.Movement;
import physics.BasicPhysicsEngine;
import physics.PhysicsObject;
import physics.Rectangle;
import physics.Vector2d;

public class GameEngine {
	final int PLAYER_SPEED = 2;
	final int JUMP_SPEED = 4;
	private ArrayList<Rectangle>renderableList = new ArrayList<Rectangle>();
	
	ConcurrentHashMap<ClientHandler, PhysicsObject> players = new ConcurrentHashMap<ClientHandler, PhysicsObject>();
	PhysicsObject[]obstacles;
	
	private static BasicPhysicsEngine physics = new BasicPhysicsEngine();
	
	public void processInput(Input input){
		PhysicsObject player = players.get(input.client);
		if(player == null){
			player = new PhysicsObject(new Rectangle(20,20,20,20));
			players.put(input.client, player);
			physics.addObject(player);
			renderableList.add(player.getRectangle());
			player.addConstantForce(new Vector2d(0,1));
		}
		if(input.movement==Movement.right)
			player.addImpulseForce(new Vector2d(PLAYER_SPEED,0));
		if(input.movement==Movement.left)
			player.addImpulseForce(new Vector2d(-PLAYER_SPEED,0));
		if(input.movement==Movement.jump)
			player.addImpulseForce(new Vector2d(0,-JUMP_SPEED));
		
	}
	
	public void initializeLevel(int height, int width, int number_of_obstacles){
		this.obstacles = new PhysicsObject[number_of_obstacles];
		int position = 0;
		for (int i = 0; i < number_of_obstacles; i++) {
			Rectangle rect = new Rectangle();
			rect.width = (int) (Math.random() * width *5 / number_of_obstacles);
			rect.height = (int) (Math.random() * height / number_of_obstacles);
			rect.x = position;
			rect.y = height-rect.height;
			obstacles[i] = new PhysicsObject(rect);
			physics.addObject(obstacles[i]);
			renderableList.add(obstacles[i].getRectangle());
			position += rect.width;
		}
	}

	public void tick(int delta) {
		physics.tick(delta);
	}
	
	public ArrayList<Rectangle> getRenderableList(){
		return renderableList;
	}
}
