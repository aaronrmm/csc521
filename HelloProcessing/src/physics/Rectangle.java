package physics;

public class Rectangle extends java.awt.Rectangle{

	public Rectangle(int newX, int newY, int width, int height) {
		super(newX,newY,width,height);
		this.id = next_id++; 
	}
	public Rectangle() {
		this(0,0,0,0);
	}
	private static final long serialVersionUID = -4119950297729882857L;
	public long id;
	private static long next_id = 0;
}