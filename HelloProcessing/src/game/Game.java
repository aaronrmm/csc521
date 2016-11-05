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
	public static Timeline eventtime = new Timeline(realtime, 1);
	public static EventManagementEngine eventE = new EventManagementEngine(eventtime);
	public static Scene current_scene;
	public static RenderingEngine renderingE;
	private HashMap<Object, Scene> scenes = new HashMap<Object, Scene>();
	
	public Game(String name, HashMap<Object, Scene> scenes, Scene starting_scene){
		this.scenes = scenes;
		current_scene = starting_scene;
		SceneChangeEvent.Register(this);
		renderingE = new ProcessingRenderingEngine(name, current_scene, eventE);
	}

	@Override
	public void update(SceneChangeEvent event) {
		current_scene = scenes.get(event.scene_key);
	}

}
