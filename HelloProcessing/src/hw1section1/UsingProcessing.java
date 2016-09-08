package hw1section1;
import java.awt.Rectangle;

import physics.BasicPhysicsEngine;
import physics.PhysicsObject;
import physics.Vector2d;
import processing.core.PApplet;

public class UsingProcessing extends PApplet {

	final int number_of_obstacles = 5;
	final int PLAYER_SPEED = 1;
	final int JUMP_SPEED = 20;
	final int BACKGROUND_COLOR = 250;
	int previous_time = 0;
	
	//systems manual startup
	BasicPhysicsEngine physics = new BasicPhysicsEngine();
	
	
	Rectangle[] obstacles = new Rectangle[number_of_obstacles];
	PhysicsObject player;

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
			int width = (int) (Math.random() * this.width *5 / number_of_obstacles);
			int height = (int) (Math.random() * this.height / number_of_obstacles);
			Rectangle rectangle = new Rectangle(position,  this.height-height, width, height);
			obstacles[i] = rectangle;
			physics.addObject(new PhysicsObject(rectangle));
			position += width;
		}
		player = new PhysicsObject(new Rectangle(0, 0, (int) (width / 20), (int) (height / 20)));
		physics.addObject(player);
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
		fill(BACKGROUND_COLOR);
		this.rect(0, 0, width, height);
		for (int i = 0; i < number_of_obstacles; i++) {
			Rectangle rect = obstacles[i];
			fill(second() * 4 % 255, millis() * 8 % 255, millis() * 10 % 255);
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
