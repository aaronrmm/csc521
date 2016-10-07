package common;

public abstract class AbstractComponent implements Component{

	private GameObject gameObject;
	public GameObject getGameObject(){
		return gameObject;
	}
	public void setGameObject(GameObject gameObject){
		this.gameObject = gameObject;
	}
}
