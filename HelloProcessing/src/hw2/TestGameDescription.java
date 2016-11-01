package hw2;

import java.util.HashMap;
import java.util.LinkedList;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.OscillatingController;
import common.PlayerInputComponent;
import common.RenderableComponent;
import common.RenderingEngine;
import common.events.CharacterCollisionEvent;
import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;

public class TestGameDescription implements GameDescription, GenericListener<ClientInputEvent>{

	private static final boolean DEBUG_MODE = true;
	private static final int NUMBER_OF_SPAWN_POINTS = 1;
	private static final int NUMBER_OF_MOVING_OBSTACLES = 0;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private PlayerObjectFactory playerF;
	private HashMap<Long, GameObject> playerObjects=new HashMap<Long, GameObject>();
	private LinkedList<GameObject> spawnPoints = new LinkedList<GameObject>();
	private EventManagementEngine eventE;
	
	public TestGameDescription(EventManagementEngine eventE) {
		ClientInputEvent.Register(this);
	}

	@Override
	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		this.playerF = playerF;
		this.eventE = eventE;
		
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
		for (int i = 0; i < 0; i++) {
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
					int width = 5;
					switch(i){
					case 0: killzoneBotRect = new Rectangle(0,width,width,HEIGHT-2*width); break;//left
					case 1: killzoneBotRect = new Rectangle(WIDTH-width,width,WIDTH,HEIGHT-2*width); break;//right
					case 2: killzoneBotRect = new Rectangle(width,-width,WIDTH-2*width,width); break;//top
					case 3: killzoneBotRect = new Rectangle(width,HEIGHT-width,WIDTH-2*width,width); break;//bottom
					}
					PhysicsComponent killzoneBotP = new PhysicsComponent(killzoneBotRect, physicsE){
						@Override
						public void update(CharacterCollisionEvent collision){
							if(collision.object2 == this && collision.object1.getGameObject().entityClass == EntityClass.PLAYER){
								eventE.queue(new CharacterDeathEvent(collision.object1.getGameObject()));
							}
						}
					};

					
					
					
					CharacterCollisionEvent.Register(killzoneBotP);
					physicsE.addStaticObject(killzoneBotP, killzoneBotRect.x, killzoneBotRect.y);
					killzoneBot.add(killzoneBotP, PhysicsComponent.class.getName());
					if(DEBUG_MODE){
						RenderableComponent renderable = new RenderableComponent(killzoneBotP, renderingE);
						renderingE.addObject(renderable);
						killzoneBot.add(renderable, RenderableComponent.class.getName());
					}
				}
				
				GenericListener<CharacterDeathEvent> deathListener = new GenericListener<CharacterDeathEvent>(){
					@Override
					public void update(CharacterDeathEvent event){
						GameObject character = event.character;
						if(character.alive==false)
							return;
						long clientId = ((PlayerInputComponent)character.getComponent(PlayerInputComponent.class.getName())).clientId;
						eventE.queue(new CharacterSpawnEvent(clientId, event.timestamp+2));
						event.character.destroy();
						character.alive = false;
					}
				};
				CharacterDeathEvent.Register(deathListener);
				
				GenericListener<CharacterSpawnEvent> spawnListener = new GenericListener<CharacterSpawnEvent>(){

					@Override
					public void update(CharacterSpawnEvent event) {
						GameObject respawn = spawnPoints.removeLast();
						spawnPoints.push(respawn);
						PhysicsComponent pComponent = (PhysicsComponent)respawn.getComponent(PhysicsComponent.class.getName());
						int x = pComponent.getX();
						int y = pComponent.getY();
						GameObject playerObject = spawnPlayer(x,y,event.player);
						playerObjects.put(event.player, playerObject);
					}
					
				};
				CharacterSpawnEvent.Register(spawnListener);
		
	}
	
	@Override
	public GameObject spawnPlayer(int x, int y, long clientId){
		return playerF.create(x, y, clientId);
	}

	public void update(ClientInputEvent input) {
		if(input.client==null)
			return;
		if(! playerObjects.containsKey(input.client.getClientId())){
			eventE.queue(new CharacterSpawnEvent(input.client.getClientId(), input.timestamp+1));
		}

		
	}

}
