package hw2;

import java.util.HashMap;

import common.EventManagementEngine;
import common.RenderableComponent;
import common.RenderingEngine;
import hw1section5.Input;
import hw1section5.Input.Movement;
import processing.core.PApplet;

public class ProcessingRenderingEngine extends PApplet implements RenderingEngine {

	static HashMap<Long, RenderableComponent>renderableList = new HashMap<Long, RenderableComponent>();
	public static EventManagementEngine eventE = null;
	public ProcessingRenderingEngine(){
	}
	
	public void initialize(EventManagementEngine eventManagementE){
			ProcessingRenderingEngine.eventE = eventManagementE;
					PApplet.main(ProcessingRenderingEngine.class.getName());
	}
	
	@Override
	public void addObject(RenderableComponent renderable) {
		renderableList.put(renderable.id, renderable);
		
	}
	public void settings() {// runs first
		size(400, 300);

	}
	
	public void setup(){
	}
	
	public void draw(){
		this.fill(0);
		this.rect(0, 0, this.width, this.height);
		this.fill(255);
		for(RenderableComponent renderable : renderableList.values()){
			this.rect(renderable.getX(), renderable.getY(), renderable.getWidth(), renderable.getHeight());
		}
	}
	
	public void keyPressed() {
		Input input = new Input();
		if(key=='a')
			input.movement = Movement.left;
		if(key=='d')
			input.movement = Movement.right;
		if(key==' ')
			input.movement = Movement.jump;
		eventE.queue(input);
	}
}
