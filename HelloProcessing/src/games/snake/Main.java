package games.snake;

import hw3.HW3ServerMain;
import scripting.ScriptManager;

public class Main {

	public static void main(String[]args){
		ScriptManager.loadScript("scripts/snake.js");
		HW3ServerMain.setGameDescription(new SnakeGameDescription());
		HW3ServerMain.main(args);
	}
}
