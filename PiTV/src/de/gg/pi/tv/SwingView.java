/**
 * 
 */
package de.gg.pi.tv;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import de.gg.pi.tv.view.View;
import de.gg.pi.tv.view.ViewMenuHandler;

/**
 * @author PeanutPiek
 *
 */
public abstract class SwingView extends JPanel implements View {
	
	
	
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
