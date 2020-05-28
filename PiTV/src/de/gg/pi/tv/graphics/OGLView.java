/**
 * 
 */
package de.gg.pi.tv.graphics;

import java.awt.Canvas;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuObject;
import de.gg.pi.tv.view.View;
import de.gg.pi.tv.view.ViewMenuHandler;

/**
 * OpenGL View Component.
 * 
 * @author PeanutPiek
 *
 */
public class OGLView extends AWTGLCanvas implements View {

	
	
	
	private ArrayList<MenuObject> elements = new ArrayList<MenuObject>();
	
	
	
	public OGLView() throws LWJGLException {
		super();
		
		
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#addMenuHandler(de.gg.pi.tv.view.ViewMenuHandler)
	 */
	@Override
	public void addMenuHandler(ViewMenuHandler handler) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#setCursor(de.gg.pi.tv.ICursor)
	 */
	@Override
	public void setCursor(ICursor cursor) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#addElement(de.gg.pi.tv.MenuObject)
	 */
	@Override
	public void addElement(MenuObject object) {
		// TODO Auto-generated method stub
		elements.add(object);
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#hasNextPage()
	 */
	@Override
	public boolean hasNextPage() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#hasPrevPage()
	 */
	@Override
	public boolean hasPrevPage() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#moveNextPage()
	 */
	@Override
	public void moveNextPage() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#movePrevPage()
	 */
	@Override
	public void movePrevPage() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.gg.pi.tv.view.View#loadPage()
	 */
	@Override
	public void loadPage() {
		// TODO Auto-generated method stub

	}

	
}
