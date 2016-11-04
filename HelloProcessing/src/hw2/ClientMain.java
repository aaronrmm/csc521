package hw2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.RenderableComponent;
import common.events.CharacterDeathEvent;
import common.events.CharacterSyncEvent;
import common.events.ClientInputEvent;
import common.events.GenericListener;
import common.timelines.Timeline;
import networking.ClientsideNetworking;
import physics.BasicPhysicsEngine;
import physics.PhysicsEngine;

public class ClientMain {

	private static final Logger logger = Logger.getLogger(ClientMain.class.getName());
	
	public static void main(String[]args){
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		Timeline timeline = new Timeline(null, 100);
		EventManagementEngine eventE = new EventManagementEngine(timeline);
		ProcessingRenderingEngine renderingE = new ProcessingRenderingEngine();
		ProcessingRenderingEngine.Name = "Client";
		renderingE.initialize(eventE);
		PhysicsEngine physicsE = new BasicPhysicsEngine(eventE);
		//PlayerObjectFactory playerF = new PlayerObjectFactory(physicsE, renderingE, eventE);
		//PlatformObjectFactory platformF = new PlatformObjectFactory(physicsE, renderingE);
		//SpawnPointFactory spawnF = new SpawnPointFactory(physicsE);
		//GameDescription game = new TestGameDescription(eventE);
		//game.generateGame(eventE, renderingE, physicsE, playerF, platformF, spawnF);
		ClientsideNetworking networking = new ClientsideNetworking(eventE);
		ClientInputEvent.Register(networking);
		CharacterSyncEvent.Register(new GenericListener<CharacterSyncEvent>(){

			@Override
			public void update(CharacterSyncEvent event) {
				RenderableComponent renderable = (RenderableComponent) event.getCharacter().getComponent(RenderableComponent.class.getName());
				renderable.setGameObject( event.getCharacter());
				renderingE.addObject(renderable);
			}
		});
		CharacterDeathEvent.Register(new GenericListener<CharacterDeathEvent>(){
			@Override
			public void update(CharacterDeathEvent event){
				renderingE.remove(renderingE.getObject(event.character.getId()));
			}
		});
		networking.start();
		long lastTick = 0;

		while(true){
			if (timeline.getTime() - lastTick >0){
				try{
				physicsE.tick((int)(timeline.getTime()-lastTick));
				lastTick = timeline.getTime();
				eventE.HandleNextEvents(800);
				logger.log(Level.SEVERE, "client tick");
				Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
