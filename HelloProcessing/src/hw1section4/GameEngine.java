package hw1section4;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import physics.BasicPhysicsEngine;
import physics.PhysicsObject;
import physics.Vector2d;

public class GameEngine {
	final int PLAYER_SPEED = 1;
	final int JUMP_SPEED = 2;
	private ArrayList<Rectangle>drawables = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> getDrawables(){
		drawables.clear();
		for(PhysicsObject physicsO : players.values())
			drawables.add(physicsO.getRectangle());
		for(int i=0;i<obstacles.length;i++)
			drawables.add(obstacles[i].getRectangle());
		return drawables;
	}
	
	HashMap<ClientHandler, PhysicsObject> players = new HashMap<ClientHandler, PhysicsObject>();
	PhysicsObject[]obstacles;
	
	private static BasicPhysicsEngine physics = new BasicPhysicsEngine();
	
	public void processInput(Input input){
		PhysicsObject player = players.get(input.client);
		if(player == null){
			player = new PhysicsObject(new Rectangle(20,20,20,20));
			players.put(input.client, player);
			physics.addObject(player);
			player.addConstantForce(new Vector2d(0,1));
		}
		player.addImpulseForce(new Vector2d(0, -JUMP_SPEED));
		
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
			position += rect.width;
		}
	}

	public void tick(int delta) {
		physics.tick(delta);
	}
}
