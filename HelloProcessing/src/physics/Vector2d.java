package physics;

public class Vector2d {
	int x;
	int y;
	
	public Vector2d(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public void add(Vector2d vector){
		this.x+=vector.x;
		this.y+=vector.y;
	}
}
