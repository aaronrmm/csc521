package tests.jbox2d.processing;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import processing.core.PApplet;

public class Main extends PApplet{
	
	public static Body BALL = null;

	//Create a JBox2D world. 
    public static final World world = new World(new Vec2(0.0f, -0.01f), true);
     
    //Screen width and height
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
     
    //Ball radius in pixel
    public static final int BALL_RADIUS = 8;

    
    public static void main(String[]args){
    	PApplet.main(Main.class.getName());
    }
	
	public void settings() {// runs first
		size(WIDTH, HEIGHT);

	}
    public void setup(){
    	//create ball   
        final BouncyBall ball = new BouncyBall(45, 90, BALL_RADIUS, world);
        BALL = ball.body;
          
        //Add ground to the application, this is where balls will land
        addGround(WIDTH, 10);
         
        //Add left and right walls so balls will not move outside the viewing area.
        addWall(0,HEIGHT,1,HEIGHT); //Left wall
        addWall(WIDTH-1,HEIGHT,1,HEIGHT); //Right wall
    }
    
    public void draw(){
		fill(second() * 4 % 255, second() * 8 % 255, second() * 10 % 255);
		this.rect(0, 0, WIDTH, HEIGHT);
    	
    	world.step(1, 1, 1);
	
		ellipse(BALL.getPosition().x, HEIGHT - BALL.getPosition().y, BALL_RADIUS, BALL_RADIUS);
    }
    
  //This method adds a ground to the screen. 
    public static void addGround(float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);
             
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
     
        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,-10f);
     
        world.createBody(bd).createFixture(fd);
    }
    
  //This method creates a walls. 
    public static void addWall(float posX, float posY, float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);
             
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;    
     
        BodyDef bd = new BodyDef();
        bd.position.set(posX, posY);
             
        world.createBody(bd).createFixture(fd);
    }
    

	//BASIC WASD INPUT
	public void keyPressed() {
		System.out.println(key);//applyLinearImpulse
		if(key=='a')//applyForce
			BALL.applyTorque(200);
		if(key=='w')
			BALL.applyLinearImpulse(new Vec2(0, 1), BALL.getPosition());
		if(key=='s')
			BALL.applyLinearImpulse(new Vec2(0, -1), BALL.getPosition());
		if(key=='d')
			BALL.applyTorque(-200);
		if(key==' ')
			BALL.applyLinearImpulse(new Vec2(0, 1), BALL.getPosition());
		
	}

}
