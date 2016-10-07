package hw1section1;

import physics.BasicPhysicsEngine;
import physics.PhysicsComponent;
import physics.Rectangle;
import physics.Vector2d;
import processing.core.PApplet;

public class UsingProcessing extends PApplet {

	final int number_of_obstacles = 5;
	final int PLAYER_SPEED = 1;
	final int JUMP_SPEED = 20;
	int previous_time = 0;
	
	//systems manual startup
	BasicPhysicsEngine physics = new BasicPhysicsEngine();
	
	
	Rectangle[] obstacles = new Rectangle[number_of_obstacles];
	PhysicsComponent player;

	public static void main(String[] args) {
		PApplet.main(UsingProcessing.class.getName());
	}

	public void settings() {// runs first
		size(200, 200);

	}

	public void setup() {
		fill(120, 250, 240);// sets the paintbrush color
		int position = 0;
		for (int i = 0; i < number_of_obstacles; i++) {
			int width = (int) (Math.random() * this.width *5 / number_of_obstacles);
			int height = (int) (Math.random() * this.height / number_of_obstacles);
			Rectangle rectangle = new Rectangle(position,  this.height-height, width, height);
			obstacles[i] = rectangle;
			physics.addStaticObject(new PhysicsComponent(rectangle), rectangle.x, rectangle.y);
			position += width;
		}
		player = new PhysicsComponent(new Rectangle(0, 0, (int) (width / 20), (int) (height / 20)));
		physics.addDynamicObject(player,0,0);
		player.addConstantForce(new Vector2d(0,1));//applying gravity
	}

	public void draw() {
		//game logic
		
		//physics
		int current_time = this.millis();
		int delta = this.millis()-previous_time;
		physics.tick(delta/10);//todo justify slowing this down
		previous_time=current_time;
		
		
		//display
		fill(second() * 4 % 255, second() * 8 % 255, second() * 10 % 255);
		this.rect(0, 0, width, height);
		for (int i = 0; i < number_of_obstacles; i++) {
			Rectangle rect = obstacles[i];
			fill(0);
			this.rect((float) rect.getX(), (float) rect.getY(), (float) rect.getWidth(), (float) rect.getHeight());
		}
		this.rect((float) player.getX(), (float) player.getY(), (float) player.getWidth(),
				(float) player.getHeight());
	}

	//BASIC WASD INPUT
	public void keyPressed() {
		System.out.println(key);
		if(key=='a')
			player.addImpulseForce(new Vector2d(-PLAYER_SPEED,0));
//		if(key=='w')
//			player.addImpulseForce(new Vector2d(0,-PLAYER_SPEED));
//		if(key=='s')
//			player.addImpulseForce(new Vector2d(0,PLAYER_SPEED));
		if(key=='d')
			player.addImpulseForce(new Vector2d(PLAYER_SPEED,0));
		if(key==' ')
			player.addImpulseForce(new Vector2d(0, -JUMP_SPEED));
		
	}
}
