package games.platforms;

import hw3.HW3ServerMain;
import scripting.ScriptManager;

public class Main {

	public static void main(String[]args){
		ScriptManager.loadScript("scripts/platforms.js");
		HW3ServerMain.setGameDescription(new PlatformsGameDescription());
		HW3ServerMain.main(args);
	}
}
