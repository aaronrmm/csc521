package games.platforms;

import hw3.HW3ServerMain;

public class Main {

	public static void main(String[]args){
		HW3ServerMain.setGameDescription(new PlatformsGameDescription());
		HW3ServerMain.main(args);
	}
}
