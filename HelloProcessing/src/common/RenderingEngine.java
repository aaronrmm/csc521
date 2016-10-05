package common;

public interface RenderingEngine {

	void rect(float x, float y, float width, float height);
	void ellipse(float x, float y, float width, float height);
	void addObject(RenderableComponent renderable);
}
