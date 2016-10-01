package common;

import java.util.concurrent.ConcurrentHashMap;

public class GameObject {

	private ConcurrentHashMap<String, Component> components = new ConcurrentHashMap<String,Component>();
	
	public void destroy(){
		ConcurrentHashMap<String, Component>oldComponents = components;
		components = new ConcurrentHashMap<String, Component>();
		for(Component component:oldComponents.values()){
			component.destroy();
		}
		components.clear();
	}
	
	public void add(Component component, String className){
		this.components.put(className,component);
	}

	public Component getComponent(String name) {
		return this.components.get(name);
	}
}
