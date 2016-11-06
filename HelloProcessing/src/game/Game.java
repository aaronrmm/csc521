package game;

import java.util.HashMap;

import common.EventManagementEngine;
import common.RenderingEngine;
import common.events.GenericListener;
import common.events.SceneChangeEvent;
import common.timelines.Timeline;
import hw2.ProcessingRenderingEngine;

public class Game implements GenericListener<SceneChangeEvent>{
	
	public static final Timeline realtime = new Timeline(null, 1);
	public static Timeline eventtime = new Timeline(realtime, 61);
	public static EventManagementEngine eventE = new EventManagementEngine(eventtime);
	public static Scene current_scene;
	public static RenderingEngine renderingE;
	private static HashMap<Object, Scene> scenes = new HashMap<Object, Scene>();
	
	public Game(String name, Scene starting_scene, Scene... scenes){
		for (Scene scene : scenes)
			Game.scenes.put(scene.id, scene);
		current_scene = starting_scene;
		SceneChangeEvent.Register(this);
		renderingE = new ProcessingRenderingEngine(name, eventE);
	}

	@Override
	public void update(SceneChangeEvent event) {
		current_scene = scenes.get(event.scene_id);
	}

	public static Scene getScene(long id) {
		return Game.scenes.get(id);
	}

}
