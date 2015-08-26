/**
 * 
 */
package de.gg.pi.tv.menu;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.gg.pi.tv.menu.page.MenuObject;
import de.gg.pi.tv.menu.page.grid.MenuData;

/**
 * @author PeanutPiek
 *
 */
public abstract class MenuBoard<T extends MenuObject> extends JPanel {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected ArrayList<MenuData<T>> dataList = new ArrayList<MenuData<T>>();
	

	public MenuBoard() {
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
	}
}
