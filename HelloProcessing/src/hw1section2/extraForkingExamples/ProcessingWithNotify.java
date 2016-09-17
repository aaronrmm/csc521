package hw1section2.extraForkingExamples;


import processing.core.PApplet;

public class ProcessingWithNotify extends PApplet {

	public static boolean paused;
	public static Object game_state=new Object();
	public static synchronized Object getGameState(){ return game_state; }
	
	public static void main(String[] args) {
		PApplet.main(ProcessingWithNotify.class.getName());
	}

	public void settings() {// runs first
		size(200, 200);

	}

	public void setup() {
	}

	public void draw() {
		System.out.println("drawing");
		try{ synchronized(game_state){ 
			System.out.println("paused");
			game_state.wait();
			System.out.println("unpaused");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	//BASIC WASD INPUT
	public void keyPressed() {//is never called because keyPressed() is called in the same thread as draw()
		System.out.println("unpausing");
		synchronized(game_state){
			game_state.notifyAll();
		}
	}
}
