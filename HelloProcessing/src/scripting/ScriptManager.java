package scripting;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import common.ScriptComponent;

/**
 * Class to create and manage a JavaScript engine.
 */
public class ScriptManager {

	/* The javax.script JavaScript engine used by this class. */
	private static ScriptEngine js_engine = new ScriptEngineManager().getEngineByName("JavaScript");
	/* The Invocable reference to the engine. */
	private static Invocable js_invocable = (Invocable) js_engine;

	/**
	 * Used to bind the provided object to the name in the scope of the scripts
	 * being executed by this engine.
	 */
	public static void bindArgument(String name, Object obj) {
		js_engine.put(name,obj);
	}
	
	/**
	 * Will load the script source from the provided filename.
	 */
	public static void loadScript(String script_name) {
		try {
			js_engine.eval(new java.io.FileReader(script_name));
		}
		catch(ScriptException se) {
			se.printStackTrace();
		}
		catch(java.io.IOException iox) {
			iox.printStackTrace();
		}
	}

	/**
	 * Will invoke the "update" function of the script loaded by this engine
	 * without any parameters.
	 */
	public static void executeScript(String script_method_name) {
		try {
			js_invocable.invokeFunction(script_method_name);
		}
		catch(ScriptException se) {
			se.printStackTrace();
		}
		catch(NoSuchMethodException nsme) {
			nsme.printStackTrace();
		}
	}

	/**
	 * Will invoke the "update" function of the script loaded by this engine
	 * with the provided list of parameters.
	 */
	public static void executeScript(String script_method_name, Object... args) {
		try {
			js_invocable.invokeFunction(script_method_name,args);
		}
		catch(ScriptException se) {
			se.printStackTrace();
		}
		catch(NoSuchMethodException nsme) {
			nsme.printStackTrace();
		}
	}
	
	private static ConcurrentLinkedQueue<ScriptComponent> scripts = new ConcurrentLinkedQueue<ScriptComponent>();
	
	public static void run_scripts(){
		for(ScriptComponent script : scripts){
			script.update();
		}
	}
	
	public static void addScriptComponent(ScriptComponent script){
		scripts.add(script);
	}

	public static void removeScriptComponent(ScriptComponent scriptComponent) {
		scripts.remove(scriptComponent);
	}
}

