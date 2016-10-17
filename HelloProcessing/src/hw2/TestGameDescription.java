package hw2;

import java.util.HashMap;
import java.util.LinkedList;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.InputListener;
import common.OscillatingController;
import common.RenderingEngine;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import common.factories.SpawnPointFactory;
import hw1section5.Input;
import physics.PhysicsComponent;
import physics.PhysicsEngine;

public class TestGameDescription implements GameDescription, InputListener{

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
		//platformF.create(0, 100, 200, 500);
		
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
		
	}
	
	@Override
	public GameObject spawnPlayer(int x, int y, long clientId){
		return playerF.create(x, y, clientId);
	}

	@Override
	public void update(Input input) {
		if(! playerObjects.containsKey(input.client.getClientId())){
			GameObject playerObject = spawnPlayer(0,0,input.client.getClientId());
			playerObjects.put(input.client.getClientId(), playerObject);
		}

		
	}

}
