package hw2;

import java.util.logging.Level;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.RenderableComponent;
import common.RenderingEngine;
import game.Scene;
import processing.core.PApplet;

public class ProcessingRenderingEngine extends PApplet implements RenderingEngine {

	private final static Logger logger = Logger.getLogger(ProcessingRenderingEngine.class.getName());
	public static EventManagementEngine eventE = null;
	public static String Name;
	private static Scene _Scene = new Scene();
	public Scene getScene(){return _Scene;}
	public void setScene(Scene scene){_Scene = scene;}
	
	public ProcessingRenderingEngine(){
		
	}
	
	public ProcessingRenderingEngine(String name, Scene scene, EventManagementEngine eventE){
		Name = name;
		_Scene = scene;
		PApplet.main(ProcessingRenderingEngine.class.getName());
	}
	
	public void initialize(EventManagementEngine eventManagementE){
			ProcessingRenderingEngine.eventE = eventManagementE;
					PApplet.main(ProcessingRenderingEngine.class.getName());
	}
	
	@Override
	public void addObject(RenderableComponent renderable) {
		logger.log(Level.FINEST, "putting renderable for gameobjectId "+renderable.getGameObject().getId());
		_Scene.renderableList.put(renderable.getGameObject().getId(), renderable);
		
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
		this.fill((int)(Math.random()*255));
		this.ellipse(0, 0, 50, 50);
		this.fill(255);
		for(RenderableComponent renderable : _Scene.renderableList.values()){
			this.rect(renderable.getX(), renderable.getY(), renderable.getWidth(), renderable.getHeight());
		}
	}
	
	public void keyPressed() {
		_Scene.input_handler.keyPressed(key);
	}


	@Override
	public void remove(RenderableComponent renderableComponent) {
		if (_Scene.renderableList.remove(renderableComponent.getGameObject().getId())==null)
			logger.log(Level.SEVERE, "Could not remove renderable id "+renderableComponent.id);
		else
			logger.log(Level.FINEST, "removing renderable for gameobjectId "+renderableComponent.getGameObject().getId());
			
		
	}

	@Override
	public RenderableComponent getObject(long gameObjectId) {
		return _Scene.renderableList.get(gameObjectId);
	}
}
