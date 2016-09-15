package hw1section4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import processing.core.PApplet;

public class Client extends PApplet{

	public static void main(String[]args){
		PApplet.main(Client.class.getName());
	}
	final static int PORT = 9500;
	final static String HOST = "127.0.0.1";
	Socket socket;

	public void settings() {// runs first
		size(200, 200);

	}
	
	public void setup(){
		try {
			socket = new Socket(HOST,PORT);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			byte[] message = new byte[30];
			in.read(message);
			System.out.println(new String(message,"UTF-8"));
			out.write(("Hi, everybody!").getBytes());
			socket.close();
		} catch (UnknownHostException e) {
			drawError();
			e.printStackTrace();
		} catch (IOException e) {
			drawError();
			e.printStackTrace();
		}
	}
	
	public void draw(){
		//draw self
		//network
		//draw others
	}
	
	private void drawError(){
		fill(255,0,0);
		this.rect(0, 0, width, height);
	}
	
	public void keyPressed() {
		System.out.println(key);
		if(key==ESC)
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
