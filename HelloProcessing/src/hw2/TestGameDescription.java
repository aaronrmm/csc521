package hw2;

import java.util.HashMap;
import java.util.LinkedList;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.InputListener;
import common.OscillatingController;
import common.RenderableComponent;
import common.RenderingEngine;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import hw1section5.Input;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class TestGameDescription implements GameDescription, InputListener{

	private static final boolean DEBUG_MODE = true;
	private static final int NUMBER_OF_SPAWN_POINTS = 3;
	private static final int NUMBER_OF_MOVING_OBSTACLES = 10;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private PlayerObjectFactory playerF;
	private HashMap<Long, GameObject> playerObjects=new HashMap<Long, GameObject>();
	private LinkedList<GameObject> spawnPoints = new LinkedList<GameObject>();
	
	public TestGameDescription(EventManagementEngine eventE) {
		eventE.register(this);
	}

	@Override
	public void generateGame(RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		this.playerF = playerF;
		
		//player spawn points dividing top of screen
		for(int i=0;i<NUMBER_OF_SPAWN_POINTS;i++){
			GameObject spawn = spawnF.create((WIDTH-10)*i/NUMBER_OF_SPAWN_POINTS +10, 0,0,0);
			spawnPoints.add(spawn);
		}
		//moving platforms at mid-height
		int position = 0;
		for (int i = 0; i < NUMBER_OF_MOVING_OBSTACLES/2; i++) {
			int width = (int) (Math.random() * WIDTH *5 / NUMBER_OF_MOVING_OBSTACLES);
			int height = (int) (Math.random() * HEIGHT / NUMBER_OF_MOVING_OBSTACLES);
			int x = position;
			int y = HEIGHT/2-height;
			GameObject movingP = platformF.create(x, y, width, height);
			movingP.add(new OscillatingController((PhysicsComponent)movingP.getComponent(PhysicsComponent.class.getName()), physicsE), OscillatingController.class.getName());
			position += width;
		}
		
		//stationary platforms at bottom of screen
		position = 0;
		for (int i = 0; i < 2; i++) {
			int width = (int) (WIDTH/3);
			int height = (int) (Math.random() * 3+10);
			int x = (WIDTH*2*i)/3;
			int y = HEIGHT-height;
			platformF.create(x, y, width, height);
			position += width;
		}
		
		//killzones at edges of screen
				for(int i=0;i<4;i++){
					GameObject killzoneBot = new GameObject(EntityClass.KILLZONE);
					Rectangle killzoneBotRect = null;
					switch(i){
					case 0: killzoneBotRect = new Rectangle(0,HEIGHT-4,WIDTH,10); break;
					case 1: killzoneBotRect = new Rectangle(0,-10,WIDTH,10); break;
					case 2: killzoneBotRect = new Rectangle(-5,0,10,HEIGHT); break;
					case 3: killzoneBotRect = new Rectangle(WIDTH-5,0,10,HEIGHT); break;
					}
					PhysicsComponent killzoneBotP = new PhysicsComponent(killzoneBotRect){
						@Override
						public void onCollision(PhysicsComponent pObject){
							if(pObject.getGameObject().entityClass == EntityClass.PLAYER){
								GameObject respawn = spawnPoints.removeLast();
								spawnPoints.push(respawn);
								PhysicsComponent pComponent = (PhysicsComponent)respawn.getComponent(PhysicsComponent.class.getName());
								pObject.getRectangle().x = pComponent.getX();
								pObject.getRectangle().y = pComponent.getY();
							}
						}
					};
					physicsE.addStaticObject(killzoneBotP, killzoneBotRect.x, killzoneBotRect.y);
					killzoneBot.add(killzoneBotP, PhysicsComponent.class.getName());
					if(DEBUG_MODE){
						RenderableComponent renderable = new RenderableComponent(killzoneBotP);
						renderingE.addObject(renderable);
						killzoneBot.add(renderable, RenderableComponent.class.getName());
					}
				}
		
	}
	
	@Override
	public GameObject spawnPlayer(int x, int y, long clientId){
		return playerF.create(x, y, clientId);
	}

	@Override
	public void update(Input input) {
		if(input.client==null)
			return;
		if(! playerObjects.containsKey(input.client.getClientId())){
			GameObject playerObject = spawnPlayer(0,0,input.client.getClientId());
			playerObjects.put(input.client.getClientId(), playerObject);
		}

		
	}

}
