package hw1section5;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import common.GameObject;
import common.OscillatingController;
import hw1section5.Input.Movement;
import physics.BasicPhysicsEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;
import physics.Vector2d;

public class GameEngine {
	final int PLAYER_SPEED = 2;
	final int JUMP_SPEED = 4;
	private ArrayList<Rectangle>renderableList = new ArrayList<Rectangle>();
	
	ConcurrentHashMap<ClientHandler, GameObject> players = new ConcurrentHashMap<ClientHandler, GameObject>();
	GameObject[]obstacles;
	
	private static PhysicsEngine physics = new BasicPhysicsEngine();
	
	public void processInput(Input input){
		GameObject player = players.get(input.client);
		if(player == null){
			player = new GameObject();
			PhysicsComponent physicsComponent = new PhysicsComponent(new Rectangle(0,0,20,20));
			player.add(physicsComponent, physicsComponent.getClass().getName());
			players.put(input.client, player);
			physics.addObject(physicsComponent);
			renderableList.add(physicsComponent.getRectangle());
			physicsComponent.addConstantForce(new Vector2d(0,1));
		}
		if(input.movement==Movement.right)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(PLAYER_SPEED,0));
		if(input.movement==Movement.left)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(-PLAYER_SPEED,0));
		if(input.movement==Movement.jump)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(0,-JUMP_SPEED));
		
	}
	
	public void initializeLevel(int height, int width, int number_of_obstacles){
		this.obstacles = new GameObject[number_of_obstacles];
		
		//moving platforms at mid-height
		int position = 0;
		for (int i = 0; i < number_of_obstacles/2; i++) {
			Rectangle rect = new Rectangle();
			rect.width = (int) (Math.random() * width *5 / number_of_obstacles);
			rect.height = (int) (Math.random() * height / number_of_obstacles);
			rect.x = position;
			rect.y = height/2-rect.height;
			obstacles[i] = new GameObject();
			PhysicsComponent physicsComponent = new PhysicsComponent(rect);
			obstacles[i].add(physicsComponent, PhysicsComponent.class.getName());
			OscillatingController timer = new OscillatingController(physicsComponent);
			obstacles[i].add(timer, OscillatingController.class.getName());
			physics.registerTimer(timer);
			
			physics.addObject(physicsComponent);
			renderableList.add(physicsComponent.getRectangle());
			position += rect.width;
		}
		
		//stationary platforms at bottom of screen
		position = 0;
		for (int i = 0; i < number_of_obstacles/2; i++) {
			Rectangle rect = new Rectangle();
			rect.width = (int) (Math.random() * width *5 / number_of_obstacles);
			rect.height = (int) (Math.random() * height / number_of_obstacles);
			rect.x = position;
			rect.y = height-rect.height;
			obstacles[i] = new GameObject();
			PhysicsComponent physicsComponent = new PhysicsComponent(rect);
			obstacles[i].add(physicsComponent, PhysicsComponent.class.getName());
			physics.addObject(physicsComponent);
			renderableList.add(physicsComponent.getRectangle());
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
