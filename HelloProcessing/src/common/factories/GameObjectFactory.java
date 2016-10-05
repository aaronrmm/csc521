package common.factories;

import common.GameObject;

public class GameObjectFactory {

	public GameObject create(int x, int y){
		return new GameObject(null);
	}
}
