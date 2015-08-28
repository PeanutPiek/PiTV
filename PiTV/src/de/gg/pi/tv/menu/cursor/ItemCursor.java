/**
 * 
 */
package de.gg.pi.tv.menu.cursor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import de.gg.pi.tv.ICursor;

/**
 * @author PeanutPiek
 *
 */
public abstract class ItemCursor implements ICursor {
	
	
	protected Color mainColor;
	
	
	public ItemCursor(Color color) {
		super();
		mainColor = color;
		
	}
	
	
	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean isVisible() {
		return true;
	}


	public abstract void paint(Graphics g, int x, int y, int w, int h);
	
}
