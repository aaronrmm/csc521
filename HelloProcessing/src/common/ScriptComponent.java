package common;

import scripting.ScriptManager;

public class ScriptComponent extends AbstractComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String script_method_name;
	
	public ScriptComponent(String script_method_name, GameObject gameObject){
		super(gameObject);
		this.script_method_name = script_method_name;
		ScriptManager.addScriptComponent(this);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(){
		ScriptManager.bindArgument("game_object", this.getGameObject());
		ScriptManager.executeScript(this.script_method_name);
	}
}
