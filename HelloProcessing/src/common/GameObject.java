package common;

import java.util.ArrayList;

public class GameObject {

	private ArrayList<Component> components = new ArrayList<Component>();
	
	public void destroy(){
		ArrayList<Component>oldComponents = components;
		components = new ArrayList<Component>();
		for(Component component:oldComponents){
			component.destroy();
		}
		components.clear();
	}
	
	public void add(Component component){
		this.components.add(component);
	}
}
