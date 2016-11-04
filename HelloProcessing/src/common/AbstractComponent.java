package common;

public abstract class AbstractComponent implements Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient GameObject gameObject;
	public GameObject getGameObject(){
		return gameObject;
	}
	public void setGameObject(GameObject gameObject){
		this.gameObject = gameObject;
	}
	public AbstractComponent(GameObject gameObject){
		this.gameObject = gameObject;
	}
}
