package common;

import physics.PhysicsComponent;
import physics.Rectangle;

public class RenderableComponent extends AbstractComponent implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long id;
	private static long next_id = 0;
	private static transient RenderingEngine RenderE;
	private int color[] = {255,255,255,255};

	private PhysicsComponent physics;
	
	public RenderableComponent(GameObject gameObject, PhysicsComponent physics, RenderingEngine renderE, int[] color){
		super(gameObject);
		this.physics = physics;
		this.id = next_id++;
		RenderE = renderE;
		this.color = color;
	}
	
	public RenderableComponent(GameObject object, PhysicsComponent physicsComponent, RenderingEngine renderer) {
		this(object, physicsComponent, renderer, new int[]{255,255,255,255});
	}

	@Override
	public void destroy() {
		if(RenderE!=null)
			RenderE.remove(this);
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

	public int[] getColor(){
		return color;
	}
	
	public void setColor(int[] color){
		this.color = color;
	}
}
