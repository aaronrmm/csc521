package common;

import physics.PhysicsComponent;
import physics.Rectangle;

public class RenderableComponent extends AbstractComponent implements Component {

	public long id;
	private static long next_id = 0;

	private PhysicsComponent physics;
	
	public RenderableComponent(PhysicsComponent physics){
		this.physics = physics;
		this.id = next_id++;
	}
	
	@Override
	public void destroy() {
	}
	
	public int getX(){
		return physics.getX();
	}
	
	public int getY(){
		return physics.getY();
	}
	
	public int getWidth(){
		return physics.getWidth();
	}
	
	public int getHeight(){
		return physics.getHeight();
	}
	
	public Rectangle render(){
		Rectangle render = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
		render.id = this.id;
		return render;
	}

}
