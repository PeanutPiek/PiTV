/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Image;

import de.gg.pi.tv.menu.utils.Vector2;

/**
 * @author PeanutPiek
 *
 */
public class GridData<T extends GridValue> {
	
	
	private T data;
	
	
	private int num;
	
	
	private Vector2 origin;


	private DataHandler dataHandler;
	
	
	
	public GridData(T data, int left, int top) {
		this.data = data;
		this.origin = new Vector2(left, top);
		this.num = left * top;
	}
	
	
	public void setData(T data) {
		this.data = data;
	}
	
	
	public T getData() {
		return this.data;
	}
	
	
	public int getNum() {
		return this.num;
	}
	
	
	public Vector2 getOrigin() {
		return this.origin;
	}


	public Image getIcon() {
		return data.getIcon();
	}


	public String getName() {
		return data.getName();
	}


	public void call() {
		data.call();
	}


	public void setDataHandler(DataHandler handler) {
		this.dataHandler = handler;
	}


	public DataHandler getHandler() {
		return dataHandler;
	}
}
