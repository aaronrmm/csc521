package hw2;

import java.util.HashMap;

import common.EventManagementEngine;
import common.GameDescription;
import common.GameObject;
import common.InputListener;
import common.RenderingEngine;
import common.factories.PlatformObjectFactory;
import common.factories.PlayerObjectFactory;
import hw1section5.Input;
import physics.PhysicsEngine;

public class TestGameDescription implements GameDescription, InputListener{

	private PlayerObjectFactory playerF;
	private HashMap<Long, GameObject> playerObjects=new HashMap<Long, GameObject>();
	
	public TestGameDescription(EventManagementEngine eventE) {
		eventE.register(this);
	}

	@Override
	public void generateGame(RenderingEngine renderingE, PhysicsEngine physicsE, PlayerObjectFactory playerF, PlatformObjectFactory platformF) {
		platformF.create(0, 100, 200, 500);
		this.playerF = playerF;
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
