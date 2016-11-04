package physics;

import java.io.Serializable;

import org.jbox2d.common.Vec2;

public class Vector2d extends Vec2 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector2d(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public void add(Vector2d vector){
		this.x+=vector.x;
		this.y+=vector.y;
	}
}
