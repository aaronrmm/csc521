package hw1section2;


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
		new Thread(new Runnable(){
			@Override
			public void run(){
				System.out.println("run a separate Thread"+Thread.currentThread().getId());
			}
		}).start();
	}

	public void setup() {
	}

	public void draw() {
		if(frameCount==1)
			System.out.println("drawing in Thread"+Thread.currentThread().getId());
		if(this.frameCount>200)
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
	/*is never called because keyPressed() is called in the same thread as draw()
	 *If you press a key before frame 200, this will print that it is the same thread as draw(). 
	 */
	public void keyPressed() {
		System.out.println("unpausing in KeyPressed(), Thread"+Thread.currentThread().getId());
		synchronized(game_state){
			game_state.notifyAll();
		}
	}
}
