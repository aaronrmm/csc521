package hw1section4;

import hw1section4.Input.Movement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import physics.Rectangle;
import processing.core.PApplet;

public class Client extends PApplet{

	public static void main(String[]args){
		PApplet.main(Client.class.getName());
	}
	final static int PORT = 9500;
	final static String HOST = "127.0.0.1";
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	private HashMap<Long, Rectangle> worldView = new HashMap<Long, Rectangle>();
	
	
	public void settings() {// runs first
		size(200, 200);

	}
	
	public void setup(){
		try {
			socket = new Socket(HOST,PORT);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(new Input());
			new Thread(new Runnable(){
				@Override
				public void run(){
					while(true){
						try {
							updateView((Rectangle)ois.readObject());
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} catch (UnknownHostException e) {
			drawError();
			e.printStackTrace();
		} catch (IOException e) {
			drawError();
			e.printStackTrace();
		}
	}
	
	public void draw(){
		fill(255);
		this.rect(0, 0, this.width, this.height);
		//draw self
		//network
		//draw others
		for(Rectangle rect : worldView.values()){
			fill(0);
			this.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
	}
	
	private void drawError(){
		fill(255,0,0);
		this.rect(0, 0, width, height);
	}
	
	public void keyPressed() {
		System.out.println("input");
		try {
			if(key=='a')
				oos.writeObject(new Input(Movement.left));
	//		if(key=='w')
	//			player.addImpulseForce(new Vector2d(0,-PLAYER_SPEED));
	//		if(key=='s')
	//			player.addImpulseForce(new Vector2d(0,PLAYER_SPEED));
			if(key=='d')
				oos.writeObject(new Input(Movement.right));
			if(key==' ')
				oos.writeObject(new Input(Movement.jump));
			if(key==ESC)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateView(Rectangle rectangle){
		worldView.put(rectangle.id, rectangle);
	}
}
