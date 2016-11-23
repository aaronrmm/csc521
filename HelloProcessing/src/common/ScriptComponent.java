package common;

import scripting.ScriptManager;

public class ScriptComponent extends AbstractComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String script_method_name;
	public Object args;
	
	public ScriptComponent(String script_method_name, GameObject gameObject, Object args){
		super(gameObject);
		this.args = args;
		this.script_method_name = script_method_name;
		ScriptManager.addScriptComponent(this);
	}

	@Override
	public void destroy() {
		ScriptManager.removeScriptComponent(this);
		
	}
	
	public void update(){
		if(args==null)
			ScriptManager.executeScript(this.script_method_name);
		else
			ScriptManager.executeScript(this.script_method_name, args);
	}
}
