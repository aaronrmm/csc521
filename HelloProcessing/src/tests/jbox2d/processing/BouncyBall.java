package tests.jbox2d.processing;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class BouncyBall {
	
    public Body body;
    
    public BouncyBall(float x, float y, float radius, World world){
    	  //Create an JBox2D body defination for ball.
	    BodyDef bd = new BodyDef();
	    bd.type = BodyType.DYNAMIC;
	    bd.position.set(x, y);
	    
	    CircleShape cs = new CircleShape();
	    cs.m_radius = radius * 0.1f;  //We need to convert radius to JBox2D equivalent
	    
	 // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 1f;
        fd.friction = 0.3f;        
        fd.restitution = 0.0f;
        
        /**
         * Virtual invisible JBox2D body of ball. Bodies have velocity and position. 
         * Forces, torques, and impulses can be applied to these bodies.
         */
         body = world.createBody(bd);
         body.createFixture(fd);
    }

}
