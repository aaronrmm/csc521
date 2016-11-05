package hw2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.EventManagementEngine;
import common.RenderableComponent;
import common.RenderingEngine;
import common.events.ClientInputEvent;
import common.events.ClientInputEvent.Command;
import physics.Rectangle;
import processing.core.PApplet;

public class ProcessingRenderingEngine extends PApplet implements RenderingEngine {

	private final static Logger logger = Logger.getLogger(ProcessingRenderingEngine.class.getName());
	static ConcurrentHashMap<Long, RenderableComponent>renderableList = new ConcurrentHashMap<Long, RenderableComponent>();
	public static EventManagementEngine eventE = null;
	public static String Name;

	public ProcessingRenderingEngine(){
		
	}
	
	public ProcessingRenderingEngine(String name){
		Name = name;
	}
	
	public void initialize(EventManagementEngine eventManagementE){
			ProcessingRenderingEngine.eventE = eventManagementE;
					PApplet.main(ProcessingRenderingEngine.class.getName());
	}
	
	@Override
	public void addObject(RenderableComponent renderable) {
		logger.log(Level.FINEST, "putting renderable for gameobjectId "+renderable.getGameObject().getId());
		renderableList.put(renderable.getGameObject().getId(), renderable);
		
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
		for(RenderableComponent renderable : renderableList.values()){
			this.rect(renderable.getX(), renderable.getY(), renderable.getWidth(), renderable.getHeight());
		}
	}
	
	public void keyPressed() {
		ClientInputEvent input = new ClientInputEvent();
		if(key=='a')
			input.command = Command.left;
		if(key=='d')
			input.command = Command.right;
		if(key==' ')
			input.command = Command.jump;
		if(key=='r')
			input.command = Command.record_replay;
		eventE.queue(input);
	}

	public List<Rectangle> getRectangles() {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		for(RenderableComponent renderable: renderableList.values()){
			rectangles.add(renderable.render());
		}
		return rectangles;
	}

	@Override
	public void remove(RenderableComponent renderableComponent) {
		if (renderableList.remove(renderableComponent.getGameObject().getId())==null)
			logger.log(Level.SEVERE, "Could not remove renderable id "+renderableComponent.id);
		else
			logger.log(Level.FINEST, "removing renderable for gameobjectId "+renderableComponent.getGameObject().getId());
			
		
	}

	@Override
	public RenderableComponent getObject(long gameObjectId) {
		return renderableList.get(gameObjectId);
	}
}
