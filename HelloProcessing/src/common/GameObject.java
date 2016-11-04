package common;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class GameObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntityClass entityClass;
	private ConcurrentHashMap<String, Component> components = new ConcurrentHashMap<String,Component>();
	private long id;
	public boolean alive = true;
	private static long nextId;
	
	public GameObject(EntityClass entityClass){
		this.entityClass = entityClass;
		this.id = nextId++;
	}
	
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
		component.setGameObject(this);
	}

	public Component getComponent(String name) {
		return this.components.get(name);
	}

	public long getId() {
		return this.id;
	}
}
