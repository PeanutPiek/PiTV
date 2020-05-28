/**
 * 
 */
package de.gg.pi.tv.graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import de.gg.pi.tv.ICursor;
import de.gg.pi.tv.MenuObject;
import de.gg.pi.tv.view.View;
import de.gg.pi.tv.view.ViewMenuHandler;

/**
 * @author PeanutPiek
 *
 */
public abstract class SwingView extends JPanel implements View {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected List<ViewMenuHandler> handlers = new ArrayList<ViewMenuHandler>();
	
	
	protected List<MenuObject> elements = new ArrayList<MenuObject>();
	
	
	protected ICursor cursor;
	
	
	protected JPanel page;
	

	
	
	public SwingView() {
		super();
		
		page = new JPanel();
	}
	

	@Override
	public void addMenuHandler(ViewMenuHandler handler) {
		handlers.add(handler);
	}


	@Override
	public void setCursor(ICursor cursor) {
		// TODO Auto-generated method stub
		this.cursor = cursor;
	}


	@Override
	public void addElement(MenuObject object) {
		elements.add(object);
		loadPage();
	}

}
