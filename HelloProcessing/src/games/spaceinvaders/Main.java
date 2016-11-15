package games.spaceinvaders;

import hw3.HW3ServerMain;
import scripting.ScriptManager;

public class Main {

	public static void main(String[]args){
		ScriptManager.loadScript("scripts/space_invaders.js");
		HW3ServerMain.setGameDescription(new SpaceInvadersGameDescription());
		HW3ServerMain.main(args);
	}
}
