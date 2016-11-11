package tests.jbox2d.processing.examples.Blobby;

import java.util.ArrayList;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Blobby extends PApplet{
	// A reference to our box2d world
	Box2DProcessing box2d;

	// A list we'll use to track fixed objects
	ArrayList<Boundary> boundaries;

	// Our "blob" object
	Blob blob;

	public static void main(String[]args){
		PApplet.main(Blobby.class.getName());
	}

	public void settings() {// runs first
		size(300, 300);
		smooth();

	}
	@Override
	public void setup() {

		// Initialize box2d physics and create the world
		box2d = new Box2DProcessing(this);
		box2d.createWorld();

		// Add some boundaries
		boundaries = new ArrayList<Boundary>();
		boundaries.add(new Boundary(width / 2, height - 5, width, 10, box2d, this));
		boundaries.add(new Boundary(width / 2, 5, width, 10, box2d, this));
		boundaries.add(new Boundary(width - 5, height / 2, 10, height, box2d, this));
		boundaries.add(new Boundary(5, height / 2, 10, height, box2d, this));

		// Make a new blob
		blob = new Blob(box2d, this);
	}
	@Override
	public void draw() {
		background(255);

		// We must always step through time!
		box2d.step();

		// Show the blob!
		blob.display();

		// Show the boundaries!
		for (Boundary wall : boundaries) {
			wall.display();
		}

	}
}
