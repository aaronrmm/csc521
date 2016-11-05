package rendering;

import java.util.concurrent.ConcurrentHashMap;

import common.RenderableComponent;

public class Scene {

	public ConcurrentHashMap<Long, RenderableComponent>renderableList = new ConcurrentHashMap<Long, RenderableComponent>();

}
