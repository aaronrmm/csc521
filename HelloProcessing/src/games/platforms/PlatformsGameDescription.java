package games.platforms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.EntityClass;
import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.RenderableComponent;
import common.RenderingEngine;
import common.ScriptComponent;
import common.events.CharacterCollisionEvent;
import common.events.CharacterDeathEvent;
import common.events.CharacterSpawnEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;
import common.timelines.Timeline;
import game.Game;
import physics.PhysicsComponent;
import physics.PhysicsEngine;
import physics.Rectangle;
import scripting.ScriptManager;

public class PlatformsGameDescription implements GameDescription, GenericListener<ClientInputEvent>{

	private static final Logger logger = Logger.getLogger(PlatformsGameDescription.class.getName());
	private static final boolean DEBUG_MODE = true;
	private static final int NUMBER_OF_SPAWN_POINTS = 2;
	private static final int NUMBER_OF_MOVING_OBSTACLES = 30;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private PlayerObjectFactory playerF;
	private HashMap<Long, GameObject> playerObjects=new HashMap<Long, GameObject>();
	private LinkedList<GameObject> spawnPoints = new LinkedList<GameObject>();
	private ConcurrentLinkedQueue <GameObject> gameObjects = new ConcurrentLinkedQueue<GameObject>();
	private EventManagementEngine eventE;
	
	public PlatformsGameDescription() {
	}

	public void generateGame(EventManagementEngine eventE, RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF, PlatformObjectFactory platformF, SpawnPointFactory spawnF) {
		this.playerF = playerF;
		this.eventE = eventE;
		ClientInputEvent.registrar.Register(this);
		Timeline game_time = new Timeline(Game.eventtime, 1);
		ScriptManager.bindArgument("time", game_time);
		
		//player spawn points dividing top of screen
		for(int i=0;i<NUMBER_OF_SPAWN_POINTS;i++){
			GameObject spawn = spawnF.create((WIDTH-10)*i/NUMBER_OF_SPAWN_POINTS +10, 0,0,0);
			spawnPoints.add(spawn);
		}
		//moving platforms at mid-height
		for (int i = 0; i < NUMBER_OF_MOVING_OBSTACLES/2; i++) {
			int width = (int) (Math.random() * WIDTH *1 / NUMBER_OF_MOVING_OBSTACLES)+30;
			int height = 3;
			int x = (int)(Math.random() * WIDTH/2+5);
			int y = -height+(int)(Math.random()*HEIGHT);
			GameObject movingP = platformF.create(x, y, width, height);
			movingP.setProperty("originX", x);
			movingP.setProperty("originY", y);
			movingP.renderingC.setColor(new int[]{110,0,120,255});
			movingP.setProperty("seed",(int)(Math.random()*300));
			ScriptComponent script = new ScriptComponent("update_platform", movingP, movingP);
			movingP.add(script);
			ScriptManager.addScriptComponent(script);
			movingP.networked = true;
			gameObjects.add(movingP);
		}
		
		//stationary platforms at bottom of screen
		for (int i = 0; i < 2; i++) {
			int width = (int) (WIDTH/3);
			int height = (int) (Math.random() * 3+10);
			int x = (WIDTH*2*i)/3;
			int y = HEIGHT-height;
			GameObject platform = platformF.create(x, y, width, height);
			platform.networked = true;
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
					PhysicsComponent killzoneBotP = new PhysicsComponent(killzoneBot, killzoneBotRect, physicsE);
					killzoneBotP.setGameObject(killzoneBot);
					
					CharacterCollisionEvent.registrar.Register(killzoneBotP);
					physicsE.addStaticObject(killzoneBotP, killzoneBotRect.x, killzoneBotRect.y);
					killzoneBot.add(killzoneBotP);
					if(DEBUG_MODE){
						RenderableComponent renderable = new RenderableComponent(killzoneBot, killzoneBotP, renderingE);
						renderingE.addObject(renderable);
						killzoneBot.add(renderable);
					}
				}

				CharacterCollisionEvent.registrar.Register(new GenericListener<CharacterCollisionEvent>(){
					@Override
					public void update(CharacterCollisionEvent event) {
						ScriptManager.executeScript("on_collision", event);
					}
				});
				
				GenericListener<CharacterDeathEvent> deathListener = new GenericListener<CharacterDeathEvent>(){
					@Override
					public void update(CharacterDeathEvent event){
						GameObject character = event.character;
						long clientId = (long) character.getProperty("clientId");
						eventE.queue(new CharacterSpawnEvent(clientId, event.timestamp+2));
						event.character.destroy();
						character.alive = false;
					}
				};
				CharacterDeathEvent.registrar.Register(0L,deathListener);
				
				GenericListener<CharacterSpawnEvent> spawnListener = new GenericListener<CharacterSpawnEvent>(){

					@Override
					public void update(CharacterSpawnEvent event) {
						GameObject respawn = spawnPoints.removeLast();
						spawnPoints.push(respawn);
						PhysicsComponent pComponent = (PhysicsComponent)respawn.getComponent(PhysicsComponent.class);
						int x = pComponent.getX();
						int y = pComponent.getY();
						GameObject playerObject = spawnPlayer(x,y,event.player);
						playerObjects.put(event.player, playerObject);
					}
					
				};
				CharacterSpawnEvent.registrar.Register(0L,spawnListener);
	}
	
	public GameObject spawnPlayer(int x, int y, long clientId){
		GameObject player = playerF.create(x, y, clientId);
		player.networked = true;
		gameObjects.add(player);
		player.setProperty("clientId", clientId);
		return player;
	}

	@Override
	public void update(ClientInputEvent input) {
		if(input.client==null)
			return;
		if(! playerObjects.containsKey(input.client.getClientId())){
			logger.log(Level.FINE, "new input for client:"+input.client.getClientId());
			playerObjects.put(input.client.getClientId(), null);
			eventE.queue(new CharacterSpawnEvent(input.client.getClientId(), input.timestamp+1));
		}

		
	}
}
