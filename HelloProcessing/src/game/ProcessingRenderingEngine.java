package game;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.RenderableComponent;
import common.RenderingEngine;
import processing.core.PApplet;

public class ProcessingRenderingEngine extends PApplet implements RenderingEngine {

	private final static Logger logger = Logger.getLogger(ProcessingRenderingEngine.class.getName());
	public static EventManagementEngine eventE = null;
	public static String Name;
	public void setScene(Scene scene){Game.current_scene = scene;}
	
	public ProcessingRenderingEngine(){
		
	}
	
	public ProcessingRenderingEngine(String name, EventManagementEngine eventE){
		Name = name;
		PApplet.main(ProcessingRenderingEngine.class.getName());
	}
	
	public void initialize(EventManagementEngine eventManagementE){
			ProcessingRenderingEngine.eventE = eventManagementE;
					PApplet.main(ProcessingRenderingEngine.class.getName());
	}
	
	@Override
	public void addObject(RenderableComponent renderable) {
		logger.log(Level.FINEST, "putting renderable for gameobjectId "+renderable.getGameObject().getId());
		Game.current_scene.renderableList.put(renderable.getGameObject().getId(), renderable);
		
	}
	public void settings() {// runs first
		size(300, 300);

	}
	
	public void setup(){
		if (Name!= null)this.surface.setTitle(Name);
	}
	
	public void draw(){
		this.fill(0);
		this.rect(0, 0, this.width, this.height);
		this.fill(255,255,255,(int)(Math.abs(Game.eventtime.getTime()%255-127)));
		this.ellipse(0, 0, 10, 10);
		this.fill(255);
		for(RenderableComponent renderable : Game.current_scene.renderableList.values()){
			this.fill(renderable.getColor()[0],renderable.getColor()[1],renderable.getColor()[2],renderable.getColor()[3]);
			this.rect(renderable.getX(), renderable.getY(), renderable.getWidth(), renderable.getHeight());
			logger.finest(renderable.getGameObject().getId()+",");
		}
	}

	public void keyPressed() {
		Game.current_scene.input_handler.keyPressed(key);
	}


	@Override
	public void remove(RenderableComponent renderableComponent) {
		if (Game.current_scene.renderableList.remove(renderableComponent.getGameObject().getId())==null)
			logger.log(Level.SEVERE, "Could not remove renderable id "+renderableComponent.id);
		else
			logger.log(Level.FINEST, "removing renderable for gameobjectId "+renderableComponent.getGameObject().getId());
			
		
	}

	@Override
	public RenderableComponent getObject(long gameObjectId) {
		return Game.current_scene.renderableList.get(gameObjectId);
	}
}
