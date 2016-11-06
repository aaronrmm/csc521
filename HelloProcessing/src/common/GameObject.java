package common;

import java.io.Serializable;
import java.util.HashMap;

public class GameObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntityClass entityClass;
	private HashMap<Class<? extends Component>, Component> components = new HashMap<Class<? extends Component>, Component>();
	private long id;
	public boolean alive = true;
	private static long nextId;
	
	public GameObject(EntityClass entityClass){
		this.entityClass = entityClass;
		this.id = nextId++;
	}
	
	public void destroy(){
		HashMap<Class<? extends Component>, Component> oldComponents = components;
		components = new HashMap<Class<? extends Component>, Component>();
		for(Component component:oldComponents.values()){
			component.destroy();
		}
		components.clear();
	}
	
	public void add(Component component){
		this.components.put(component.getClass(),component);
		component.setGameObject(this);
	}

	public Component getComponent(String name) {
		return this.components.get(name);
	}

	public long getId() {
		return this.id;
	}
	
	public int getComponentSize(){
		return components.size();
	}

	public Component getComponent(Class<? extends Component> class1) {
		return components.get(class1);
	}
}
