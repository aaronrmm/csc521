package hw1section5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import common.EntityClass;
import common.GameObject;
import common.OscillatingController;
import common.RenderableComponent;
import hw1section5.Input.Movement;
import physics.BasicPhysicsEngine;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;
import physics.Vector2d;

public class GameEngine {
	final static int PLAYER_SPEED = 2;
	final static int JUMP_SPEED = 20;
	final static int NUMBER_OF_SPAWN_POINTS=3;
	final static boolean DEBUG_MODE = true; 
	private ArrayList<RenderableComponent>renderableList = new ArrayList<RenderableComponent>();
	private LinkedList<Rectangle>spawnPoints = new LinkedList<Rectangle>();
	
	ConcurrentHashMap<ClientHandler, GameObject> players = new ConcurrentHashMap<ClientHandler, GameObject>();
	GameObject[]obstacles;
	
	private static PhysicsEngine physics = new BasicPhysicsEngine();
	
	public void processInput(Input input){
		GameObject player = players.get(input.client);
		if(player == null){
			player = new GameObject(EntityClass.PLAYER);
			Rectangle spawnPoint = spawnPoints.removeLast();
			spawnPoints.push(spawnPoint);
			PhysicsComponent physicsComponent = new PhysicsComponent(new Rectangle(spawnPoint.x,spawnPoint.y,20,20), true);
			player.add(physicsComponent, physicsComponent.getClass().getName());
			players.put(input.client, player);
			physics.addDynamicObject(physicsComponent,spawnPoint.x,spawnPoint.y);
			RenderableComponent renderable = new RenderableComponent(physicsComponent);
			player.add(renderable, renderable.getClass().getName());
			this.addRenderableComponent(renderable);
			physicsComponent.addConstantForce(new Vector2d(0,1));
		}
		if(input.movement==Movement.right)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(PLAYER_SPEED,0));
		if(input.movement==Movement.left)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(-PLAYER_SPEED,0));
		if(input.movement==Movement.jump)
			((PhysicsComponent)player.getComponent(PhysicsComponent.class.getName())).addImpulseForce(new Vector2d(0,-JUMP_SPEED));
		
	}
	
	//todo put level info into separate object implementing some LevelDesign interface
	public void initializeLevel(int height, int width, int number_of_obstacles){
		this.obstacles = new GameObject[number_of_obstacles];
		
		//player spawn points dividing top of screen
		for(int i=0;i<NUMBER_OF_SPAWN_POINTS;i++){
			GameObject spawn = new GameObject(EntityClass.SPAWNPOINT);
			Rectangle spawnRect = new Rectangle((width-10)*i/NUMBER_OF_SPAWN_POINTS +10, 0,0,0);
			PhysicsComponent spawnPhysics = new PhysicsComponent(spawnRect, false);
			spawn.add(spawnPhysics, PhysicsComponent.class.getName());
			physics.addStaticObject(spawnPhysics, i, i);
			spawnPoints.add(spawnRect);
		}
		//moving platforms at mid-height
		int position = 0;
		for (int i = 0; i < number_of_obstacles/2; i++) {
			Rectangle rect = new Rectangle();
			rect.width = (int) (Math.random() * width *5 / number_of_obstacles);
			rect.height = (int) (Math.random() * height / number_of_obstacles);
			rect.x = position;
			rect.y = height/2-rect.height;
			obstacles[i] = new GameObject(EntityClass.BARRIER);
			PhysicsComponent physicsComponent = new PhysicsComponent(rect, true);
			obstacles[i].add(physicsComponent, PhysicsComponent.class.getName());
			OscillatingController timer = new OscillatingController(physicsComponent,physics);
			obstacles[i].add(timer, OscillatingController.class.getName());
			physics.addStaticObject(physicsComponent,rect.x, rect.y);
			RenderableComponent renderable = new RenderableComponent(physicsComponent);
			obstacles[i].add(renderable, renderable.getClass().getName());
			this.addRenderableComponent(renderable);
			position += rect.width;
		}
		
		//stationary platforms at bottom of screen
		position = 0;
		for (int i = 0; i < 2; i++) {
			Rectangle rect = new Rectangle();
			rect.width = (int) (width/3);
			rect.height = (int) (Math.random() * 3+10);
			rect.x = (width*2*i)/3;
			rect.y = height-rect.height;
			obstacles[i] = new GameObject(EntityClass.BARRIER);
			PhysicsComponent physicsComponent = new PhysicsComponent(rect, true);
			obstacles[i].add(physicsComponent, PhysicsComponent.class.getName());
			physics.addStaticObject(physicsComponent, rect.x, rect.y);
			RenderableComponent renderable = new RenderableComponent(physicsComponent);
			obstacles[i].add(renderable, renderable.getClass().getName());
			this.addRenderableComponent(renderable);
			position += rect.width;
		}
		
		//killzones at edges of screen
		for(int i=0;i<4;i++){
			GameObject killzoneBot = new GameObject(EntityClass.KILLZONE);
			Rectangle killzoneBotRect = null;
			switch(i){
			case 0: killzoneBotRect = new Rectangle(0,height-4,width,10); break;
			case 1: killzoneBotRect = new Rectangle(0,-10,width,10); break;
			case 2: killzoneBotRect = new Rectangle(-5,0,10,height); break;
			case 3: killzoneBotRect = new Rectangle(width-5,0,10,height); break;
			}
			PhysicsComponent killzoneBotP = new PhysicsComponent(killzoneBotRect){
				@Override
				public void onCollision(PhysicsComponent pObject){
					if(pObject.getGameObject().entityClass == EntityClass.PLAYER){
						Rectangle respawn = spawnPoints.removeLast();
						spawnPoints.push(respawn);
						
						pObject.getRectangle().x = respawn.x;
						pObject.getRectangle().y = respawn.y;
					}
				}
			};
			physics.addStaticObject(killzoneBotP, killzoneBotRect.x, killzoneBotRect.y);
			killzoneBot.add(killzoneBotP, PhysicsComponent.class.getName());
			if(DEBUG_MODE){
				RenderableComponent renderable = new RenderableComponent(killzoneBotP);
				this.addRenderableComponent(renderable);
				killzoneBot.add(renderable, RenderableComponent.class.getName());
			}
		}
	}

	public void tick(int delta) {
		physics.tick(delta);
	}
	
	public ArrayList<Rectangle> getRenderableList(){
		ArrayList<Rectangle>rectangles = new ArrayList<Rectangle>();
		for(RenderableComponent renderable:renderableList){
			rectangles.add(renderable.render());
		}
		return rectangles;
	}

	public void addRenderableComponent(RenderableComponent renderable) {
		this.renderableList.add(renderable);
		
	}
}
