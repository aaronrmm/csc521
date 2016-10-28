package networking;

import java.io.Serializable;

import physics.Rectangle;

public class UpdatedRectangle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4631594967856121035L;
	public Rectangle rectangle;
	public boolean isNew;
	
	public UpdatedRectangle(Rectangle rectangle){
		isNew = true;
		this.rectangle = rectangle;
	}
}
