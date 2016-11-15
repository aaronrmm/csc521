package games.random;

import hw3.HW3ServerMain;
import scripting.ScriptManager;

public class Main {

	public static void main(String[]args){
		ScriptManager.loadScript("scripts/random_movement.js");
		HW3ServerMain.setGameDescription(new RandomGameDescription());
		HW3ServerMain.main(args);
	}
}
