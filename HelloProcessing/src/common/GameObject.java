package common;

import java.io.Serializable;
import java.util.HashMap;

import physics.PhysicsComponent;

public class GameObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntityClass entityClass;
	private HashMap<String, Component> components = new HashMap<String, Component>();
	public RenderableComponent renderingC;
	public PhysicsComponent physicsC;
	private long id;
	public boolean alive = true;
	public boolean networked = false;;
	private static long nextId;
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	
	public GameObject(EntityClass entityClass){
		this.entityClass = entityClass;
		this.id = nextId++;
	}
	
	public void destroy(){
		HashMap<String, Component> oldComponents = components;
		components = new HashMap<String, Component>();
		for(Component component:oldComponents.values()){
			component.destroy();
		}
		components.clear();
	}
	
	public void add(Component component){
		this.components.put(component.getClass().getName(),component);
		if (component instanceof RenderableComponent)
			this.renderingC = (RenderableComponent) component;
		if( component instanceof PhysicsComponent)
			this.physicsC = (PhysicsComponent) component;
		component.setGameObject(this);
	}

	public Component getComponent(String name) {
		return this.components.get(name);
	}

	public long getId() {
		return this.id;
	}
	

	public Component getComponent(Class<? extends Component> class1) {
		return components.get(class1.getName());
	}
	
	public Object getProperty(String propertyName){
		return properties.get(propertyName);
	}
	
	public void setProperty(String propertyName, Object property){
		properties.put(propertyName, property);
	}
}
