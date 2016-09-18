package hw1section4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import hw1section4.Input.Movement;
import physics.Rectangle;
import processing.core.PApplet;

public class Client extends PApplet{

	public static void main(String[]args){
		if(args.length==2){
			xPLocation = Integer.parseInt(args[0]);
			yPLocation = Integer.parseInt(args[1]);
		}
		PApplet.main(Client.class.getName());
	}
	final static int PORT = 9500;
	final static String HOST = "127.0.0.1";
	private static int xPLocation;
	private static int yPLocation;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	private ConcurrentHashMap<Long, Rectangle> worldView = new ConcurrentHashMap<Long, Rectangle>();
	
	
	public void settings() {// runs first
		size(200, 300);

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
							drawError();
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
		if(this.frameCount==1)
			frame.setLocation(xPLocation, yPLocation);//is not working
		fill(second() * 4 % 255, second() * 8 % 255, second() * 10 % 255);
		this.rect(0, 0, this.width, this.height);
		//draw others
		for(Rectangle rect : worldView.values()){
			fill(0);
			this.rect(rect.x, rect.y, rect.width, rect.height);
		}
		
	}
	
	private void drawError(){
		fill(255,0,0);
		this.rect(0, 0, this.width, this.height);
	}
	
	public void keyPressed() {
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
