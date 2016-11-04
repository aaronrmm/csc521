package common;

import java.io.Serializable;

public interface Component extends Serializable{
	public void destroy();
	public GameObject getGameObject();
	public void setGameObject(GameObject gameObject);
}
