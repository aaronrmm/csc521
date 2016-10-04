package common.factories;

import common.GameObject;

public class GameObjectFactory {

	public GameObject create(){
		return new GameObject(null);
	}
}
