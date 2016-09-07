import java.awt.Rectangle;
import java.awt.Shape;

import processing.core.PApplet;

public class UsingProcessing extends PApplet {

	final int number_of_obstacles = 5;
	final int PLAYER_SPEED = 1;
	final int BACKGROUND_COLOR = 250;
	Rectangle[] obstacles = new Rectangle[number_of_obstacles];
	Rectangle player;

	public static void main(String[] args) {
		PApplet.main("UsingProcessing");
	}

	public void settings() {// runs first
		size(200, 200);

	}

	public void setup() {
		fill(120, 250, 240);// sets the paintbrush color
		int position = 0;
		for (int i = 0; i < number_of_obstacles; i++) {
			int width = (int) (Math.random() * this.width / number_of_obstacles);
			int height = (int) (Math.random() * this.height / number_of_obstacles);
			Rectangle rectangle = new Rectangle(position, 0, width, height);
			obstacles[i] = rectangle;
			position += width;
		}
		player = new Rectangle((int) (width / 2), (int) (height / 2), (int) (width / 2), (int) (height / 2));
		System.out.println("in setup");
	}

	public void draw() {
		fill(BACKGROUND_COLOR);
		this.rect(0, 0, width, height);
		for (int i = 0; i < number_of_obstacles; i++) {
			Rectangle rect = obstacles[i];
			fill(second() * 4 % 255, millis() * 8 % 255, millis() * 10 % 255);
			this.rect((float) rect.getX(), (float) rect.getY(), (float) rect.getWidth(), (float) rect.getHeight());
		}
		this.ellipse((float) player.getX(), (float) player.getY(), (float) player.getWidth(),
				(float) player.getHeight());
	}

	//BASIC WASD INPUT
	public void keyPressed() {
		System.out.println(key);
		if(key=='a')
			move(player,player.x-PLAYER_SPEED,player.y);
		if(key=='w')
			move(player,player.x,player.y-PLAYER_SPEED);
		if(key=='s')
			move(player,player.x,player.y+PLAYER_SPEED);
		if(key=='d')
			move(player,player.x+PLAYER_SPEED,player.y);
		
		System.out.println(player.x+","+player.y);
	}
	
	public void move(Shape shape, int x, int y){
		for(int i=0;i<obstacles.length;i++){
			if(shape==obstacles[i]) continue;
			if(obstacles[i].intersects(x,y,shape.getBounds2D().getWidth(),shape.getBounds2D().getHeight())){
				if (OnCollide(shape, obstacles[i]){
					
				}
			}
		}
	}
	
	public boolean OnCollide(Shape shapeA, Shape shapeB){
		return false;
	}
}
