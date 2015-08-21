/**
 * 
 */
package de.gg.pi.tv.menu.page.gl;

import java.util.ArrayList;
import java.util.List;

import de.gg.pi.tv.menu.MenuBoard;
import de.gg.pi.tv.menu.page.MenuObject;

/**
 * @author PeanutPiek
 *
 */
public class MenuOGLBoard<T extends MenuObject> extends MenuBoard<T> implements Runnable {

	
	
	
	
	private List<OGLObject<T>> valueList = new ArrayList<OGLObject<T>>();
	
	
	
	private Thread renderingThread;
	
	
	public MenuOGLBoard() {
		super();
		
		
		
		
		renderingThread = new Thread(this);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
