/**
 * 
 */
package de.gg.pi.tv;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


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
